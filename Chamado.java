/**
 * Classe para representar um chamado
 *
 * Cada chamado tem:
 *  - id: organizacao da ordem dos chamados
 *  - prioridade: de 1 (baixa) a 5 (urgente)
 *
 * Regras do sistema:
 *  1) Primeiro se atende a maior prioridade
 *  2) Com mesma prioridade se faz primeiro o mais antigo
 */
public class Chamado {
    int id;           // ordem de chegada
    int prioridade;   // 1 (baixa) a 5 (urgente)

    public Chamado(int id, int prioridade) {
        this.id = id;
        this.prioridade = prioridade;
    }

    // Formatacao para mostrar o numero do chamado e sua prioridade
    @Override
    public String toString() {
        return "Chamado:" + id + "(Prioridade-" + prioridade + ")";
    }
}