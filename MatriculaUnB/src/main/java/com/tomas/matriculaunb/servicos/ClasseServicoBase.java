package com.tomas.matriculaunb.servicos;


import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ClasseServicoBase {
    private List<ClasseBase> lista;

    public List<ClasseBase> getLista() {
        return lista;
    }

    public void setLista(List<ClasseBase> lista) {
        this.lista = lista;
    }

    public void criar(ClasseBase classeBase){
        if (this.getLista() == null){
            this.setLista(new ArrayList<>());
        }
        this.getLista().add(classeBase);
    }
    public void excluir(ClasseBase classeBase){
        if (this.getLista() != null) {
            this.getLista().remove(classeBase);
        }
    }


    public void alterar(ClasseBase objAlterado){
        ClasseBase objOriginal= null;
        try {
            objOriginal = retornar(objAlterado.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        excluir(objOriginal);
        criar(objAlterado);
    }

    public ClasseBase retornar(UUID id) throws Exception{
        /*
        for (Curso curso:lista){
            if (curso.getId() == id){
                return curso;
            }
        }
        return null;
        */

        ClasseBase objRetornado = null;
        try {
            objRetornado = (ClasseBase) lista.stream()
                    .filter(curso -> curso.getId()==id)
                    .findFirst()
                    .orElse(null).clone();
        } catch (CloneNotSupportedException e) {
            throw new Exception("Erro ao retornar o objeto");
        }

        return objRetornado;

    }
    public void exibirLista() {
        for (ClasseBase obj : this.getLista()) {
            obj.exibirDados();
        }
    }
}

