import javax.swing.*;
import java.awt.*;

public class InterfaceSeusPokemons extends JFrame {
    private static final int LIMITE_POKEMONS = 6;
    private final Pilha<Pokemon> pilhaCompartilhada;
    private JPanel pokemonsPanel;
    public InterfaceSeusPokemons(Pilha<Pokemon> pilhaCapturados) {
        this.pilhaCompartilhada = pilhaCapturados;
        setTitle("Seus Pokémons Capturados");
        setSize(700, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        JLabel tituloLabel = new JLabel("Seu Pokemons Principais", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(tituloLabel, BorderLayout.NORTH);
        pokemonsPanel = new JPanel();
        pokemonsPanel.setLayout(new GridLayout(1, LIMITE_POKEMONS, 10, 10));
        pokemonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        atualizarPokemons();
        add(pokemonsPanel, BorderLayout.CENTER);
        JPanel botoesPanel = new JPanel(new FlowLayout());
        JButton atualizarButton = new JButton("Atualizar");
        JButton fecharButton = new JButton("Fechar");
        atualizarButton.addActionListener(e -> atualizarPokemons());
        fecharButton.addActionListener(e -> dispose());
        botoesPanel.add(atualizarButton);
        botoesPanel.add(fecharButton);
        add(botoesPanel, BorderLayout.SOUTH);
    }
    private void atualizarPokemons() {
        pokemonsPanel.removeAll();
        Pilha<Pokemon> pilhaAuxiliar = new Pilha<>();
        int count = 0;
        while (!pilhaCompartilhada.isEmpty() && count < LIMITE_POKEMONS) {
            Pokemon pokemon = pilhaCompartilhada.pop();
            pokemonsPanel.add(criarPainelPokemon(pokemon));
            pilhaAuxiliar.push(pokemon);
            count++;
        }
        while (!pilhaAuxiliar.isEmpty()) {
            pilhaCompartilhada.push(pilhaAuxiliar.pop());
        }
        pokemonsPanel.revalidate();
        pokemonsPanel.repaint();
    }
    private JPanel criarPainelPokemon(Pokemon pokemon) {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        painel.setBackground(Color.WHITE);
        String caminhoImagem = "/imagens/" + String.format("%03d", pokemon.id) + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(caminhoImagem));
        JLabel imagemLabel = new JLabel();
        if (icon != null) {
            imagemLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        } else {
            imagemLabel.setText("Sem Imagem");
            imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        JLabel nomeLabel = new JLabel(pokemon.nome, SwingConstants.CENTER);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        painel.add(imagemLabel, BorderLayout.CENTER);
        painel.add(nomeLabel, BorderLayout.SOUTH);
        return painel;
    }
    public static void abrirEmNovaAba(Pilha<Pokemon> pokemonsCapturados) {
        InterfaceSeusPokemons novaAba = new InterfaceSeusPokemons(pokemonsCapturados);
        novaAba.setVisible(true);
    }
}
