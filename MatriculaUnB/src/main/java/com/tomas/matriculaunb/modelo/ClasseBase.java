package com.tomas.matriculaunb.modelo;

import java.util.Objects;
import java.util.UUID;

public abstract class ClasseBase implements Cloneable {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClasseBase{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClasseBase that = (ClasseBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    public ClasseBase(UUID id) {
        this.id = id;
    }

    /*
    public ClasseBase() {
        this.id = UUID.randomUUID();
    }
    */

    public ClasseBase(){}

    public void exibirDados(){
        System.out.println(this.toString());
    }

    @Override
    public ClasseBase clone() throws CloneNotSupportedException {
        return (ClasseBase) super.clone();
    }

    public void validar() throws Exception{
        if (this.getId()==null){
            throw new Exception("Objeto inválido - id não preenchido");
        }
    }

}