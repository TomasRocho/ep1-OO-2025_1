package com.tomas.matriculaunb.modelo;

import java.util.UUID;

public class Curso extends ClasseBase  {
    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "Curso{" + super.toString()+
                "titulo='" + titulo + '\'' +
                '}';
    }

    public Curso() {
        super();
    }

    public Curso(String titulo) {
        this.setId(UUID.randomUUID());
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


}