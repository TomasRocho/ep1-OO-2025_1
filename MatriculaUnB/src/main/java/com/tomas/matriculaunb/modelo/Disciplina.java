package com.tomas.matriculaunb.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Disciplina extends ClasseBase{
    private String titulo;
    private String codigo;
    private int cargaHoraria;
    private List<Disciplina> listaPreRequisitos;

    public List<Disciplina> getListaPreRequisitos() {
        return listaPreRequisitos;
    }

    public void setListaPreRequisitos(List<Disciplina> listaPreRequisitos) {
        this.listaPreRequisitos = listaPreRequisitos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }


    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Disciplina(){super();}
    public Disciplina(String titulo, String codigo, int cargaHoraria){
        this.setId(UUID.randomUUID());
        this.setTitulo(titulo);
        this.setCodigo(codigo);
        this.setCargaHoraria(cargaHoraria);
    }

    @Override
    public String toString() {
        return "Disciplina{" + super.toString()+
                "titulo='" + titulo + '\'' +
                ", codigo='" + codigo + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", listaPreRequisitos=" + listaPreRequisitos +
                '}';
    }

    public boolean incluirPreRequisito(Disciplina disciplina){
        if (this.getListaPreRequisitos()== null){
            this.setListaPreRequisitos(new ArrayList<>()) ;
        }
        this.getListaPreRequisitos().add(disciplina);
        return true;
    }
    public boolean excluirPreRequisito(Disciplina disciplina){
        if (this.getListaPreRequisitos()!= null){
            this.getListaPreRequisitos().remove(disciplina);
        }
        return true;
    }
    public void exibirPreRequisitos(){
        System.out.println("Os pre-requisitos de "+ this.getTitulo()+" são ");

        /*
        for (int i = 0; i < this.getListaPreRequisitos().size(); i++){
            System.out.println(this.getListaPreRequisitos().get(i).getTitulo());
        }
        */

        for(Disciplina d:this.getListaPreRequisitos()){
            System.out.println(d.getTitulo());
        }
    }

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getTitulo()==null || this.getTitulo().isBlank()){
            throw new Exception("Disciplina inválida - titulo não preenchido");
        }
        if (this.getCodigo()==null || this.getCodigo().isBlank()){
            throw new Exception("Disciplina inválida - código não preenchido");
        }
    }

}

