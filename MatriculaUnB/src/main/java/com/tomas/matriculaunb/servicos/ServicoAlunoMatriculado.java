package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
