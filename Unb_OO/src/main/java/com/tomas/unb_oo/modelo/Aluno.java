package com.tomas.unb_oo.modelo;

import com.tomas.unb_oo.modelo.enumeration.StatusAlunoMatriculado;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa{
    private String matricula;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public Aluno(String nome, String matricula,Curso curso,boolean especial){
        super(nome);
        this.setMatricula(matricula);
        this.setCurso(curso);
        this.setEspecial(especial);
    }
    public String toString() {
        return super.toString() + ";" + matricula + ";" + curso.getId() + ";" + especial;
    }
    public void emitirBoletim(){
        for (AlunoMatriculado matricula:this.getListaMatriculas()){
            System.out.println("----------");
            System.out.println(matricula.exibirResultado());
            System.out.println("----------");
        }
    }
    public List<Disciplina> getDisciplinasConcluidas(){
        List<Disciplina> listaFinal = new ArrayList<>();
        for (AlunoMatriculado matricula:this.getListaMatriculas()){
            if (matricula.getStatus() == StatusAlunoMatriculado.Aprovado){
                listaFinal.add(matricula.getTurma().getDisciplina());
            }
        }
        return listaFinal;
    }
    public List<Turma> getTurmasAtuais(){
        List<Turma> listaFinal = new ArrayList<>();
        for (AlunoMatriculado matricula:this.getListaMatriculas()){
            if (matricula.getTurma().isAtiva()){
                listaFinal.add(matricula.getTurma());
            }
        }
        return listaFinal;
    }

}
