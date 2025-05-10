package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Professor;

public class ServicoProfessor extends ClasseServicoBase{
    @Override
    public boolean podeIncluir(ClasseBase professor) throws Exception{

        professor.validar();

        if (this.existe(professor.getId())){
            throw new Exception("Impossível Incluir Professor - id duplicado");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase professorAlterado) throws Exception{

        professorAlterado.validar();
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase professor) throws Exception{

        //todo: verificar se esse professor não está sendo usado por alguma turma

        return true;
    }

    public Professor retornarPorNome(String nome) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Professor professorRetornado = null;
        professorRetornado = (Professor) this.getLista().stream()
                .filter(obj -> ((Professor)obj).getNome().equals(nome))
                .findFirst()
                .orElse(null);
        if (professorRetornado!=null){
            return (Professor) professorRetornado.clone();
        }
        return null;
    }
    
}
