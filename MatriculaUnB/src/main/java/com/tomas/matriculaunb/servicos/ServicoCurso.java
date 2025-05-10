package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicoCurso extends ClasseServicoBase {

    //CLASSE SINGLETON
    private static ServicoCurso instance = null;
    private ServicoCurso(){}
    public static ServicoCurso getInstance() {
        if (instance == null) {
            instance = new ServicoCurso();
        }
        return instance;
    }


    @Override
    public boolean podeIncluir(ClasseBase curso) throws Exception{

        curso.validar();

        if (this.existe(curso.getId())){
            throw new Exception("Impossível Incluir Curso - id duplicado");
        }
        if (tituloDuplicado((Curso) curso,false)){
            throw new Exception("Impossível Incluir Curso - título duplicado");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase cursoAlterado) throws Exception{

        cursoAlterado.validar();

        if (tituloDuplicado((Curso) cursoAlterado,true)){
            throw new Exception("Impossível Alterar Curso - título duplicado");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase curso) throws Exception{

        ServicoAluno servicoAluno = ServicoAluno.getInstance();
        if (servicoAluno.existeCurso(curso.getId())){
            throw new Exception("Impossível excluir Curso - curso utilizado por algum aluno");
        }
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

    public boolean tituloDuplicado(Curso curso,boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Curso)obj).getTitulo().equals(curso.getTitulo()));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Curso)obj).getTitulo().equals(curso.getTitulo())
                                            && !obj.getId().equals(curso.getId()));
    }
}
