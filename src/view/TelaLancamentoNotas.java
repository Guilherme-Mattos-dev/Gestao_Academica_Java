package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controller.AlunoController;
import model.Aluno;
import model.Situacao;

public class TelaLancamentoNotas extends JFrame {

    private static final long serialVersionUID = 1L;

    private final AlunoController controller;
    private final JComboBox<AlunoItem> comboAlunos = new JComboBox<>();
    private final JTextField campoNota1 = new JTextField(7);
    private final JTextField campoNota2 = new JTextField(7);
    private final JTextField campoNota3 = new JTextField(7);
    private final JLabel labelMedia = new JLabel("Média: —");
    private final JLabel labelSituacao = new JLabel("Situação: —");
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new Object[]{"Matrícula", "Aluno", "Nota 1", "Nota 2", "Nota 3", "Média", "Situação"}, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public TelaLancamentoNotas(AlunoController controller) {
        this.controller = controller;
        setTitle("Lançamento de Notas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 560);
        setLocationRelativeTo(null);
        montarTela();
        carregarAlunos();
        atualizarTabela();
    }

    private void montarTela() {
        JPanel principal = new JPanel(new BorderLayout(12, 12));
        principal.setBorder(new EmptyBorder(18, 18, 18, 18));
        setContentPane(principal);

        JLabel titulo = new JLabel("Lançamento de Notas");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        principal.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 12));
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Dados do lançamento"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 7, 7, 7);
        c.anchor = GridBagConstraints.WEST;

        adicionarCampo(formulario, c, 0, "Aluno:", comboAlunos);
        adicionarCampo(formulario, c, 1, "Nota 1 (0 a 10):", campoNota1);
        adicionarCampo(formulario, c, 2, "Nota 2 (0 a 10):", campoNota2);
        adicionarCampo(formulario, c, 3, "Nota 3 (0 a 10):", campoNota3);

        JPanel resultado = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 5));
        resultado.add(labelMedia);
        resultado.add(labelSituacao);

        JButton botaoSalvar = new JButton("Salvar / Atualizar notas");
        JButton botaoLimpar = new JButton("Limpar");
        JButton botaoAtualizar = new JButton("Atualizar lista");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(botaoSalvar);
        botoes.add(botaoLimpar);
        botoes.add(botaoAtualizar);

        JPanel bloco = new JPanel(new BorderLayout(5, 5));
        bloco.add(formulario, BorderLayout.CENTER);
        bloco.add(resultado, BorderLayout.SOUTH);
        centro.add(bloco, BorderLayout.NORTH);

        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(25);
        centro.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.add(botoes, BorderLayout.WEST);
        principal.add(centro, BorderLayout.CENTER);
        principal.add(rodape, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(e -> salvarNotas());
        botaoLimpar.addActionListener(e -> limparCampos());
        botaoAtualizar.addActionListener(e -> {
            carregarAlunos();
            atualizarTabela();
        });
        comboAlunos.addActionListener(e -> preencherNotasDoAluno());
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints c, int linha,
                                String texto, JComponent campo) {
        c.gridx = 0;
        c.gridy = linha;
        painel.add(new JLabel(texto), c);
        c.gridx = 1;
        painel.add(campo, c);
    }

    private void carregarAlunos() {
        AlunoItem selecionado = (AlunoItem) comboAlunos.getSelectedItem();
        String matriculaSelecionada = selecionado == null ? null : selecionado.matricula;
        comboAlunos.removeAllItems();

        List<Aluno> alunos = controller.listarAlunos();
        for (Aluno aluno : alunos) {
            comboAlunos.addItem(new AlunoItem(aluno.getMatricula(), aluno.getNome()));
        }

        if (matriculaSelecionada != null) {
            for (int i = 0; i < comboAlunos.getItemCount(); i++) {
                if (comboAlunos.getItemAt(i).matricula.equals(matriculaSelecionada)) {
                    comboAlunos.setSelectedIndex(i);
                    break;
                }
            }
        }
        preencherNotasDoAluno();
    }

    private void preencherNotasDoAluno() {
        AlunoItem item = (AlunoItem) comboAlunos.getSelectedItem();
        if (item == null) {
            limparCampos();
            return;
        }

        Aluno aluno = controller.buscarPorMatricula(item.matricula);
        if (aluno == null) {
            return;
        }

        campoNota1.setText(notaParaTexto(aluno.getNota(0)));
        campoNota2.setText(notaParaTexto(aluno.getNota(1)));
        campoNota3.setText(notaParaTexto(aluno.getNota(2)));
        atualizarResumo(aluno);
    }

    private String notaParaTexto(double nota) {
        if (nota < 0) {
            return "";
        }
        return String.valueOf(nota);
    }

    private void salvarNotas() {
        AlunoItem item = (AlunoItem) comboAlunos.getSelectedItem();
        if (item == null) {
            mostrarErro("Cadastre ou selecione um aluno antes de lançar as notas.");
            return;
        }

        try {
            double n1 = converterNota(campoNota1.getText(), "Nota 1");
            double n2 = converterNota(campoNota2.getText(), "Nota 2");
            double n3 = converterNota(campoNota3.getText(), "Nota 3");

            controller.lancarOuAtualizarNotas(item.matricula, n1, n2, n3);

            Aluno aluno = controller.buscarPorMatricula(item.matricula);
            atualizarResumo(aluno);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Notas salvas com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            mostrarErro("Digite números válidos nos três campos. Exemplo: 7,5.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private double converterNota(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha " + campo + ".");
        }
        double valor = Double.parseDouble(texto.trim().replace(",", "."));
        if (!Double.isFinite(valor) || valor < 0 || valor > 10) {
            throw new IllegalArgumentException(campo + " deve estar entre 0 e 10.");
        }
        return valor;
    }

    private void atualizarResumo(Aluno aluno) {
        if (aluno == null || !aluno.temNotasLancadas()) {
            labelMedia.setText("Média: —");
            labelSituacao.setText("Situação: Pendente");
        } else {
            labelMedia.setText(String.format(java.util.Locale.forLanguageTag("pt-BR"),
                    "Média: %.2f", aluno.calcularMedia()));
            Situacao sit = aluno.getSituacao();
            labelSituacao.setText("Situação: " + (sit == null ? "Pendente" : sit));
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Aluno aluno : controller.listarAlunos()) {
            Object n1 = aluno.getNota(0) < 0 ? "—" : formatar(aluno.getNota(0));
            Object n2 = aluno.getNota(1) < 0 ? "—" : formatar(aluno.getNota(1));
            Object n3 = aluno.getNota(2) < 0 ? "—" : formatar(aluno.getNota(2));
            Object media = aluno.temNotasLancadas() ? formatar(aluno.calcularMedia()) : "—";
            Situacao sit = aluno.getSituacao();
            Object situacao = sit == null ? "Pendente" : sit.toString();

            modeloTabela.addRow(new Object[]{
                aluno.getMatricula(),
                aluno.getNome(),
                n1, n2, n3, media, situacao
            });
        }
    }

    private String formatar(double valor) {
        return String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", valor);
    }

    private void limparCampos() {
        campoNota1.setText("");
        campoNota2.setText("");
        campoNota3.setText("");
        labelMedia.setText("Média: —");
        labelSituacao.setText("Situação: —");
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private static class AlunoItem {
        private final String matricula;
        private final String nome;

        private AlunoItem(String matricula, String nome) {
            this.matricula = matricula;
            this.nome = nome;
        }

        @Override
        public String toString() {
            return nome + " (" + matricula + ")";
        }
    }
}
