package com.tomas.matriculaunb.modelo;

import com.tomas.matriculaunb.modelo.enumerations.StatusAlunoMatriculado;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa{
    private Curso curso;
    private boolean especial;
    private List<AlunoMatriculado> listaMatriculas;

    public List<AlunoMatriculado> getListaMatriculas() {
        return listaMatriculas;
    }

    public void setListaMatriculas(List<AlunoMatriculado> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }



    public Aluno(){super();}
    public Aluno(String nome, String matricula,Curso curso,boolean especial){
        super(matricula, nome);
        this.setCurso(curso);
        this.setEspecial(especial);
    }
    public String toString() {
        return super.toString() + ";"  + curso.getId() + ";" + especial;
    }
    public void emitirBoletim(){
        for (AlunoMatriculado matricula:this.getListaMatriculas()){
            System.out.println("----------");
            System.out.println(matricula.exibirResultado());
            System.out.println("----------");
        }
    }

    public List<Disciplina> retornarDisciplinasConcluidas(){
        List<Disciplina> listaFinal = new ArrayList<>();
        for (AlunoMatriculado obj:this.getListaMatriculas()){
            if (obj.getStatus() == StatusAlunoMatriculado.Aprovado){
                listaFinal.add(obj.getTurma().getDisciplina());
            }
        }
        return listaFinal;
    }
    public List<Turma> retornarTurmasAtuais(){
        List<Turma> listaFinal = new ArrayList<>();
        for (AlunoMatriculado matricula:this.getListaMatriculas()){
            if (matricula.getTurma().isAtiva()){
                listaFinal.add(matricula.getTurma());
            }
        }
        return listaFinal;
    }



    @Override
    public void validar()throws Exception{
        super.validar();

        if (this.getNome()==null || this.getNome().isBlank()){
            throw new Exception("Aluno inválido - Nome não preenchido ");
        }

        if (this.getMatricula()==null || this.getMatricula().isBlank()){
            throw new Exception("Aluno inválido - Matricula não preenchida ");
        }

        if (this.getCurso()==null){
            throw new Exception("Aluno inválido - Curso não preenchida ");
        }

    }



}