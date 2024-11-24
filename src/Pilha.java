public class Pilha<T> {
    private No<T> topo;
    private int tamanho;

    public Pilha() {
        topo = null;
        tamanho = 0;
    }

    public void push(T item) {
        No<T> novo = new No<>(item);
        novo.next = topo;
        topo = novo;
        tamanho++;
    }

    public T pop() {
        if (!isEmpty()) {
            No<T> item = topo;
            topo = topo.next;
            item.next = null;
            tamanho--;
            return item.value;
        }
        return null;
    }

    public T peek() {
        if (!isEmpty()) {
            return topo.value;
        }
        return null;
    }

    public void exibir() {
        No<T> atual = topo;
        while (atual != null) {
            System.out.print(atual.value.toString() + " ");
            atual = atual.next;
        }
        System.out.println();
    }

    public int size() {
        return tamanho;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public void inverter() {
        Pilha<T> pilhaAux1 = new Pilha<>();
        Pilha<T> pilhaAux2 = new Pilha<>();

        while (!isEmpty()) {
            pilhaAux1.push(pop());
        }

        while (!pilhaAux1.isEmpty()) {
            pilhaAux2.push(pilhaAux1.pop());
        }

        while (!pilhaAux2.isEmpty()) {
            push(pilhaAux2.pop());
        }
    }


    public void ordena() {
        Pilha<T> pilhaAux1 = new Pilha<>();
        Pilha<T> pilhaAux2 = new Pilha<>();

        while (!isEmpty()) {
            T menor = peek();

            while (!isEmpty()) {
                T valor = pop();
                if (((Comparable<T>)valor).compareTo(menor) < 0)
                    menor = valor;
                pilhaAux1.push(valor);
            }

            while (!pilhaAux1.isEmpty()) {
                T valor = pilhaAux1.pop();
                if (valor.equals(menor))
                    pilhaAux2.push(valor);
                else
                    push(valor);
            }
        }

        while (!pilhaAux2.isEmpty()) {
            push(pilhaAux2.pop());
        }
    }

    public boolean palindrome(String palavra) {
        Pilha<Character> pilha1 = new Pilha<>();
        Pilha<Character> pilha2 = new Pilha<>();

        for (int i = 0; i < (palavra.length() + 1) / 2; i++) {
            pilha1.push(palavra.charAt(i));
            pilha2.push(palavra.charAt(palavra.length() - (i + 1)));
        }

        while (!pilha1.isEmpty()) {
            if (!pilha1.pop().equals(pilha2.pop()))
                return false;
        }
        return true;
    }

    public boolean expressao(String expressao) {
        Pilha<Character> pilha = new Pilha<>();

        for (int i = 0; i < expressao.length(); i++) {
            char caracter = expressao.charAt(i);
            switch (caracter) {
                case '(':
                    pilha.push(')');
                    break;
                case '[':
                    pilha.push(']');
                    break;
                case '{':
                    pilha.push('}');
                    break;
                case ')':
                case ']':
                case '}':
                    if (pilha.isEmpty() || pilha.pop() != caracter)
                        return false;
                    break;
            }
        }
        return pilha.isEmpty();
    }

    public void eliminarMaior() {
        Pilha<T> pilhaAux = new Pilha<>();
        T maior = peek();

        while (!isEmpty()) {
            T valor = pop();
            if (((Comparable<T>)valor).compareTo(maior) > 0)
                maior = valor;
            pilhaAux.push(valor);
        }

        while (!pilhaAux.isEmpty()) {
            T valor = pilhaAux.pop();
            if (!valor.equals(maior))
                push(valor);
        }
    }

    public void eliminarMenor() {
        Pilha<T> pilhaAux = new Pilha<>();
        T menor = peek();
        while (!isEmpty()) {
            T valor = pop();
            if (((Comparable<T>)valor).compareTo(menor) < 0)
                menor = valor;
            pilhaAux.push(valor);
        }
        while (!pilhaAux.isEmpty()) {
            T valor = pilhaAux.pop();
            if (!valor.equals(menor))
                push(valor);
        }
    }

    public void eliminarMaiorMenor() {
        Pilha<T> pilhaAux = new Pilha<>();
        T maior = peek();
        T menor = peek();

        while (!isEmpty()) {
            T valor = pop();
            if (((Comparable<T>)valor).compareTo(maior) > 0)
                maior = valor;
            else if (((Comparable<T>)valor).compareTo(menor) < 0)
                menor = valor;
            pilhaAux.push(valor);
        }

        while (!pilhaAux.isEmpty()) {
            T valor = pilhaAux.pop();
            if (!valor.equals(maior) && !valor.equals(menor))
                push(valor);
        }
    }

    public class No<T> {
        T value;
        No<T> next;

        public No(T value) {
            this.value = value;
            this.next = null;
        }
    }

}