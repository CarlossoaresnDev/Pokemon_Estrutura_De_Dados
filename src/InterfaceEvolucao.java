import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfaceEvolucao extends JFrame {
    private final ArvoreEvolutiva arvore;
    private final JLabel imagemLabel;
    private final JLabel infoLabel;
    private final JPanel arvorePanel;
    private Pokemon pokemonSelecionado;
    private final Pilha<Pokemon> pilhaCapturados = new Pilha<>();

    private int mouseX = 0;
    private int mouseY = 0;
    private int offsetX = 0;
    private int offsetY = 0;

    public InterfaceEvolucao(ArvoreEvolutiva arvore) {
        this.arvore = arvore;
        setTitle("Árvore Evolutiva de Pokémon");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        imagemLabel = new JLabel();
        imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagemLabel.setPreferredSize(new Dimension(200, 200));
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imagemLabel, BorderLayout.CENTER);

        infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
        infoLabel.setPreferredSize(new Dimension(200, 100));
        imagePanel.add(infoLabel, BorderLayout.SOUTH);
        add(imagePanel, BorderLayout.EAST);

        arvorePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharRamos(g, arvore.raiz, getWidth() / 2 + offsetX, 30 + offsetY, getWidth() / 8);
            }
        };
        arvorePanel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(arvorePanel);
        scrollPane.setPreferredSize(new Dimension(750, 600));
        add(scrollPane, BorderLayout.CENTER);
        JButton mostrarArvoreBtn = new JButton("Atualizar Árvore");
        mostrarArvoreBtn.addActionListener(e -> {
            arvorePanel.removeAll();
            exibirArvoreComBotoes(arvore.raiz, scrollPane.getWidth() / 2, 30, scrollPane.getWidth() / 8);
            arvorePanel.revalidate();
            arvorePanel.repaint();
        });
        add(mostrarArvoreBtn, BorderLayout.NORTH);

        JPanel crudPanel = new JPanel(new FlowLayout());

        JButton adicionarBtn = new JButton("Adicionar");
        JButton removerBtn = new JButton("Remover");
        JButton editarBtn = new JButton("Editar");
        JButton capturarBtn = new JButton("Capturar");
        JButton visualizarCapturadosBtn = new JButton("Ver Capturados");

        adicionarBtn.addActionListener(e -> adicionarPokemon());
        removerBtn.addActionListener(e -> removerPokemon());
        editarBtn.addActionListener(e -> editarPokemon());
        capturarBtn.addActionListener(e -> capturarPokemon());
        visualizarCapturadosBtn.addActionListener(e -> visualizarCapturados());

        crudPanel.add(adicionarBtn);
        crudPanel.add(removerBtn);
        crudPanel.add(editarBtn);
        crudPanel.add(capturarBtn);
        crudPanel.add(visualizarCapturadosBtn);

        add(crudPanel, BorderLayout.SOUTH);

        arvorePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        arvorePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                offsetX += e.getX() - mouseX;
                offsetY += e.getY() - mouseY;
                mouseX = e.getX();
                mouseY = e.getY();
                arvorePanel.removeAll();
                exibirArvoreComBotoes(arvore.raiz, arvorePanel.getWidth() / 2 + offsetX, 30 + offsetY, arvorePanel.getWidth() / 8);
                arvorePanel.revalidate();
                arvorePanel.repaint();
            }
        });
    }

    private void capturarPokemon() {
        if (pokemonSelecionado != null) {
            pilhaCapturados.push(pokemonSelecionado);
            JOptionPane.showMessageDialog(this, pokemonSelecionado.nome + " foi capturado!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um Pokémon para capturar.");
        }
    }
    private void visualizarCapturados() {
        if (pilhaCapturados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum Pokémon capturado ainda.");
            return;
        }
        Pilha<Pokemon> copiaPilha = new Pilha<>();
        Pilha<Pokemon> auxiliar = new Pilha<>();
        while (!pilhaCapturados.isEmpty()) {
            Pokemon pokemon = pilhaCapturados.pop();
            copiaPilha.push(pokemon);
            auxiliar.push(pokemon);
        }
        while (!auxiliar.isEmpty()) {
            pilhaCapturados.push(auxiliar.pop());
        }
        InterfaceSeusPokemons seusPokemonsInterface = new InterfaceSeusPokemons(copiaPilha);
        seusPokemonsInterface.setVisible(true);
    }


    private void exibirArvoreComBotoes(Pokemon pokemon, int x, int y, int xOffset) {
        if (pokemon == null) return;

        JButton pokemonButton = new JButton(pokemon.nome);
        pokemonButton.setBounds(x - 50 + offsetX, y - 15 + offsetY, 100, 30);
        pokemonButton.addActionListener(e -> {
            pokemonSelecionado = pokemon;
            exibirInformacoes(pokemon);
        });
        arvorePanel.add(pokemonButton);

        int childY = y + 70;
        int childXOffset = xOffset / 2;

        for (int i = 0; i < pokemon.evolucoes.tamanho(); i++) {
            Pokemon evolucao = pokemon.evolucoes.get(i);
            int childX = x - xOffset + i * xOffset * 2;
            exibirArvoreComBotoes(evolucao, childX, childY, childXOffset);
        }
    }

    private void desenharRamos(Graphics g, Pokemon pokemon, int x, int y, int xOffset) {
        if (pokemon == null) return;

        int childY = y + 70;
        int childXOffset = xOffset / 2;

        for (int i = 0; i < pokemon.evolucoes.tamanho(); i++) {
            Pokemon evolucao = pokemon.evolucoes.get(i);
            int childX = x - xOffset + i * xOffset * 2;

            g.drawLine(x, y + 15, childX, childY - 15);
            desenharRamos(g, evolucao, childX, childY, childXOffset);
        }
    }

    private void exibirInformacoes(Pokemon pokemon) {
        String caminhoImagem = "/imagens/" + String.format("%03d", pokemon.id) + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(caminhoImagem));
        if (icon != null) {
            imagemLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        } else {
            imagemLabel.setIcon(null);
        }

        StringBuilder info = new StringBuilder("<html><div style='text-align: center;'>");
        info.append("<strong>Nome:</strong> ").append(pokemon.nome).append("<br>");
        info.append("<strong>Tipo:</strong> ").append(pokemon.tipo).append("<br>");
        if (pokemon.evolucaoDe != null) {
            info.append("<strong>Evolução de:</strong> ").append(pokemon.evolucaoDe.nome).append("<br>");
        } else {
            info.append("<strong>Raiz da árvore evolutiva</strong><br>");
        }
        info.append("</div></html>");
        infoLabel.setText(info.toString());
    }

    private void adicionarPokemon() {
        String nome = JOptionPane.showInputDialog(this, "Nome do novo Pokémon:");
        String tipo = JOptionPane.showInputDialog(this, "Tipo do Pokémon:");
        int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID do Pokémon:"));

        Pokemon novoPokemon = new Pokemon(nome, tipo, id);
        if (pokemonSelecionado != null) {
            pokemonSelecionado.adicionarEvolucao(novoPokemon);
        } else {
            arvore.raiz = novoPokemon;
        }
        JOptionPane.showMessageDialog(this, "Pokémon adicionado com sucesso!");
        arvorePanel.repaint();
    }

    private void removerPokemon() {
        if (pokemonSelecionado != null) {
            Pokemon origem = pokemonSelecionado.evolucaoDe;
            if (origem != null) {
                origem.removerEvolucao(pokemonSelecionado);
                pokemonSelecionado = null;
                arvorePanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Não é possível remover a raiz.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um Pokémon para remover.");
        }
    }

    private void editarPokemon() {
        if (pokemonSelecionado != null) {
            String novoNome = JOptionPane.showInputDialog(this, "Novo nome do Pokémon:", pokemonSelecionado.nome);
            String novoTipo = JOptionPane.showInputDialog(this, "Novo tipo do Pokémon:", pokemonSelecionado.tipo);

            if (novoNome != null && !novoNome.isEmpty()) {
                pokemonSelecionado.nome = novoNome;
            }
            if (novoTipo != null && !novoTipo.isEmpty()) {
                pokemonSelecionado.tipo = novoTipo;
            }
            JOptionPane.showMessageDialog(this, "Pokémon editado com sucesso!");
            exibirInformacoes(pokemonSelecionado);
            arvorePanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um Pokémon para editar.");
        }
    }

    public static void main(String[] args) {
        Pokemon arceus = new Pokemon("Arceus", "Normal", 493);
        Pokemon squirtle = new Pokemon("Squirtle", "Água", 7);
        Pokemon wartortle = new Pokemon("Wartortle", "Água", 8);
        Pokemon blastoise = new Pokemon("Blastoise", "Água", 9);
        squirtle.adicionarEvolucao(wartortle);
        wartortle.adicionarEvolucao(blastoise);
        arceus.adicionarEvolucao(squirtle);
        ArvoreEvolutiva arvore = new ArvoreEvolutiva(arceus);
        InterfaceEvolucao interfaceEvolucao = new InterfaceEvolucao(arvore);
        interfaceEvolucao.setVisible(true);
    }
}
