package com.tomas.matriculaunb.modelo;

public abstract class Pessoa extends ClasseBase{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString(){
        return super.toString() + ";" + nome;
    }
    public Pessoa(String nome){
        super();
        this.setNome(nome);
    }

}
