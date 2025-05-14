package com.tomas.matriculaunb.modelo;

import java.util.UUID;

public class PreRequisito extends ClasseBase{
    private UUID idDisciplina;
    private UUID idDisciplinaPreRequisito;

    public UUID getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(UUID idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public UUID getIdDisciplinaPreRequisito() {
        return idDisciplinaPreRequisito;
    }

    public void setIdDisciplinaPreRequisito(UUID idDisciplinaPreRequisito) {
        this.idDisciplinaPreRequisito = idDisciplinaPreRequisito;
    }

    public PreRequisito(){super();}

    public PreRequisito(UUID id, UUID idDisciplina, UUID idDisciplinaPreRequisito) {
        super(id);
        this.idDisciplina = idDisciplina;
        this.idDisciplinaPreRequisito = idDisciplinaPreRequisito;
    }

    public PreRequisito(UUID idDisciplina, UUID idDisciplinaPreRequisito) {
        this.setId(UUID.randomUUID());
        this.idDisciplina = idDisciplina;
        this.idDisciplinaPreRequisito = idDisciplinaPreRequisito;
    }

    @Override
    public String toString() {
        return "PreRequisito{" + super.toString()+
                "idDisciplina=" + idDisciplina +
                ", idDisciplinaPreRequisito=" + idDisciplinaPreRequisito +
                '}';
    }

    @Override
    public void validar()throws Exception{
        super.validar();
        if (this.getIdDisciplina()==null || this.getIdDisciplina().toString().isBlank()){
            throw new Exception("Pre Requisito invalido, idDisciplina nao preenchido");
        }
        if (this.getIdDisciplinaPreRequisito()==null || this.getIdDisciplinaPreRequisito().toString().isBlank()){
            throw new Exception("Pre Requisito invalido, idDisciplinaPreRequisito nao preenchido");
        }
    }
}