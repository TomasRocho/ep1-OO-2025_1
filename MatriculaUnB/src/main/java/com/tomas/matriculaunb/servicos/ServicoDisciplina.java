package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.Disciplina;

import java.util.UUID;

public class ServicoDisciplina extends ClasseServicoBase{
    @Override
    public boolean podeIncluir(ClasseBase disciplina) throws Exception{

        if (((Disciplina)disciplina).getTitulo()==null || ((Disciplina)disciplina).getTitulo().isBlank()){
            throw new Exception("Impossível Incluir Disciplina - titulo não preenchido ");
        }
        if (((Disciplina)disciplina).getCodigo()==null || ((Disciplina)disciplina).getCodigo().isBlank()){
            throw new Exception("Impossível Incluir Disciplina - codigo não preenchido ");
        }
        if (this.existe(disciplina.getId())){
            throw new Exception("Impossível Incluir Disciplina - id duplicado");
        }
        if (tituloDuplicado(((Disciplina) disciplina).getTitulo(),null)){
            throw new Exception("Impossível Incluir Disciplina - título duplicado");
        }
        if (codigoDuplicado(((Disciplina) disciplina).getCodigo(),null)){
            throw new Exception("Impossível Incluir Disciplina - codigo duplicado");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase disciplinaAlterada) throws Exception{

        if (((Disciplina)disciplinaAlterada).getTitulo()==null || ((Disciplina)disciplinaAlterada).getTitulo().isBlank()){
            throw new Exception("Impossível Alterar Disciplina - titulo não preenchido ");
        }
        if (((Disciplina)disciplinaAlterada).getCodigo()==null || ((Disciplina)disciplinaAlterada).getCodigo().isBlank()){
            throw new Exception("Impossível Alterar Disciplina - codigo não preenchido ");
        }
        if (tituloDuplicado(((Disciplina) disciplinaAlterada).getTitulo(),disciplinaAlterada.getId())){
            throw new Exception("Impossível Alterar Disciplina - título duplicado");
        }
        if (codigoDuplicado(((Disciplina) disciplinaAlterada).getCodigo(),disciplinaAlterada.getId())){
            throw new Exception("Impossível Alterar Disciplina - codigo duplicado");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase disciplinaAlterada) throws Exception{

        //todo: verificar se essa disciplina nao esta sendo usada por alguma turma

        return true;
    }

    public Disciplina retornarPorTitulo(String titulo) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Disciplina disciplinaRetornada = null;
        disciplinaRetornada = (Disciplina) this.getLista().stream()
                .filter(obj -> ((Disciplina)obj).getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
        if (disciplinaRetornada!=null){
            return (Disciplina) disciplinaRetornada.clone();
        }
        return null;
    }

    public boolean tituloDuplicado(String titulo, UUID id){
        if (this.getLista()==null){
            return false;
        }
        if (id==null){
            return this.getLista().stream()
                    .anyMatch( obj->((Disciplina)obj).getTitulo().equals(titulo));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Disciplina)obj).getTitulo().equals(titulo)
                        && !obj.getId().equals(id));
    }
    public boolean codigoDuplicado(String codigo, UUID id){
        if (this.getLista()==null){
            return false;
        }
        if (id==null){
            return this.getLista().stream()
                    .anyMatch( obj->((Disciplina)obj).getCodigo().equals(codigo));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Disciplina)obj).getCodigo().equals(codigo)
                        && !obj.getId().equals(id));
    }

    public Disciplina retornarPorCodigo(String codigo) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Disciplina disciplinaRetornada = null;
        disciplinaRetornada = (Disciplina) this.getLista().stream()
                .filter(obj -> ((Disciplina)obj).getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
        if (disciplinaRetornada!=null){
            return (Disciplina) disciplinaRetornada.clone();
        }
        return null;
    }
}
