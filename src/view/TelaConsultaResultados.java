
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.AlunoController;
import model.Aluno;
import model.Situacao;

public class TelaConsultaResultados extends JFrame {

    private AlunoController alunoController;

    private JTextField campoMatricula;
    private JTextArea areaResultado;

    public TelaConsultaResultados(AlunoController alunoController) {

        this.alunoController = alunoController;

        setTitle("Consulta de Resultados");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel de pesquisa
        JPanel painelPesquisa = new JPanel(new FlowLayout());

        JLabel labelMatricula = new JLabel("Matrícula:");

        campoMatricula = new JTextField(15);

        JButton btnConsultar = new JButton("Consultar");

        painelPesquisa.add(labelMatricula);
        painelPesquisa.add(campoMatricula);
        painelPesquisa.add(btnConsultar);

        // Área de exibição dos resultados
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(
                new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14)
        );

        JScrollPane scroll = new JScrollPane(areaResultado);

        // Botão fechar
        JButton btnFechar = new JButton("Fechar");

        JPanel painelInferior = new JPanel(new FlowLayout());
        painelInferior.add(btnFechar);

        // Eventos dos botões
        btnConsultar.addActionListener(e -> consultarAluno());

        campoMatricula.addActionListener(e -> consultarAluno());

        btnFechar.addActionListener(e -> dispose());

        add(painelPesquisa, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private void consultarAluno() {

        String matricula = campoMatricula.getText().trim();

        if (matricula.isEmpty()) {
            areaResultado.setText("Digite a matrícula do aluno.");
            return;
        }

        Aluno aluno = alunoController.buscarPorMatricula(matricula);

        if (aluno == null) {
            areaResultado.setText("Aluno não encontrado.");
            return;
        }

        StringBuilder resultado = new StringBuilder();

        resultado.append("====================================\n");
        resultado.append("       RESULTADO DO ALUNO\n");
        resultado.append("====================================\n\n");

        resultado.append("Matrícula: " + aluno.getMatricula() + "\n");
        resultado.append("Nome: " + aluno.getNome() + "\n");
        resultado.append("Turma: " + aluno.getTurma() + "\n");

        resultado.append("\n------------------------------------\n");
        resultado.append("             NOTAS\n");
        resultado.append("------------------------------------\n");

        for (int i = 0; i < 3; i++) {

            double nota = aluno.getNota(i);

            if (nota == -1) {
                resultado.append("Nota " + (i + 1) + ": Pendente\n");
            } else {
                resultado.append(String.format(
                        "Nota %d: %.2f%n", i + 1, nota
                ));
            }
        }

        resultado.append("\n------------------------------------\n");

        Situacao situacao = aluno.getSituacao();

        if (situacao == null) {

            resultado.append("Média: Não disponível\n");
            resultado.append("Situação: Pendente\n");

        } else {

            resultado.append(String.format(
                    "Média: %.2f%n", aluno.calcularMedia()
            ));

            if (situacao == Situacao.APROVADO) {
                resultado.append("Situação: Aprovado\n");

            } else if (situacao == Situacao.RECUPERACAO) {
                resultado.append("Situação: Em recuperação\n");

            } else if (situacao == Situacao.REPROVADO) {
                resultado.append("Situação: Reprovado\n");
            }
        }

        resultado.append("------------------------------------\n");

        areaResultado.setText(resultado.toString());
        areaResultado.setCaretPosition(0);
    }
}
