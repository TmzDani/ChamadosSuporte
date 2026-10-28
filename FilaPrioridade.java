/**
 * Fila de prioridade implementada com um HEAP MÁXIMO guardado em um vetor.
 *
 * IDEIA: o heap é uma árvore binaria "completa" desenhada dentro de um vetor.
 * Nao ha ponteiros: a posição no vetor diz quem e pai e quem e filho.
 * A ordem de chegada importa
 *
 *
 * PROPRIEDADE DO HEAP MAXIMO: o pai tem prioridade sobre os filhos
 * afinal de nada adianta resolver o fim da linha sem resolver a ponta
 * Por conta disso o chamado mais urgente esta sempre na ponta
 *
 * So existem duas operações, e ambas custam O(log n), pois a arvore tem
 * altura log n e cada operação percorre no máximo um caminho raiz<->folha:
 *   - inserir: coloca no fim e SOBE enquanto for mais urgente que o pai;
 *   - removerMaior: tira a raiz, põe o último no lugar e DESCE.
 */
public class FilaPrioridade {
    private Chamado[] heap;   // vetor para a arvores
    private int tamanho;      // demonstracao de quantos chamados realmente existem

    public FilaPrioridade(int capacidade) {
        heap = new Chamado[capacidade];
        tamanho = 0;
    }

    // ---------------------------------------------------------------
    // Item 1.1 - Critério de comparação
    // ---------------------------------------------------------------
    /**
     * Devolve true se 'a' deve ser atendido ANTES de 'b'.
     *  - prioridade maior vence;
     *  - se as prioridades sao iguais, atende-se quem chegou antes
     *
     * Como o id NUNCA se repete, nunca ha empate total. Isso é o que faz o
     * heap respeitar a ordem de chegada mesmo ele sendo, por natureza, instavel.
     */
    private boolean temPrioridadeSobre(Chamado a, Chamado b) {
        if (a.prioridade != b.prioridade) {
            return a.prioridade > b.prioridade;   // maior prioridade ganha
        }
        return a.id < b.id;                       // empate: chegou antes ganha
    }

    // Troca dois elementos do vetor de posição metodo auxiliar para o sobe e desce
    private void trocar(int i, int j) {
        Chamado tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    // ---------------------------------------------------------------
    // Item 1.2 - Inserção
    // ---------------------------------------------------------------
    /**
     * 1) coloca o chamado na primeira posicao livre (fim do vetor);
     * 2) isso pode quebrar a propriedade do heap (o novo pode ser mais
     *    urgente que o pai), entao o chamado SOBE até ficar no lugar certo.
     */
    public void inserir(Chamado c) {
        if (tamanho == heap.length) {
            System.out.println("Fila cheia! Não foi possível inserir " + c);
            return;
        }
        heap[tamanho] = c;   // próxima posição livre
        subir(tamanho);      // conserta a ordem de baixo para cima
        tamanho++;           // agora o novo elemento faz parte do heap
    }

    private void subir(int i) {
        while (i > 0) {
            int pai = (i - 1) / 2;
            if (temPrioridadeSobre(heap[i], heap[pai])) {
                trocar(i, pai);
                i = pai;            // continua a subida a partir da posição do pai
            } else {
                break;              // pai ja e mais urgente: heap OK
            }
        }
    }

    // ---------------------------------------------------------------
    // Item 1.3 - Remoção do mais urgente
    // ---------------------------------------------------------------
    /**
     * 1) a raiz (posição 0) é sempre o mais urgente: guardamos para devolver;
     * 2) o ULTIMO elemento do vetor vai para a raiz (assim o vetor continua
     *    compacto, sem "buracos");
     * 3) como esse elemento veio de uma folha, provavelmente e pouco urgente,
     *    entao ele DESCE até achar seu lugar.
     */
    public Chamado removerMaior() {
        if (tamanho == 0) {
            return null;                 // fila vazia
        }
        Chamado maior = heap[0];
        tamanho--;                       // o heap encolheu uma posicao
        heap[0] = heap[tamanho];         // último vai para a raiz
        heap[tamanho] = null;            // limpa a posicao que sobrou
        descer(0);                       // conserta de cima para baixo
        return maior;
    }

    private void descer(int i) {
        while (true) {
            int esq = 2 * i + 1;
            int dir = 2 * i + 2;
            int maior = i;               // candidato a ficar nesta posição

            // o filho esquerdo existe e e mais urgente que o candidato atual?
            if (esq < tamanho && temPrioridadeSobre(heap[esq], heap[maior])) {
                maior = esq;
            }
            // o filho direito existe e e mais urgente que o melhor ate agora?
            if (dir < tamanho && temPrioridadeSobre(heap[dir], heap[maior])) {
                maior = dir;
            }

            if (maior == i) {
                break;                   // nenhum filho é mais urgente: heap OK
            }
            trocar(i, maior);
            i = maior;                   // continua a descida a partir do filho
        }
    }

    // Mostra o vetor do heap, nao representa a ordem de atendimento
    public void imprimirVetor() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}