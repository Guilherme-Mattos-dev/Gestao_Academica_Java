package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.AlunoController;
import controller.RelatorioController;

public class TelaResultado extends JFrame {

    private AlunoController alunoController;
    private RelatorioController relatorioController;
    private JTextArea areaResultado;

    public TelaResultado(AlunoController alunoController) {

        this.alunoController = alunoController;
        this.relatorioController = new RelatorioController();

        setTitle("Relatório Geral de Resultados");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(
                new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14)
        );

        JScrollPane scroll = new JScrollPane(areaResultado);

        JButton btnAtualizar = new JButton("Atualizar Relatório");
        JButton btnFechar = new JButton("Fechar");

        btnAtualizar.addActionListener(e -> gerarRelatorio());
        btnFechar.addActionListener(e -> dispose());

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        gerarRelatorio();
    }

    private void gerarRelatorio() {

        boolean temAlunos = relatorioController.gerarRelatorio(
                alunoController.listarAlunos()
        );

        if (!temAlunos) {
            areaResultado.setText("Nenhum aluno cadastrado.");
            return;
        }

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("====================================\n");
        relatorio.append("       RELATÓRIO GERAL\n");
        relatorio.append("====================================\n\n");

        if (relatorioController.getTotalComNotas() > 0) {

            relatorio.append(String.format(
                    "Média geral: %.2f%n", relatorioController.getMediaGeral()
            ));

            relatorio.append(String.format(
                    "Menor média: %.2f%n", relatorioController.getMenorMedia()
            ));

            relatorio.append(String.format(
                    "Maior média: %.2f%n", relatorioController.getMaiorMedia()
            ));

        } else {
            relatorio.append("Não existem médias disponíveis.\n");
        }

        relatorio.append("\n====================================\n");
        relatorio.append("    QUANTIDADE POR SITUAÇÃO\n");
        relatorio.append("====================================\n\n");

        relatorio.append("Aprovados: " + relatorioController.getAprovados() + "\n");
        relatorio.append("Em recuperação: " + relatorioController.getRecuperacao() + "\n");
        relatorio.append("Reprovados: " + relatorioController.getReprovados() + "\n");
        relatorio.append("Pendentes: " + relatorioController.getPendentes() + "\n");

        relatorio.append("\n------------------------------------\n");
        relatorio.append("Total de alunos: " + relatorioController.getTotalAlunos() + "\n");

        areaResultado.setText(relatorio.toString());
        areaResultado.setCaretPosition(0);
    }
}
