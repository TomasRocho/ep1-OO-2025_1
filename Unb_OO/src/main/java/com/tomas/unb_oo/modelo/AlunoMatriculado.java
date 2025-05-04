package com.tomas.unb_oo.modelo;

import com.tomas.unb_oo.modelo.enumeration.StatusAlunoMatriculado;

public class AlunoMatriculado extends ClasseBase{
    private Turma turma;
    private Aluno aluno;
    private float notaP1;
    private float notaP2;
    private float notaP3;
    private float notaL;
    private float notaS;
    private int faltas;
    private boolean trancado;

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

    public float getNotaP1() {
        return notaP1;
    }

    public void setNotaP1(float notaP1) {
        this.notaP1 = notaP1;
    }

    public float getNotaP2() {
        return notaP2;
    }

    public void setNotaP2(float notaP2) {
        this.notaP2 = notaP2;
    }

    public float getNotaP3() {
        return notaP3;
    }

    public void setNotaP3(float notaP3) {
        this.notaP3 = notaP3;
    }

    public float getNotaL() {
        return notaL;
    }

    public void setNotaL(float notaL) {
        this.notaL = notaL;
    }

    public float getNotaS() {
        return notaS;
    }

    public void setNotaS(float notaS) {
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
        this.setNotaP1(0);
        this.setNotaP2(0);
        this.setNotaP3(0);
        this.setNotaL(0);
        this.setNotaS(0);
        this.setFaltas(0);
        this.setTrancado(false);

    }

    public Float calcularMediaFinal(){
        if (this.getAluno().isEspecial()|| this.isTrancado()){
            return null;
        }

        if (this.getTurma().isAvaliacaoMediaAritmetica()){
            return (notaP1+notaP2+notaP3+notaS+notaL)/5;
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
        resultado += "P1: " + this.getNotaP1() + ", P2: "+ this.getNotaP2() + ", P3: "
                + this.getNotaP3() + ", Nota da lista de exercicios: "+this.getNotaL()
                +", Nota do seminário: "+ this.getNotaS()+"\n";
        resultado += "Percentual de faltas: "+ calcularPercentualFaltas()+"\n";
        resultado += "Nota final: " + calcularMediaFinal()+"\n";
        resultado += "Resultado final: "+ this.getStatus();
        return resultado;
    }



}
