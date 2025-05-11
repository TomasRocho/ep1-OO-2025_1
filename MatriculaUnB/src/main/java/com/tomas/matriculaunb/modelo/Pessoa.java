package com.tomas.matriculaunb.modelo;

import java.util.UUID;

public abstract class Pessoa extends ClasseBase{
    private String matricula;
    private String nome;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString(){
        return super.toString() + ";" + matricula + ";" + nome;
    }

    public Pessoa(){super();}
    public Pessoa(String matricula,String nome){
        this.setId(UUID.randomUUID());
        this.setMatricula(matricula);
        this.setNome(nome);
    }

}
