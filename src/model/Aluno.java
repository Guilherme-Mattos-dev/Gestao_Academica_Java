package model;

public class Aluno {

    private String matricula;
    private String nome;
    private String turma;
    private double[] notas = new double[3];

    public Aluno(String matricula, String nome, String turma) {
        this.matricula = matricula;
        this.nome = nome;
        this.turma = turma;

        for (int i = 0; i < notas.length; i++) {
            notas[i] = -1;
        }
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public double getNota(int indice) {
        return notas[indice];
    }

    public double[] getNotas() {
        return notas;
    }

    public void setNota(int indice, double nota) {
        if (indice < 0 || indice >= notas.length) {
            throw new IllegalArgumentException("Índice da nota deve ser 0, 1 ou 2");
        }
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        notas[indice] = nota;
    }

    public boolean temNotasLancadas() {
        for (double n : notas) {
            if (n == -1) {
                return false;
            }
        }
        return true;
    }

    public double calcularMedia() {
        if (!temNotasLancadas()) {
            return 0;
        }

        double soma = 0;
        for (double n : notas) {
            soma += n;
        }
        return soma / notas.length;
    }

    public Situacao getSituacao() {
        if (!temNotasLancadas()) {
            return null;
        }

        double media = calcularMedia();

        if (media >= 7) {
            return Situacao.APROVADO;
        } else if (media >= 5) {
            return Situacao.RECUPERACAO;
        } else {
            return Situacao.REPROVADO;
        }
    }

    @Override
    public String toString() {
        return "Matrícula: " + matricula + " | Nome: " + nome + " | Turma: " + turma;
    }
}
