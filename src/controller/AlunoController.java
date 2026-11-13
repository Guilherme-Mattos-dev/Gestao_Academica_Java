package controller;

import java.util.ArrayList;
import model.Aluno;

public class AlunoController {

    private ArrayList<Aluno> alunos;

    public AlunoController() {
        alunos = new ArrayList<>();
    }

    public boolean cadastrarAluno(Aluno aluno) {
        if (!validarAluno(aluno)) {
            return false;
        }

        if (buscarPorMatricula(aluno.getMatricula()) != null) {
            return false;
        }

        alunos.add(aluno);
        return true;
    }

    public ArrayList<Aluno> listarAlunos() {
        return new ArrayList<>(alunos);
    }

    public Aluno buscarPorMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return null;
        }

        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equalsIgnoreCase(matricula.trim())) {
                return aluno;
            }
        }
        return null;
    }

    public boolean editarAluno(String matriculaAtual, String novaMatricula,
                               String novoNome, String novaTurma) {
        Aluno aluno = buscarPorMatricula(matriculaAtual);

        if (aluno == null || novaMatricula == null || novaMatricula.trim().isEmpty()
                || novoNome == null || novoNome.trim().isEmpty()
                || novaTurma == null || novaTurma.trim().isEmpty()) {
            return false;
        }

        Aluno alunoExistente = buscarPorMatricula(novaMatricula);
        if (alunoExistente != null && alunoExistente != aluno) {
            return false;
        }

        aluno.setMatricula(novaMatricula.trim());
        aluno.setNome(novoNome.trim());
        aluno.setTurma(novaTurma.trim());
        return true;
    }

    public boolean excluirAluno(String matricula) {
        Aluno aluno = buscarPorMatricula(matricula);
        if (aluno == null) {
            return false;
        }
        alunos.remove(aluno);
        return true;
    }

    public boolean validarAluno(Aluno aluno) {
        return aluno != null
                && aluno.getMatricula() != null && !aluno.getMatricula().trim().isEmpty()
                && aluno.getNome() != null && !aluno.getNome().trim().isEmpty()
                && aluno.getTurma() != null && !aluno.getTurma().trim().isEmpty();
    }

    public int quantidadeAlunos() {
        return alunos.size();
    }

    public boolean listaVazia() {
        return alunos.isEmpty();
    }

    /**
     * Lança ou atualiza as três notas de um aluno já cadastrado.
     * Usa os métodos setNota da classe model.Aluno.
     */
    public void lancarOuAtualizarNotas(String matricula, double n1, double n2, double n3) {
        Aluno aluno = buscarPorMatricula(matricula);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }
        aluno.setNota(0, n1);
        aluno.setNota(1, n2);
        aluno.setNota(2, n3);
    }
}
