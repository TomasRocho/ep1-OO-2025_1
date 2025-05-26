package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class ServicoAlunoMatriculado extends ClasseServicoBase{

    final String nomeArquivo="alunoMatriculado.txt";

    //CLASSE SINGLETON
    private static ServicoAlunoMatriculado instance = null;
    private ServicoAlunoMatriculado(){}
    public static ServicoAlunoMatriculado getInstance() {
        if (instance == null) {
            instance = new ServicoAlunoMatriculado();
        }
        return instance;
    }


    @Override
    public boolean podeIncluir(ClasseBase alunoMatriculado) throws Exception{

        alunoMatriculado.validar();

        if (this.existe(alunoMatriculado.getId())){
            throw new Exception("Impossível Incluir Aluno matriculado - id duplicado");
        }
        if (this.matriculaDuplicada((AlunoMatriculado) alunoMatriculado,false)){
            throw new Exception("Impossível Incluir Aluno matriculado - aluno e turma já cadastrados");
        }

        if (!this.possuiVagasDisponiveis(((AlunoMatriculado) alunoMatriculado).getTurma())){
            throw new Exception("Impossível Incluir Aluno matriculado - vagas esgotadas");
        }

        //preenche a listaMatriculas do aluno informado
        ((AlunoMatriculado) alunoMatriculado).getAluno().setListaMatriculas(this.getListaMatriculasPorAluno(((AlunoMatriculado) alunoMatriculado).getAluno().getId(),null));

        if (((AlunoMatriculado) alunoMatriculado).getAluno().disciplinaConcluida(((AlunoMatriculado) alunoMatriculado).getTurma().getDisciplina())){
            throw new Exception("Impossível Incluir Aluno matriculado - aluno já concluiu esta disciplina");
        }
        if (!this.possuiPreRequisitos(((AlunoMatriculado) alunoMatriculado).getAluno(),((AlunoMatriculado) alunoMatriculado).getTurma().getDisciplina())){
            throw new Exception("Impossível Incluir Aluno matriculado - aluno não possui pré-requisitos");
        }


        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase alunoMatriculado) throws Exception{

        alunoMatriculado.validar();

        if (this.matriculaDuplicada((AlunoMatriculado) alunoMatriculado,true)){
            throw new Exception("Impossível Alterar Aluno matriculado - aluno e turma já cadastrados");
        }

        return true;
    }

    public boolean possuiPreRequisitos(Aluno aluno, Disciplina disciplina){
        ServicoPreRequisito servicoPreRequisito = ServicoPreRequisito.getInstance();
        List<Disciplina> listaPreRequisitos=servicoPreRequisito.getPreRequisitosDisciplina(disciplina.getId());
        for (Disciplina preRequisito:listaPreRequisitos){
            if (!aluno.disciplinaConcluida(preRequisito)){
                return false;
            }
        }
        return true;
    }

    public boolean matriculaDuplicada(AlunoMatriculado alunoMatriculado, boolean alteracao) {
        if (this.getLista() == null) {
            return false;
        }
        if (!alteracao) {
            return this.getLista().stream()
                    .anyMatch(obj ->
                            ((AlunoMatriculado) obj).getAluno().equals(alunoMatriculado.getAluno())
                                    && ((AlunoMatriculado) obj).getTurma().equals(alunoMatriculado.getTurma()));
        }
        return this.getLista().stream()
                .anyMatch(obj ->
                        ((AlunoMatriculado) obj).getAluno().equals(alunoMatriculado.getAluno())
                                && ((AlunoMatriculado) obj).getTurma().equals(alunoMatriculado.getTurma())
                                && !obj.getId().equals(alunoMatriculado.getId()));
    }

    public List<ClasseBase> getAlunosMatriculadosPorCurso(Curso curso){

        return this.getLista().stream()
                .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getAluno().getCurso().equals(curso))
                .toList();
    }

    public List<ClasseBase> getAlunosMatriculadosPorAluno(Aluno aluno){

        return this.getLista().stream()
                .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getAluno().equals(aluno))
                .toList();
    }

    public List<ClasseBase> getAlunosMatriculadosPorAluno(Aluno aluno, boolean turmasAtuais, boolean turmasConcluidas){

        //todas as turmas
        if (turmasAtuais && turmasConcluidas){
            return this.getLista().stream()
                    .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getAluno().equals(aluno))
                    .toList();
        }
        //turmas atuais
        if (turmasAtuais){
            return this.getLista().stream()
                    .filter(alunoMatriculado->(
                            (AlunoMatriculado)alunoMatriculado).getAluno().equals(aluno)
                            && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(Util.getSemestreAtual()))
                    .toList();
        }
        //turmas concluidas
        return this.getLista().stream()
                    .filter(alunoMatriculado->(
                            (AlunoMatriculado)alunoMatriculado).getAluno().equals(aluno)
                            && !((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(Util.getSemestreAtual()))
                    .toList();

    }

    public List<ClasseBase> getAlunosMatriculadosPorDisciplina(Disciplina disciplina){

        return this.getLista().stream()
                .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getTurma().getDisciplina().equals(disciplina))
                .toList();
    }

    public List<ClasseBase> getAlunosMatriculadosPorProfessor(Professor professor){

        return this.getLista().stream()
                .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getTurma().getProfessor().equals(professor))
                .toList();
    }

    public List<ClasseBase> getAlunosMatriculadosPorSala(Sala sala){

        return this.getLista().stream()
                .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getTurma().getSala().equals(sala))
                .toList();
    }

    public List<ClasseBase> getAlunosMatriculadosPorTurma(Turma turma){

        return this.getLista().stream()
                .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getTurma().equals(turma))
                .toList();
    }

    private boolean possuiVagasDisponiveis(Turma turma){
        long vagasUtilizadas = this.getLista().stream()
                .filter(obj ->
                        ((AlunoMatriculado) obj).getTurma().getId().equals(turma.getId()))
                .count();
        if (turma.getQtdMaxAlunos()<=vagasUtilizadas){
            return false;
        }
        return true;
    }

    public boolean existeAluno(UUID idAluno){
        if (this.getLista() == null) {
            return false;
        }
        return this.getLista().stream()
                .anyMatch(obj ->
                        ((AlunoMatriculado) obj).getAluno().getId().equals(idAluno));
    }
    public boolean existeTurma(UUID idTurma){
        if (this.getLista() == null) {
            return false;
        }
        return this.getLista().stream()
                .anyMatch(obj ->
                        ((AlunoMatriculado) obj).getTurma().getId().equals(idTurma));
    }

    public List<AlunoMatriculado> getListaMatriculasPorAluno(UUID idAluno, String semestreAno){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        if (semestreAno==null){
            for (ClasseBase alunoMatriculado:this.getLista()){
                if (((AlunoMatriculado)alunoMatriculado).getAluno().getId().equals(idAluno)){
                    listaFinal.add((AlunoMatriculado) alunoMatriculado);
                }
            }
        }
        else{
            for (ClasseBase alunoMatriculado:this.getLista()){
                if (((AlunoMatriculado)alunoMatriculado).getAluno().getId().equals(idAluno)
                    && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestreAno)){
                    listaFinal.add((AlunoMatriculado) alunoMatriculado);
                }
            }
        }

        return listaFinal;
    }
    public List<AlunoMatriculado> getListaMatriculasPorTurma(UUID idTurma){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getTurma().getId().equals(idTurma)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        return listaFinal;
    }

    public List<AlunoMatriculado> getListaMatriculasPorDisciplina(UUID idDisciplina, String semestreAno){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getTurma().getDisciplina().getId().equals(idDisciplina)
                && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestreAno)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        return listaFinal;
    }

    public List<AlunoMatriculado> getListaMatriculasPorProfessor(UUID idProfessor, String semestreAno){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getTurma().getProfessor().getId().equals(idProfessor)
                    && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestreAno)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        return listaFinal;
    }

    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }

    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<AlunoMatriculado>>() {});
    }

    public String gerarHtmlBoletim(Aluno aluno,  String tituloRelatorio, boolean completo) {


        List<ClasseBase> matriculas=this.getLista().stream()
                .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getAluno().equals(aluno))
                .toList();

        // Agrupar matriculas por semestre
        Map<String, List<ClasseBase>> matriculasPorSemestre = matriculas.stream()
                .collect(Collectors.groupingBy(mat->((AlunoMatriculado)mat).getTurma().formataSemestreAnoParaOrdenacao()));

        // Ordenar os semestres
        Map<String, List<ClasseBase>> semestresOrdenados = new TreeMap<>(matriculasPorSemestre);

        for (List<ClasseBase> lista : semestresOrdenados.values()) {
            lista.sort(Comparator.comparing((ClasseBase mat)->((AlunoMatriculado)mat).getTurma().formataSemestreAnoParaOrdenacao()));
        }

        // Gerar o conteúdo HTML
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"pt-BR\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Turmas por Semestre</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("        h2 { color: #2c3e50; }\n");
        html.append("        table { border-collapse: collapse; width: 100%; margin-bottom: 30px; }\n");
        html.append("        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }\n");
        html.append("        th { background-color: #f4f4f4; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <h1>" + tituloRelatorio + "</h1>\n");
        html.append("    <h1>" + aluno.getNome() + " (" + aluno.getMatricula() + ")" + "</h1>\n");

        for (Map.Entry<String, List<ClasseBase>> entry : semestresOrdenados.entrySet()) {
            String semestreAtual = ((AlunoMatriculado) semestresOrdenados.get(entry.getKey()).getFirst()).getTurma().getSemestreAno();
            List<ClasseBase> lista = entry.getValue();
            html.append("    <h2>").append(semestreAtual).append("</h2>\n");
            html.append("    <table>\n");
            html.append("        <tr>\n");
            html.append("            <th>Disciplina</th>\n");
            if (completo) {
                html.append("            <th>Turma</th>\n");
                html.append("            <th>Professor</th>\n");
                html.append("            <th>Sala</th>\n");
                html.append("            <th>Nota 1</th>\n");
                html.append("            <th>Nota 2</th>\n");
                html.append("            <th>Nota 3</th>\n");
                html.append("            <th>Nota Seminário</th>\n");
                html.append("            <th>Nota Exercícios</th>\n");
            }
            html.append("            <th>Média Final</th>\n");
            html.append("            <th>Faltas</th>\n");
            html.append("            <th>% Faltas</th>\n");
            html.append("            <th>Status</th>\n");
            html.append("        </tr>\n");

            for (ClasseBase m : lista) {
                html.append("        <tr>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).getTurma().getDisciplina().getTitulo()).append("</td>\n");
                if (completo) {
                    html.append("            <td>").append(((AlunoMatriculado) m).getTurma().getCodigo()).append("</td>\n");
                    html.append("            <td>").append(((AlunoMatriculado) m).getTurma().getProfessor().getNome()).append("</td>\n");
                    html.append("            <td>").append(((AlunoMatriculado) m).getTurma().getSala().getLocal()).append("</td>\n");
                    html.append("            <td>").append(((AlunoMatriculado) m).getNotaP1()).append("</td>\n");
                    html.append("            <td>").append(((AlunoMatriculado) m).getNotaP2()).append("</td>\n");
                    html.append("            <td>").append(((AlunoMatriculado) m).getNotaP3()).append("</td>\n");
                    html.append("            <td>").append(((AlunoMatriculado) m).getNotaS()).append("</td>\n");
                    html.append("            <td>").append(((AlunoMatriculado) m).getNotaL()).append("</td>\n");
                }
                html.append("            <td>").append(((AlunoMatriculado)m).calcularMediaFinal()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).getFaltas()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).calcularPercentualFaltas()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).retornarStatus()).append("</td>\n");
                html.append("        </tr>\n");
            }
            html.append("    </table>\n");
        }
        html.append("</body>\n");
        html.append("</html>");
        return html.toString();
    }

    public String gerarHtmlAvaliacao(Disciplina disciplina, Professor professor, Turma turma, String tituloRelatorio, String semestre) {


        List<ClasseBase> matriculas=null;
        if (disciplina!=null){
            matriculas = this.getLista().stream()
                    .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getTurma().getDisciplina().equals(disciplina)
                            && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestre))
                    .toList();
        }
        if (professor!=null){
            matriculas = this.getLista().stream()
                    .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getTurma().getProfessor().equals(professor)
                            && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestre))
                    .toList();
        }
        if (turma!=null){
            matriculas = this.getLista().stream()
                    .filter(alunoMatriculado->((AlunoMatriculado)alunoMatriculado).getTurma().equals(turma)
                            && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestre))
                    .toList();
        }

        // Agrupar matriculas por turma
        Map<String, List<ClasseBase>> matriculasPorTurma = matriculas.stream()
                .collect(Collectors.groupingBy(mat->((AlunoMatriculado)mat).getTurma().getCodigo()));

        // Ordenar as turmas
        Map<String, List<ClasseBase>> turmasOrdenadas = new TreeMap<>(matriculasPorTurma);

        for (List<ClasseBase> lista : turmasOrdenadas.values()) {
            lista.sort(Comparator.comparing((ClasseBase mat)->((AlunoMatriculado)mat).getAluno().getNome()));
        }
        // Gerar o conteúdo HTML
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"pt-BR\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Avaliação de Turmas</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("        h2 { color: #2c3e50; }\n");
        html.append("        table { border-collapse: collapse; width: 100%; margin-bottom: 30px; }\n");
        html.append("        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }\n");
        html.append("        th { background-color: #f4f4f4; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <h1>" + tituloRelatorio + "</h1>\n");
        if (turma!=null){
            html.append("    <h1>(" + turma.getCodigo() + ") - " +  turma.getDisciplina().getTitulo() + " - " + turma.getProfessor().getNome() + " - " + turma.getSemestreAno() + "</h1>\n");
        }
        if (professor!=null){
            html.append("    <h1>" + professor.getNome() + "</h1>\n");
        }
        if (disciplina!=null){
            html.append("    <h1>" + disciplina.getTitulo() + "</h1>\n");
        }


        for (Map.Entry<String, List<ClasseBase>> entry : turmasOrdenadas.entrySet()) {
            String turmaAtual = ((AlunoMatriculado) turmasOrdenadas.get(entry.getKey()).getFirst()).getTurma().getCodigo();
            turmaAtual+= " - " + ((AlunoMatriculado) turmasOrdenadas.get(entry.getKey()).getFirst()).getTurma().getDisciplina().getTitulo();
            turmaAtual+= " - " + ((AlunoMatriculado) turmasOrdenadas.get(entry.getKey()).getFirst()).getTurma().getProfessor().getNome();
            List<ClasseBase> lista = entry.getValue();
            html.append("    <h2>").append(turmaAtual).append("</h2>\n");
            html.append("    <table>\n");
            html.append("        <tr>\n");
            html.append("            <th>Aluno</th>\n");
            html.append("            <th>Nota 1</th>\n");
            html.append("            <th>Nota 2</th>\n");
            html.append("            <th>Nota 3</th>\n");
            html.append("            <th>Nota Seminário</th>\n");
            html.append("            <th>Nota Exercícios</th>\n");
            html.append("            <th>Média Final</th>\n");
            html.append("            <th>Faltas</th>\n");
            html.append("            <th>% Faltas</th>\n");
            html.append("            <th>Status</th>\n");
            html.append("        </tr>\n");

            for (ClasseBase m : lista) {
                html.append("        <tr>\n");
                html.append("            <td>").append(((AlunoMatriculado) m).getAluno().getNome() + " (" + ((AlunoMatriculado) m).getAluno().getMatricula() +")" ).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado) m).getNotaP1()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado) m).getNotaP2()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado) m).getNotaP3()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado) m).getNotaS()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado) m).getNotaL()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).calcularMediaFinal()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).getFaltas()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).calcularPercentualFaltas()).append("</td>\n");
                html.append("            <td>").append(((AlunoMatriculado)m).retornarStatus()).append("</td>\n");
                html.append("        </tr>\n");
            }
            html.append("    </table>\n");
        }
        html.append("</body>\n");
        html.append("</html>");
        return html.toString();
    }

}
