import model.Aluno;

public class TesteAluno {

    private static void testar(String descricao, double n1, double n2, double n3) {
        Aluno aluno = new Aluno("001", "Teste", "A");
        aluno.setNota(0, n1);
        aluno.setNota(1, n2);
        aluno.setNota(2, n3);
        System.out.println(descricao + " -> média " + aluno.calcularMedia()
                + " | " + aluno.getSituacao());
    }

    public static void main(String[] args) {
        testar("7, 7, 7 (esperado APROVADO)", 7, 7, 7);
        testar("5, 5, 5 (esperado RECUPERACAO)", 5, 5, 5);
        testar("4, 5, 5 (esperado REPROVADO)", 4, 5, 5);
        testar("10, 10, 10 (esperado APROVADO)", 10, 10, 10);
        testar("0, 0, 0 (esperado REPROVADO)", 0, 0, 0);

        try {
            Aluno aluno = new Aluno("002", "Teste2", "A");
            aluno.setNota(0, 11);
            System.out.println("FALHOU: nota 11 deveria gerar erro");
        } catch (IllegalArgumentException e) {
            System.out.println("OK nota 11: " + e.getMessage());
        }

        Aluno parcial = new Aluno("003", "Teste3", "A");
        parcial.setNota(0, 8);
        parcial.setNota(1, 9);
        System.out.println("Somente duas notas lançadas: " + parcial.temNotasLancadas()
                + " | média: " + parcial.calcularMedia());
    }
}
