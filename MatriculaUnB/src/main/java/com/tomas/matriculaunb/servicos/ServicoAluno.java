package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.Disciplina;

import java.util.UUID;

public class ServicoAluno extends ClasseServicoBase{
    @Override
    public boolean podeIncluir(ClasseBase aluno) throws Exception{

        if (((Aluno)aluno).getNome()==null || ((Aluno)aluno).getNome().isBlank()){
            throw new Exception("Impossível Incluir Aluno - Nome não preenchido ");
        }

        if (this.existe(aluno.getId())){
            throw new Exception("Impossível Incluir Aluno - id duplicado");
        }
        if (((Aluno)aluno).getMatricula()==null || ((Aluno)aluno).getMatricula().isBlank()){
            throw new Exception("Impossível Incluir Aluno - Matricula não preenchida ");
        }
        if (matriculaDuplicada(((Aluno) aluno).getMatricula(),null)){
            throw new Exception("Impossível Incluir Aluno - matricula duplicada");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase alunoAlterado) throws Exception{

        if (((Aluno)alunoAlterado).getNome()==null || ((Aluno)alunoAlterado).getNome().isBlank()){
            throw new Exception("Impossível Alterar Aluno - Nome não preenchido ");
        }
        if (((Aluno)alunoAlterado).getMatricula()==null || ((Aluno)alunoAlterado).getMatricula().isBlank()){
            throw new Exception("Impossível Alterar Aluno - matricula não preenchida ");
        }
        if (matriculaDuplicada(((Aluno) alunoAlterado).getMatricula(),alunoAlterado.getId())){
            throw new Exception("Impossível Alterar Aluno - matricula duplicada");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase alunoAlterado) throws Exception{

        //todo: verificar se esse aluno não está sendo usado por algum AlunoMatriculado

        return true;
    }

    public Curso retornarPorNome(String nome) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Aluno alunoRetornado = null;
        alunoRetornado = (Aluno)this.getLista().stream()
                .filter(obj -> ((Aluno)obj).getNome().equals(nome))
                .findFirst()
                .orElse(null);
        if (alunoRetornado!=null){
            return (Curso) alunoRetornado.clone();
        }
        return null;
    }
    public Curso retornarPorMatricula(String matricula) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Aluno alunoRetornado = null;
        alunoRetornado = (Aluno)this.getLista().stream()
                .filter(obj -> ((Aluno)obj).getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
        if (alunoRetornado!=null){
            return (Curso) alunoRetornado.clone();
        }
        return null;
    }

    public boolean matriculaDuplicada(String matricula, UUID id){
        if (this.getLista()==null){
            return false;
        }
        if (id==null){
            return this.getLista().stream()
                    .anyMatch( obj->((Aluno)obj).getMatricula().equals(matricula));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Aluno)obj).getMatricula().equals(matricula)
                        && !obj.getId().equals(id));
    }
}
