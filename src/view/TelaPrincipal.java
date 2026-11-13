package view;

import javax.swing.*;
import java.awt.*;

import controller.AlunoController;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private final AlunoController alunoController;

    public TelaPrincipal() {
        alunoController = new AlunoController();

        setTitle("Sistema de Gestão Acadêmica");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel(
            "Sistema de Gestão Acadêmica", SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnCadastro = new JButton("Cadastro de Alunos");
        JButton btnNotas = new JButton("Lançamento de Notas");
        JButton btnConsulta = new JButton("Consulta de Resultados");
        JButton btnRelatorios = new JButton("Relatórios");

        btnCadastro.addActionListener(e ->
            new TelaCadastroAlunos(alunoController).setVisible(true));

        btnNotas.addActionListener(e ->
            new TelaLancamentoNotas(alunoController).setVisible(true));

        btnConsulta.addActionListener(e ->
            new TelaConsultaResultados(alunoController).setVisible(true));

        btnRelatorios.addActionListener(e ->
            new TelaResultado(alunoController).setVisible(true));

        JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 10, 10));
        painelBotoes.add(btnCadastro);
        painelBotoes.add(btnNotas);
        painelBotoes.add(btnConsulta);
        painelBotoes.add(btnRelatorios);

        setLayout(new BorderLayout(15, 15));
        add(titulo, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);

        JPanel painel = (JPanel) getContentPane();
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
