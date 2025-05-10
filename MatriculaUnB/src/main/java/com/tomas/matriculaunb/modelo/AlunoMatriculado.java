package com.tomas.matriculaunb.modelo;


import com.tomas.matriculaunb.modelo.enumerations.StatusAlunoMatriculado;

import java.math.BigDecimal;

public class AlunoMatriculado extends ClasseBase{
    private Turma turma;
    private Aluno aluno;
    private Float notaP1;
    private Float notaP2;
    private Float notaP3;
    private Float notaL;
    private Float notaS;
    private int faltas;
    private boolean trancado;

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getTurma()==null){
            throw new Exception("AlunoMatriculado inválido - turma não preenchida");
        }
        if (this.getAluno()==null){
            throw new Exception("AlunoMatriculado inválido - aluno não preenchido");
        }
        if (this.getNotaP1()==null){
            throw new Exception("AlunoMatriculado inválido - nota 1 inválida");
        }
        if (this.getNotaP2()==null){
            throw new Exception("AlunoMatriculado inválido - nota 2 inválida");
        }
        if (this.getNotaP3()==null){
            throw new Exception("AlunoMatriculado inválido - nota 3 inválida");
        }
        if (this.getNotaS()==null){
            throw new Exception("AlunoMatriculado inválido - nota seminário inválida");
        }
        if (this.getNotaL()==null){
            throw new Exception("AlunoMatriculado inválido - nota lista de exercícios inválida");
        }
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Float getNotaP1() {
        return notaP1;
    }

    public void setNotaP1(Float notaP1) {
        this.notaP1 = notaP1;
    }

    public Float getNotaP2() {
        return notaP2;
    }

    public void setNotaP2(Float notaP2) {
        this.notaP2 = notaP2;
    }

    public Float getNotaP3() {
        return notaP3;
    }

    public void setNotaP3(Float notaP3) {
        this.notaP3 = notaP3;
    }

    public Float getNotaL() {
        return notaL;
    }

    public void setNotaL(Float notaL) {
        this.notaL = notaL;
    }

    public Float getNotaS() {
        return notaS;
    }

    public void setNotaS(Float notaS) {
        this.notaS = notaS;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public boolean isTrancado() {
        return trancado;
    }

    public void setTrancado(boolean trancado) {
        this.trancado = trancado;
    }
    public AlunoMatriculado(Turma turma, Aluno aluno){
        super();
        this.setTurma(turma);
        this.setAluno(aluno);
        this.setNotaP1((float) 0);
        this.setNotaP2((float) 0);
        this.setNotaP3((float) 0);
        this.setNotaL((float) 0);
        this.setNotaS((float) 0);
        this.setFaltas(0);
        this.setTrancado(false);

    }

    public Float calcularMediaFinal(){
        if (this.getAluno().isEspecial()|| this.isTrancado()){
            return null;
        }

        if (this.getTurma().isAvaliacaoMediaAritmetica()){
            return (notaP1 + notaP2  +notaP3 +notaS+notaL)/5;
        }
        return (notaP1 + notaP2 * 2 +notaP3 * 3 +notaS+notaL)/8;
    }
    public String toString(){
        return super.toString()+ ";" + turma.getId()+";"+aluno.getId()+ ";"
                + notaP1 + ";" + notaP2 + ";" + notaP3 +";" +
                notaL+ ";" + notaS +";" + faltas + ";" + trancado;
    }
    public float calcularPercentualFaltas(){
        int numeroDeAulas = this.getTurma().getDisciplina().getCargaHoraria() /2;
        if (numeroDeAulas == 0){
            return 0;
        }
        return (float) this.getFaltas() / numeroDeAulas * 100;
    }
    public StatusAlunoMatriculado getStatus(){
        if (this.isTrancado()){
            return StatusAlunoMatriculado.Trancado;
        }
        if (this.getTurma().isAtiva()){
            return StatusAlunoMatriculado.EmCurso;
        }
        if (this.getAluno().isEspecial()){
            return StatusAlunoMatriculado.AlunoEspecial;
        }
        float nota = this.calcularMediaFinal();
        float pctFaltas = this.calcularPercentualFaltas();
        if (pctFaltas > 25){
            return StatusAlunoMatriculado.ReprovadoPorFalta;
        }
        else if (nota < 5){
            return StatusAlunoMatriculado.ReprovadoPorNota;
        }
        return StatusAlunoMatriculado.Aprovado;
    }
    public String exibirResultado(){
        String resultado = "";
        resultado += "Disciplina: "+ this.getTurma().getDisciplina().getTitulo() +"\n";
        resultado += "Semestre: "+ this.getTurma().getSemestreAno()+"\n";
        resultado += "Professor: "+ this.getTurma().getProfessor().getNome()+"\n";
        resultado += "Horário: "+ this.getTurma().getHorario()+ " - Sala: " + this.getTurma().getSala() +"\n";
        resultado += "P1: " + this.getNotaP1() + ", P2: "+ this.getNotaP2() + ", P3: "
                + this.getNotaP3() + ", Nota da lista de exercicios: "+this.getNotaL()
                +", Nota do seminário: "+ this.getNotaS()+"\n";
        resultado += "Percentual de faltas: "+ calcularPercentualFaltas()+"\n";
        resultado += "Nota final: " + calcularMediaFinal()+"\n";
        resultado += "Resultado final: "+ this.getStatus();
        return resultado;
    }





}
