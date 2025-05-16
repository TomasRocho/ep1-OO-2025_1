package com.tomas.matriculaunb.util;

import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.modelo.enumerations.EnumCampus;
import com.tomas.matriculaunb.servicos.*;

public class DadosTeste {

    private ServicoCurso servicoCurso;
    private ServicoProfessor servicoProfessor;
    private ServicoDisciplina servicoDisciplina;
    private ServicoAluno servicoAluno;
    private ServicoTurma servicoTurma;
    private ServicoSala servicoSala;
    private ServicoPreRequisito servicoPreRequisito;
    private ServicoAlunoMatriculado servicoAlunoMatriculado;


    private void geraCursos(int qtd){
        this.servicoCurso = ServicoCurso.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoCurso.incluir(new Curso("Curso-"+i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            this.servicoCurso.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void geraSalas(int qtd){
        this.servicoSala = ServicoSala.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoSala.incluir(new Sala("Sala-"+i, EnumCampus.Gama));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            this.servicoSala.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void geraProfessores(int qtd){
        this.servicoProfessor = ServicoProfessor.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoProfessor.incluir(new Professor(String.valueOf(i),"Professor-"+i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            this.servicoProfessor.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void geraDisciplinas(int qtd){
        this.servicoDisciplina = ServicoDisciplina.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoDisciplina.incluir(new Disciplina("Disciplina-"+i,String.valueOf(i),60));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            this.servicoDisciplina.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void geraPreRequisitos(int qtd){
        this.servicoPreRequisito = ServicoPreRequisito.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoPreRequisito.incluir(new PreRequisito(servicoDisciplina.getLista().get(i+5).getId(),servicoDisciplina.getLista().get(i+6).getId()));
                servicoPreRequisito.incluir(new PreRequisito(servicoDisciplina.getLista().get(i+5).getId(),servicoDisciplina.getLista().get(i+7).getId()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            this.servicoPreRequisito.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void geraAlunos(int qtd){
        this.servicoAluno=ServicoAluno.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoAluno.incluir(new Aluno("Aluno-"+i,String.valueOf(i),(Curso) this.servicoCurso.getLista().get(i%3),false));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            this.servicoAluno.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void geraTurmas(int qtd){
        this.servicoTurma=ServicoTurma.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoTurma.incluir(new Turma("T"+i ,(Disciplina) servicoDisciplina.getLista().get(i%3),
                                                (Professor) servicoProfessor.getLista().get(i%3),
                                                (Sala) servicoSala.getLista().get(i%3),
                                        "SQ"+i,(i%2+1)+"/2025",100));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            this.servicoTurma.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void geraAlunosMatriculados(int qtd){
        this.servicoAlunoMatriculado=ServicoAlunoMatriculado.getInstance();
        for(int i=1;i<=qtd;i++){
            try {
                servicoAlunoMatriculado.incluir(new AlunoMatriculado((Turma) servicoTurma.getLista().get(i%4),
                        (Aluno) servicoAluno.getLista().get(i%15)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            this.servicoAlunoMatriculado.salvarArquivo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void geraTudo() {
        this.geraCursos(10);
        this.geraSalas(10);
        this.geraDisciplinas(20);
        this.geraProfessores(50);
        this.geraTurmas(5);
        this.geraAlunos(100);
        this.geraPreRequisitos(5);
        this.geraAlunosMatriculados(50);
    }


}
