
package controller;

import java.util.List;
import model.Aluno;
import model.Situacao;

public class RelatorioController {

    private double mediaGeral;
    private double menorMedia;
    private double maiorMedia;
    private int aprovados;
    private int reprovados;
    private int recuperacao;
    private int pendentes;
    private int totalAlunos;
    private int totalComNotas;

    /**
     * Gera o relatório a partir da lista de alunos.
     * Retorna true se havia alunos para processar, false caso contrário.
     */
    public boolean gerarRelatorio(List<Aluno> alunos) {

        // Reinicia os valores
        mediaGeral = 0;
        menorMedia = Double.MAX_VALUE;
        maiorMedia = Double.NEGATIVE_INFINITY;
        aprovados = 0;
        reprovados = 0;
        recuperacao = 0;
        pendentes = 0;
        totalComNotas = 0;

        if (alunos == null || alunos.isEmpty()) {
            totalAlunos = 0;
            return false;
        }

        totalAlunos = alunos.size();
        double somaMedias = 0;

        for (Aluno aluno : alunos) {

            Situacao situacao = aluno.getSituacao();

            // Verifica se o aluno ainda não possui todas as notas
            if (situacao == null) {
                pendentes++;
                continue;
            }

            double media = aluno.calcularMedia();

            somaMedias += media;
            totalComNotas++;

            // Identifica a menor média
            if (media < menorMedia) {
                menorMedia = media;
            }

            // Identifica a maior média
            if (media > maiorMedia) {
                maiorMedia = media;
            }

            // Conta os alunos de acordo com a situação
            if (situacao == Situacao.APROVADO) {
                aprovados++;

            } else if (situacao == Situacao.RECUPERACAO) {
                recuperacao++;

            } else if (situacao == Situacao.REPROVADO) {
                reprovados++;
            }
        }

        if (totalComNotas > 0) {
            mediaGeral = somaMedias / totalComNotas;
        }

        return true;
    }

    public double getMediaGeral() { return mediaGeral; }
    public double getMenorMedia() { return menorMedia; }
    public double getMaiorMedia() { return maiorMedia; }
    public int getAprovados() { return aprovados; }
    public int getReprovados() { return reprovados; }
    public int getRecuperacao() { return recuperacao; }
    public int getPendentes() { return pendentes; }
    public int getTotalAlunos() { return totalAlunos; }
    public int getTotalComNotas() { return totalComNotas; }
}
