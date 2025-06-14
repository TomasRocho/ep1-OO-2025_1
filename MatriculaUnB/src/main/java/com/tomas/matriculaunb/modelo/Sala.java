package com.tomas.matriculaunb.modelo;

import com.tomas.matriculaunb.modelo.enumerations.EnumCampus;

import java.util.UUID;

public class Sala extends ClasseBase{
    private String local;
    private EnumCampus campus;

    public EnumCampus getCampus() {
        return campus;
    }

    public void setCampus(EnumCampus campus) {
        this.campus = campus;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    public Sala(){}
    public Sala(String local, EnumCampus campus){
        this.setId(UUID.randomUUID());
        this.setLocal(local);
        this.setCampus(campus);
    }

    @Override
    public String toString() {
        return "Sala{" + super.toString()+
                "local='" + local + '\'' +
                ", campus=" + campus +
                '}';
    }

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getLocal()==null || this.getLocal().isBlank()){
            throw new Exception("Sala inválida - local não preenchido");
        }
        if (this.getCampus()==null){
            throw new Exception("Sala inválida - campus não preenchido");
        }
    }

}
