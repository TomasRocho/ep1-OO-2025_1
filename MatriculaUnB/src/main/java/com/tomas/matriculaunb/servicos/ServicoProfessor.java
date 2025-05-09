package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.Professor;

import java.util.UUID;

public class ServicoProfessor extends ClasseServicoBase{
    @Override
    public boolean podeIncluir(ClasseBase professor) throws Exception{

        if (((Professor)professor).getNome()==null || ((Professor)professor).getNome().isBlank()){
            throw new Exception("Impossível Incluir Professor - Nome não preenchido ");
        }

        if (this.existe(professor.getId())){
            throw new Exception("Impossível Incluir Professor - id duplicado");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase professorAlterado) throws Exception{

        if (((Professor)professorAlterado).getNome()==null || ((Professor)professorAlterado).getNome().isBlank()){
            throw new Exception("Impossível Alterar Aluno - Nome não preenchido ");
        }

        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase professorAlterado) throws Exception{

        //todo: verificar se esse professor não está sendo usado por alguma turma

        return true;
    }

    public Curso retornarPorNome(String nome) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Professor professorRetornado = null;
        professorRetornado = (Professor) this.getLista().stream()
                .filter(obj -> ((Aluno)obj).getNome().equals(nome))
                .findFirst()
                .orElse(null);
        if (professorRetornado!=null){
            return (Curso) professorRetornado.clone();
        }
        return null;
    }
    
}
