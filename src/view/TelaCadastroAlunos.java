package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import controller.AlunoController;
import model.Aluno;

public class TelaCadastroAlunos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField campoMatricula;
    private JTextField campoNome;
    private JTextField campoTurma;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private AlunoController alunoController;

    public TelaCadastroAlunos(AlunoController alunoController) {
        this.alunoController = alunoController;

        setTitle("Cadastro de Alunos");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        campoMatricula = new JTextField(15);
        campoNome = new JTextField(20);
        campoTurma = new JTextField(15);

        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        painelCampos.add(new JLabel("Matrícula:"));
        painelCampos.add(campoMatricula);
        painelCampos.add(new JLabel("Nome:"));
        painelCampos.add(campoNome);
        painelCampos.add(new JLabel("Turma:"));
        painelCampos.add(campoTurma);

        modeloTabela = new DefaultTableModel(
            new Object[]{"Matrícula", "Nome", "Turma"}, 0
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane painelTabela = new JScrollPane(tabela);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCampos();
            }
        });

        setLayout(new BorderLayout(10, 10));
        add(painelCampos, BorderLayout.NORTH);
        add(painelTabela, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabela();
    }

    private void cadastrar() {
        String matricula = campoMatricula.getText().trim();
        String nome = campoNome.getText().trim();
        String turma = campoTurma.getText().trim();

        Aluno aluno = new Aluno(matricula, nome, turma);

        if (alunoController.cadastrarAluno(aluno)) {
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this,
                "Aluno cadastrado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos e use uma matrícula ainda não cadastrada.",
                "Não foi possível cadastrar",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editar() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um aluno na tabela para editar.");
            return;
        }

        String matriculaOriginal =
            modeloTabela.getValueAt(linha, 0).toString();

        boolean editado = alunoController.editarAluno(
            matriculaOriginal,
            campoMatricula.getText().trim(),
            campoNome.getText().trim(),
            campoTurma.getText().trim()
        );

        if (editado) {
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this,
                "Aluno atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this,
                "Confira os campos e verifique se a matrícula não pertence a outro aluno.",
                "Não foi possível editar",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluir() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                "Selecione um aluno na tabela para excluir.");
            return;
        }

        String matricula =
            modeloTabela.getValueAt(linha, 0).toString();

        int confirmacao = JOptionPane.showOptionDialog(
            this,
            "Deseja realmente excluir o aluno selecionado?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new Object[]{"Sim", "Não"},
            "Sim"
        );

        if (confirmacao == 0) {
            if (alunoController.excluirAluno(matricula)) {
                atualizarTabela();
                limparCampos();

                JOptionPane.showMessageDialog(this,
                    "Aluno excluído com sucesso!");
            }
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (Aluno aluno : alunoController.listarAlunos()) {
            modeloTabela.addRow(new Object[]{
                aluno.getMatricula(),
                aluno.getNome(),
                aluno.getTurma()
            });
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            campoMatricula.setText(
                modeloTabela.getValueAt(linha, 0).toString());
            campoNome.setText(
                modeloTabela.getValueAt(linha, 1).toString());
            campoTurma.setText(
                modeloTabela.getValueAt(linha, 2).toString());
        }
    }

    private void limparCampos() {
        campoMatricula.setText("");
        campoNome.setText("");
        campoTurma.setText("");
        tabela.clearSelection();
    }
}
