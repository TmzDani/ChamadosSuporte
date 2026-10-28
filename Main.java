/**
 * Nucleo de uma central de chamados.
 *  Parte 1: fila de prioridade com heap maximo.
 *  Parte 2: Insertion Sort (estavel) x Selection Sort (instavel).
 *  Parte 3: comparar a ordem final dos tres metodos.
 */
public class Main {

    //Criamos sempre a quantidade de chamados ate o tamanho do vetor
    // prioridades como exemplificado no enunciado
    static Chamado[] criarChamados() {
        int[] prioridades = {3, 3, 5, 4, 5, 1, 4, 2};
        Chamado[] chamados = new Chamado[prioridades.length];
        for (int i = 0; i < prioridades.length; i++) {
            chamados[i] = new Chamado(i + 1, prioridades[i]);   // id = ordem de chegada
        }
        return chamados;
    }

    // ---------------------------------------------------------------
    // Item 2.1 - Insertion Sort, decrescente por prioridade
    // ---------------------------------------------------------------
    static int insertionSort(Chamado[] v) {
        int deslocamentos = 0;
        for (int i = 1; i < v.length; i++) {
            Chamado chave = v[i];
            int j = i - 1;
            // decrescente: andamos para tras enquanto o da esquerda for MENOR que a chave
            while (j >= 0 && v[j].prioridade < chave.prioridade) {
                v[j + 1] = v[j];      // desloca uma casa para a direita
                deslocamentos++;
                j--;
            }
            v[j + 1] = chave;         // encaixa a chave no buraco aberto
        }
        return deslocamentos;
    }

    // ---------------------------------------------------------------
    // Item 2.2 - Selection Sort, decrescente por prioridade
    // ---------------------------------------------------------------

    static int selectionSort(Chamado[] v) {
        int trocas = 0;
        for (int i = 0; i < v.length - 1; i++) {
            int posMaior = i;
            for (int j = i + 1; j < v.length; j++) {
                if (v[j].prioridade > v[posMaior].prioridade) {
                    posMaior = j;
                }
            }
            if (posMaior != i) {
                Chamado tmp = v[i];
                v[i] = v[posMaior];
                v[posMaior] = tmp;
                trocas++;
            }
        }
        return trocas;
    }

    // Imprime só os ids, ex.: "3 5 4 7 1 2 8 6"
    static void imprimirIds(String titulo, Chamado[] v) {
        System.out.print(titulo);
        for (Chamado c : v) {
            System.out.print(c.id + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Chamado[] chamados = criarChamados();

        // ===== PARTE 1 (item 1.4): fila de prioridade =====
        FilaPrioridade fila = new FilaPrioridade(chamados.length);
        for (Chamado c : chamados) {
            fila.inserir(c);                 // chegada na ordem 1..8
        }
        System.out.print("Vetor do heap: ");
        fila.imprimirVetor();

        // Remove todos: sai sempre o mais urgente (e, no empate, o mais antigo)
        Chamado[] ordemFila = new Chamado[chamados.length];
        System.out.print("Ordem de atendimento (heap): ");
        int k = 0;
        Chamado atual;
        while ((atual = fila.removerMaior()) != null) {
            System.out.print(atual + " ");
            ordemFila[k++] = atual;
        }
        System.out.println();

        // ===== PARTE 2: ordenações simples (sempre sobre uma CÓPIA) =====
        Chamado[] copiaInsertion = chamados.clone();
        int desloc = insertionSort(copiaInsertion);
        System.out.println("\nInsertion Sort: " + desloc + " deslocamentos");

        Chamado[] copiaSelection = chamados.clone();
        int trocas = selectionSort(copiaSelection);
        System.out.println("Selection Sort: " + trocas + " trocas");

        // Print final
        System.out.println();
        imprimirIds("Fila (heap):     ", ordemFila);
        imprimirIds("Insertion Sort:  ", copiaInsertion);
        imprimirIds("Selection Sort:  ", copiaSelection);
    }
}