package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicoCurso extends ClasseServicoBase {

    @Override
    public boolean podeIncluir(ClasseBase curso) throws Exception{

        if (((Curso)curso).getTitulo()==null || ((Curso)curso).getTitulo().isBlank()){
            throw new Exception("Impossível Incluir Curso - titulo não preenchido ");
        }

        if (this.existe(curso.getId())){
            throw new Exception("Impossível Incluir Curso - id duplicado");
        }
        if (tituloDuplicado(((Curso) curso).getTitulo(),null)){
            throw new Exception("Impossível Incluir Curso - título duplicado");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase cursoAlterado) throws Exception{

        if (((Curso)cursoAlterado).getTitulo()==null || ((Curso)cursoAlterado).getTitulo().isBlank()){
            throw new Exception("Impossível Alterar Curso - titulo não preenchido ");
        }

        if (tituloDuplicado(((Curso) cursoAlterado).getTitulo(),cursoAlterado.getId())){
            throw new Exception("Impossível Alterar Curso - título duplicado");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase cursoAlterado) throws Exception{

        //todo: verificar se esse curso não está sendo usado por algum Aluno

        return true;
    }

    public Curso retornarPorTitulo(String titulo) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Curso cursoRetornado = null;
        cursoRetornado = (Curso)this.getLista().stream()
                .filter(obj -> ((Curso)obj).getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
        if (cursoRetornado!=null){
            return (Curso) cursoRetornado.clone();
        }
        return null;
    }

    public boolean tituloDuplicado(String titulo, UUID id){
        if (this.getLista()==null){
            return false;
        }
        if (id==null){
            return this.getLista().stream()
                    .anyMatch( obj->((Curso)obj).getTitulo().equals(titulo));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Curso)obj).getTitulo().equals(titulo)
                                            && !obj.getId().equals(id));
    }
}
