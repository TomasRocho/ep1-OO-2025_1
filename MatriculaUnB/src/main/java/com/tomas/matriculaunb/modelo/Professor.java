package com.tomas.matriculaunb.modelo;

import java.util.List;

public class Professor extends Pessoa {


    public Professor(){super();}
    public Professor(String matricula,String nome){
        super(matricula,nome);
    }


    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getNome()==null || this.getNome().isBlank()){
            throw new Exception("Professor inválido - nome não preenchido");
        }

        if (this.getMatricula()==null || this.getMatricula().isBlank()){
            throw new Exception("Professor inválido - matrícula não preenchida");
        }
    }

    @Override
    public String toString() {
        return "Professor{" + super.toString() +
                '}';
    }
}
