package com.tomas.matriculaunb.util;

import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.modelo.enumerations.EnumCampus;
import com.tomas.matriculaunb.servicos.*;

import java.util.ArrayList;

public class DadosTeste {

    private ServicoCurso servicoCurso = ServicoCurso.getInstance();
    private ServicoProfessor servicoProfessor= ServicoProfessor.getInstance();
    private ServicoDisciplina servicoDisciplina= ServicoDisciplina.getInstance();
    private ServicoAluno servicoAluno= ServicoAluno.getInstance();
    private ServicoTurma servicoTurma= ServicoTurma.getInstance();
    private ServicoSala servicoSala= ServicoSala.getInstance();
    private ServicoPreRequisito servicoPreRequisito= ServicoPreRequisito.getInstance();
    private ServicoAlunoMatriculado servicoAlunoMatriculado= ServicoAlunoMatriculado.getInstance();


    private void geraCursos(int qtd){
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
        for(int i=1;i<=qtd;i++){
            try {
                servicoPreRequisito.incluir(new PreRequisito(servicoDisciplina.getLista().get(i).getId(),servicoDisciplina.getLista().get(i+6).getId()));
                servicoPreRequisito.incluir(new PreRequisito(servicoDisciplina.getLista().get(i).getId(),servicoDisciplina.getLista().get(i+7).getId()));
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
        for(int i=1;i<=qtd;i++){
            try {
                servicoTurma.incluir(new Turma("T"+i ,(Disciplina) servicoDisciplina.getLista().get((i%10)+5),
                                                (Professor) servicoProfessor.getLista().get(i%3),
                                                (Sala) servicoSala.getLista().get(i%3),
                                        "SQ"+i,(i%2+1)+"/2024",100,true,true));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        for(int i=1;i<=qtd;i++){
            try {
                servicoTurma.incluir(new Turma("T"+i+5 ,(Disciplina) servicoDisciplina.getLista().get((i%10)+10),
                        (Professor) servicoProfessor.getLista().get((i%3)+5),
                        (Sala) servicoSala.getLista().get((i%3)+5),
                        "SQ"+i,"1/2025",50,true,true));
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
        for(int i=1;i<=qtd;i++){
            try {
                servicoAlunoMatriculado.incluir(new AlunoMatriculado((Turma) servicoTurma.getLista().get(i%10),
                        (Aluno) servicoAluno.getLista().get(i%45)));
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
        this.geraSalas(30);
        this.geraDisciplinas(25);
        this.geraProfessores(50);
        this.geraTurmas(5);
        this.geraAlunos(100);
        this.geraPreRequisitos(5);
        this.geraAlunosMatriculados(100);
    }

    public void apagaTudo() throws Exception {
        servicoCurso.setLista(new ArrayList<>());
        servicoCurso.salvarArquivo();
        servicoSala.setLista(new ArrayList<>());
        servicoSala.salvarArquivo();
        servicoDisciplina.setLista(new ArrayList<>());
        servicoDisciplina.salvarArquivo();
        servicoProfessor.setLista(new ArrayList<>());
        servicoProfessor.salvarArquivo();
        servicoTurma.setLista(new ArrayList<>());
        servicoTurma.salvarArquivo();
        servicoAluno.setLista(new ArrayList<>());
        servicoAluno.salvarArquivo();
        servicoPreRequisito.setLista(new ArrayList<>());
        servicoPreRequisito.salvarArquivo();
        servicoAlunoMatriculado.setLista(new ArrayList<>());
        servicoAlunoMatriculado.salvarArquivo();
    }


}
