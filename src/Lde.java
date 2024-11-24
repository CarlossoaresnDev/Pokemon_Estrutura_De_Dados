public class Lde<T> {
    public No<T> primeiro;
    public int tamanhoAtual;

    public Lde() {
        this.primeiro = null;
        this.tamanhoAtual = 0;
    }

    public void adicionar(T elemento) {
        No<T> novoNo = new No<>(elemento);
        if (primeiro == null) {
            primeiro = novoNo;
        } else {
            No<T> atual = primeiro;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
        tamanhoAtual++;
    }

    public T get(int indice) {
        if (indice < 0 || indice >= tamanhoAtual) {
            return null;
        }
        No<T> atual = primeiro;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.dado;
    }

    public int tamanho() {
        return tamanhoAtual;
    }

    public void removerPorIndice(int indice) {
        if (indice < 0 || indice >= tamanhoAtual) {
            return;
        }
        if (indice == 0) {
            primeiro = primeiro.proximo;
        } else {
            No<T> anterior = null;
            No<T> atual = primeiro;
            for (int i = 0; i < indice; i++) {
                anterior = atual;
                atual = atual.proximo;
            }
            anterior.proximo = atual.proximo;
        }
        tamanhoAtual--;
    }

    public void removerPorElemento(T elemento) {
        No<T> anterior = null;
        No<T> atual = primeiro;
        while (atual != null && !atual.dado.equals(elemento)) {
            anterior = atual;
            atual = atual.proximo;
        }
        if (atual != null) {
            if (anterior == null) {
                primeiro = atual.proximo;
            } else {
                anterior.proximo = atual.proximo;
            }
            tamanhoAtual--;
        }
    }

    public boolean estaVazia() {
        return tamanhoAtual == 0;
    }

    public boolean contem(T elemento) {
        No<T> atual = primeiro;
        while (atual != null) {
            if (atual.dado.equals(elemento)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public void limpar() {
        primeiro = null;
        tamanhoAtual = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        No<T> atual = primeiro;
        while (atual != null) {
            sb.append(atual.dado);
            if (atual.proximo != null) {
                sb.append(", ");
            }
            atual = atual.proximo;
        }
        sb.append("]");
        return sb.toString();
    }

    class No<T> {
        public T dado;
        public No<T> proximo;

        public No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }
}