package com.tomas.matriculaunb.modelo;

public class Professor extends Pessoa {
    public Professor(String nome){
        super(nome);
    }

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getNome()==null || this.getNome().isBlank()){
            throw new Exception("Professor inválido - nome não preenchido");
        }
    }

}
