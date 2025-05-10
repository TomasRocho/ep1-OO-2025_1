package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.AlunoMatriculado;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Professor;

public class ServicoAlunoMatriculado extends ClasseServicoBase{
    @Override
    public boolean podeIncluir(ClasseBase alunoMatriculado) throws Exception{

        alunoMatriculado.validar();

        if (this.existe(alunoMatriculado.getId())){
            throw new Exception("Impossível Incluir Aluno matriculado - id duplicado");
        }
        if (this.matriculaDuplicada((AlunoMatriculado) alunoMatriculado,false)){
            throw new Exception("Impossível Incluir Aluno matriculado - aluno e turma já cadastrados");
        }

        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase alunoMatriculado) throws Exception{

        alunoMatriculado.validar();

        if (this.matriculaDuplicada((AlunoMatriculado) alunoMatriculado,true)){
            throw new Exception("Impossível Alterar Aluno matriculado - aluno e turma já cadastrados");
        }

        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase alunoMatricula) throws Exception{



        return true;
    }

    public boolean matriculaDuplicada(AlunoMatriculado alunoMatriculado, boolean alteracao) {
        if (this.getLista() == null) {
            return false;
        }
        if (!alteracao) {
            return this.getLista().stream()
                    .anyMatch(obj ->
                            ((AlunoMatriculado) obj).getAluno().equals(alunoMatriculado.getAluno())
                                    && ((AlunoMatriculado) obj).getTurma().equals(alunoMatriculado.getTurma()));
        }
        return this.getLista().stream()
                .anyMatch(obj ->
                        ((AlunoMatriculado) obj).getAluno().equals(alunoMatriculado.getAluno())
                                && ((AlunoMatriculado) obj).getTurma().equals(alunoMatriculado.getTurma())
                                && !obj.getId().equals(alunoMatriculado.getId()));
    }
}
