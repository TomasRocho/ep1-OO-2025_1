package com.tomas.matriculaunb.modelo;


import com.tomas.matriculaunb.util.Util;

import java.util.List;
import java.util.UUID;

public class Turma extends ClasseBase{
    private Disciplina disciplina;
    private Professor professor;
    private Sala sala;
    private String horario;
    private boolean avaliacaoMediaAritmetica;
    private String semestreAno;
    private boolean presencial;
    private int qtdMaxAlunos;

    public int getQtdMaxAlunos() {
        return qtdMaxAlunos;
    }

    public void setQtdMaxAlunos(int qtdMaxAlunos) {
        this.qtdMaxAlunos = qtdMaxAlunos;
    }


    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isAvaliacaoMediaAritmetica() {
        return avaliacaoMediaAritmetica;
    }

    public void setAvaliacaoMediaAritmetica(boolean avaliacaoMediaAritmetica) {
        this.avaliacaoMediaAritmetica = avaliacaoMediaAritmetica;
    }

    public String getSemestreAno() {
        return semestreAno;
    }

    public void setSemestreAno(String semestreAno) {
        this.semestreAno = semestreAno;
    }

    public boolean isPresencial() {
        return presencial;
    }

    public void setPresencial(boolean presencial) {
        this.presencial = presencial;
    }


    public String formataSemestreAnoParaOrdenacao(){
        return this.getSemestreAno().substring(3)+this.getSemestreAno().substring(0,0);
    }


    public Turma(){super();}
    public Turma(Disciplina disciplina, Professor professor, Sala sala,
                 String horario,String semestreAno, int qtdMaxAlunos){
        this.setId(UUID.randomUUID());
        this.setDisciplina(disciplina);
        this.setProfessor(professor);
        this.setSala(sala);
        this.setHorario(horario);
        this.setSemestreAno(semestreAno);
        this.setPresencial(true);
        this.setAvaliacaoMediaAritmetica(true);
        this.setQtdMaxAlunos(qtdMaxAlunos);


    }



    @Override
    public String toString() {
        return "Turma{" + super.toString() +
                "disciplina=" + disciplina +
                ", professor=" + professor +
                ", sala=" + sala +
                ", horario='" + horario + '\'' +
                ", avaliacaoMediaAritmetica=" + avaliacaoMediaAritmetica +
                ", semestreAno='" + semestreAno + '\'' +
                ", presencial=" + presencial +
                ", qtdMaxAlunos=" + qtdMaxAlunos +
                '}';
    }

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getQtdMaxAlunos() < 0){
            throw new Exception("Turma inválida - número máximo de alunos inválido");
        }
        if (this.getDisciplina()==null){
            throw new Exception("Turma inválida - disciplina não preenchida");
        }
        if (this.getProfessor()==null){
            throw new Exception("Turma inválida - professor não preenchido");
        }
        if (this.isPresencial() && this.getSala()==null ){
            throw new Exception("Turma inválida - sala não preenchida");
        }
        if (!this.isPresencial() && this.getSala()!=null ){
            throw new Exception("Turma inválida - turma remota não tem sala");
        }
        if (this.getHorario()==null || this.getHorario().isBlank()){
            throw new Exception("Turma inválida - horário não preenchido");
        }
        if (this.getSemestreAno()==null || this.getSemestreAno().isBlank()){
            throw new Exception("Turma inválida - semestre não preenchido");
        }
        if (!Util.semestreValido(this.getSemestreAno())){
            throw new Exception("Turma inválida - semestre invalido, deve estar no formato 1/aaaa ou 2/aaaa");
        }
    }
    public boolean turmaAtiva(){
        if (this.getSemestreAno().equals(Util.getSemestreAtual())){
            return true;
        }
        return false;
    }
}
