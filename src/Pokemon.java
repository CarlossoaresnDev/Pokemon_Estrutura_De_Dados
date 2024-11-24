public class Pokemon {
    String nome;
    String tipo;
    int id;
    Pokemon evolucaoDe;
    Lde<Pokemon> evolucoes;

    public Pokemon(String nome, String tipo, int id) {
        this.nome = nome;
        this.tipo = tipo;
        this.id = id;
        this.evolucoes = new Lde<>();
    }
    public void adicionarEvolucao(Pokemon evolucao) {
        evolucao.evolucaoDe = this;
        this.evolucoes.adicionar(evolucao);
    }
    public void removerEvolucao(Pokemon evolucao) {
        this.evolucoes.removerPorElemento(evolucao);
    }
    public String getNome() {
        return nome;
    }
    public String getTipo() {
        return tipo;
    }
    public int getIdImagem() {
        return id;
    }

}
