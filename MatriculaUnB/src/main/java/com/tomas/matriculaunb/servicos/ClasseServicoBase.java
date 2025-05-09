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

    public boolean podeIncluir(ClasseBase objClasseBase)throws Exception{
        return true;
    }

    public boolean podeAlterar(ClasseBase objClasseBase)throws Exception{
        return true;
    }

    public boolean podeExcluir(ClasseBase objClasseBase)throws Exception{
        return true;
    }

    public void incluir(ClasseBase objClasseBase) throws Exception{
        if (this.podeIncluir(objClasseBase)){
            if (this.getLista() == null){
                this.setLista(new ArrayList<>());
            }
            this.getLista().add(objClasseBase);
        }

    }

    public void excluir(ClasseBase objClasseBase)throws Exception {
        if (this.podeExcluir(objClasseBase)){
            if (this.getLista() != null) {
                this.getLista().remove(objClasseBase);
            }
        }

    }

    public void alterar(ClasseBase objAlterado) throws Exception{
        if (this.podeAlterar(objAlterado)){
            ClasseBase objOriginal= null;
            try {
                objOriginal = retornar(objAlterado.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            excluir(objOriginal);
            incluir(objAlterado);
        }

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
        if (lista==null){
            return null;
        }
        ClasseBase objRetornado = null;
        objRetornado = (ClasseBase) lista.stream()
                .filter(obj -> obj.getId()==id)
                .findFirst()
                .orElse(null);
        if (objRetornado!=null){
            return objRetornado.clone();
        }
        return null;
    }

    public boolean existe(UUID id){
        if (lista==null){
            return false;
        }
        return lista.stream().anyMatch(obj -> obj.getId()==id);
    }

    public void exibirLista() {
        for (ClasseBase obj : this.getLista()) {
            obj.exibirDados();
        }
    }
}

