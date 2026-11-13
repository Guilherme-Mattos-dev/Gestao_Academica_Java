# Sistema de Gestão Acadêmica

## Grupo 03 — Engenharia de Software (2º Semestre)

### Integrantes

| Nome | Responsabilidade |
|------|-----------------|
| Guilherme Mattos dos Santos | Model: classe `Aluno`, enum `Situacao`, `calcularMedia()`, `getSituacao()`, validação de notas |
| Phillipe Santana Gonzaga | Controller: `AlunoController` — cadastrar, listar, buscar, editar, excluir e lançar notas |
| Leonardo Lisboa dos Santos | View: `TelaPrincipal` (navegação) e `TelaCadastroAlunos` com JTable, editar e excluir |
| João Marcos dos Santos Antunes | View: `TelaLancamentoNotas` — escolher aluno, lançar e corrigir as 3 notas, mensagens de erro |
| Douglas Gabriel Barboza Freitas | Controller: `RelatorioController` — média geral, maior média, menor média, quantidade por situação |
| Renato Campos Lopes | View: `TelaConsultaResultados` (consulta individual) e `TelaResultado` (relatório geral) |

---

## Descrição do Projeto

Sistema desktop desenvolvido em **Java puro** com interface gráfica **Swing**, seguindo a arquitetura **MVC (Model–View–Controller)**.

O sistema permite o gerenciamento acadêmico de alunos de uma disciplina, incluindo cadastro, lançamento de notas, consulta de resultados individuais e geração de relatório geral da turma.

Os dados são armazenados em memória utilizando **ArrayList** durante a execução da aplicação.

---

## Funcionalidades Implementadas

### 1. Cadastro de Alunos
- Registrar matrícula, nome completo e turma.
- Listar todos os alunos cadastrados em uma JTable.
- Editar informações de alunos existentes.
- Excluir alunos com confirmação.
- Validação: impede matrícula duplicada e campos vazios.

### 2. Lançamento de Notas
- Selecionar aluno cadastrado via JComboBox.
- Registrar três avaliações (Nota 1, Nota 2, Nota 3).
- Atualizar notas já lançadas.
- Cálculo automático da média aritmética.
- Classificação automática da situação (Aprovado, Recuperação, Reprovado).
- Tabela de acompanhamento com todos os alunos e suas notas.

### 3. Consulta de Resultados
- Buscar aluno por matrícula.
- Exibir notas individuais, média e situação.
- Identificar notas pendentes (ainda não lançadas).

### 4. Relatório Acadêmico
- Média geral da turma.
- Maior e menor média.
- Quantidade de alunos por situação: aprovados, em recuperação, reprovados e pendentes.
- Total de alunos cadastrados.

---

## Regras de Negócio

| Regra | Descrição |
|-------|-----------|
| Notas | Devem estar entre **0** e **10** |
| Matrícula | Não pode ser duplicada |
| Campos obrigatórios | Matrícula, nome e turma são obrigatórios no cadastro |
| Aprovado | Média **≥ 7** |
| Em Recuperação | Média **≥ 5** e **< 7** |
| Reprovado | Média **< 5** |
| Pendente | Quando as três notas ainda não foram lançadas |

---

## Arquitetura MVC

```
src/
├── model/
│   ├── Aluno.java            ← Dados do aluno, cálculo de média e situação
│   └── Situacao.java         ← Enum: APROVADO, RECUPERACAO, REPROVADO
│
├── controller/
│   ├── AlunoController.java  ← CRUD de alunos e lançamento de notas
│   └── RelatorioController.java ← Cálculos do relatório geral
│
├── view/
│   ├── TelaPrincipal.java         ← Tela principal com navegação
│   ├── TelaCadastroAlunos.java    ← Cadastro, edição e exclusão de alunos
│   ├── TelaLancamentoNotas.java   ← Lançamento e atualização de notas
│   ├── TelaConsultaResultados.java ← Consulta individual por matrícula
│   └── TelaResultado.java         ← Relatório geral da turma
│
└── TesteAluno.java           ← Testes de validação do Model
```

**Model:** responsável pelos dados e regras de negócio (validação de notas, cálculo de média, classificação).

**Controller:** coordena as operações entre a View e o Model (cadastro, busca, edição, exclusão, lançamento de notas e geração de relatórios).

**View:** interface gráfica Swing que exibe informações e recebe ações do usuário, sem conter lógica de negócio.

---

## Tecnologias Utilizadas

- **Linguagem:** Java SE
- **Interface gráfica:** Java Swing (JFrame, JPanel, JTextField, JButton, JTable, JComboBox, JOptionPane, JTextArea)
- **Armazenamento:** ArrayList em memória
- **Padrão arquitetural:** MVC

---

## Como Importar no Eclipse

1. Extraia o arquivo ZIP.
2. No Eclipse, acesse **File > Import**.
3. Escolha **General > Existing Projects into Workspace**.
4. Selecione a pasta `GestaoAcademica_Integrado`.
5. Marque o projeto encontrado e clique em **Finish**.

---

## Como Executar

1. Abra o arquivo `src/view/TelaPrincipal.java`.
2. Clique com o botão direito e escolha **Run As > Java Application**.
3. A tela principal será exibida com quatro opções de navegação.

---

## Testes Realizados

### Teste 1 — Cadastro e validação de matrícula duplicada
**O que foi testado:** Cadastrar dois alunos com a mesma matrícula.
**Resultado:** O sistema impediu o cadastro duplicado e exibiu mensagem de aviso, conforme esperado.

### Teste 2 — Validação de notas fora do intervalo
**O que foi testado:** Lançar nota com valor 11 (acima do máximo permitido).
**Resultado:** O sistema rejeitou a nota e exibiu mensagem informando que a nota deve estar entre 0 e 10.

### Teste 3 — Cálculo de média e classificação
**O que foi testado:** Lançar notas para verificar cada situação possível.
**Resultados:**
- Notas 7, 7, 7 → Média 7.0 → **APROVADO** ✔
- Notas 5, 5, 5 → Média 5.0 → **RECUPERAÇÃO** ✔
- Notas 4, 5, 5 → Média 4.67 → **REPROVADO** ✔
- Notas 10, 10, 10 → Média 10.0 → **APROVADO** ✔
- Notas 0, 0, 0 → Média 0.0 → **REPROVADO** ✔

### Teste 4 — Relatório com alunos sem notas
**O que foi testado:** Gerar relatório quando há alunos cadastrados mas sem notas lançadas.
**Resultado:** O sistema exibiu a mensagem "Não existem médias disponíveis" e contabilizou os alunos como "Pendentes", conforme esperado.

---

## Observações

- Os dados ficam armazenados em memória enquanto a aplicação está aberta.
- Ao fechar o programa, os cadastros são perdidos (não há banco de dados ou persistência em arquivo).
- Um único `AlunoController` é compartilhado entre todas as telas, garantindo que os dados sejam consistentes em toda a aplicação.
