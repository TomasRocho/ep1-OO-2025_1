package com.tomas.matriculaunb.modelo;


import java.util.List;
import java.util.UUID;

public class Turma extends ClasseBase{
    private Disciplina disciplina;
    private Professor professor;
    private String sala;
    private String horario;
    private boolean avaliacaoMediaAritmetica;
    private String semestreAno;
    private boolean presencial;
    private boolean ativa;
    private List<AlunoMatriculado> listaAlunosMatriculados;

    public List<AlunoMatriculado> getListaAlunosMatriculados() {
        return listaAlunosMatriculados;
    }

    public void setListaAlunosMatriculados(List<AlunoMatriculado> listaAlunosMatriculados) {
        this.listaAlunosMatriculados = listaAlunosMatriculados;
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

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
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

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Turma(){super();}
    public Turma(Disciplina disciplina, Professor professor, String sala,
                 String horario,String semestreAno){
        this.setId(UUID.randomUUID());
        this.setDisciplina(disciplina);
        this.setProfessor(professor);
        this.setSala(sala);
        this.setHorario(horario);
        this.setSemestreAno(semestreAno);
        this.setPresencial(true);
        this.setAvaliacaoMediaAritmetica(true);
        this.setAtiva(true);

    }

    public void emitirBoletins(){
        for (AlunoMatriculado matricula:this.getListaAlunosMatriculados()){
            System.out.println("----------");
            System.out.println(matricula.getAluno().getMatricula()+" - "+ matricula.getAluno().getNome());
            System.out.println(matricula.exibirResultado());
            System.out.println("----------");
        }
    }

    public String toString(){
        return super.toString()+ ";" + disciplina.getId()+";"+professor.getId()+ ";"
                + sala + ";" + horario + ";" + avaliacaoMediaAritmetica +";" +
                semestreAno+ ";" + presencial +";" + ativa;
    }

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getDisciplina()==null){
            throw new Exception("Turma inválida - disciplina não preenchida");
        }
        if (this.getProfessor()==null){
            throw new Exception("Turma inválida - professor não preenchido");
        }
        if (this.getSala()==null || this.getSala().isBlank()){
            throw new Exception("Turma inválida - sala não preenchida");
        }
        if (this.getHorario()==null || this.getHorario().isBlank()){
            throw new Exception("Turma inválida - horário não preenchido");
        }
        if (this.getSemestreAno()==null || this.getSemestreAno().isBlank()){
            throw new Exception("Turma inválida - semestre não preenchido");
        }
    }

}
