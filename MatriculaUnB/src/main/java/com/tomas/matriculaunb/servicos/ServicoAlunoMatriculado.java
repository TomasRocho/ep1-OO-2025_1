package com.tomas.matriculaunb.servicos;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.AlunoMatriculado;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicoAlunoMatriculado extends ClasseServicoBase{

    //CLASSE SINGLETON
    private static ServicoAlunoMatriculado instance = null;
    private ServicoAlunoMatriculado(){}
    public static ServicoAlunoMatriculado getInstance() {
        if (instance == null) {
            instance = new ServicoAlunoMatriculado();
        }
        return instance;
    }


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

    public boolean existeAluno(UUID idAluno){
        if (this.getLista() == null) {
            return false;
        }
        return this.getLista().stream()
                .anyMatch(obj ->
                        ((AlunoMatriculado) obj).getAluno().getId().equals(idAluno));
    }
    public boolean existeTurma(UUID idTurma){
        if (this.getLista() == null) {
            return false;
        }
        return this.getLista().stream()
                .anyMatch(obj ->
                        ((AlunoMatriculado) obj).getTurma().getId().equals(idTurma));
    }

    public List<AlunoMatriculado> getListaMatriculasPorAluno(UUID idAluno){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getAluno().getId().equals(idAluno)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        return listaFinal;
    }
    public List<AlunoMatriculado> getListaMatriculasPorTurma(UUID idTurma){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getTurma().getId().equals(idTurma)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        return listaFinal;
    }

}
