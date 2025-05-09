package com.tomas.matriculaunb.modelo;

import java.util.UUID;

public class Curso extends ClasseBase{
    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return super.toString()+ ";"+ titulo;
    }


    public Curso(String titulo) {
        super();
        this.titulo = titulo;
    }

    public Curso(UUID id, String titulo) {
        super(id);
        this.titulo = titulo;
    }

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getTitulo()==null || this.getTitulo().isBlank()){
            throw new Exception("Curso inválido - titulo não preenchido");
        }
    }


    /* exemplo de um construtor com mesmo numero de parametros mas tipos diferentes
    public Curso(UUID id,int titulo) {
        super(id);
        this.titulo = String.valueOf(titulo);
    }

     */


}