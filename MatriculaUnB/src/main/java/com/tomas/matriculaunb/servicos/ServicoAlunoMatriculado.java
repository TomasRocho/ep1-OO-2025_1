package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ServicoAlunoMatriculado extends ClasseServicoBase{

    final String nomeArquivo="alunoMatriculado.txt";

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

        if (this.getLista().size() >= ((AlunoMatriculado) alunoMatriculado).getTurma().getQtdMaxAlunos()){
            throw new Exception("Impossível Incluir Aluno matriculado - vagas esgotadas");
        }

        //preenche a listaMatriculas do aluno informado
        ((AlunoMatriculado) alunoMatriculado).getAluno().setListaMatriculas(this.getListaMatriculasPorAluno(((AlunoMatriculado) alunoMatriculado).getAluno().getId(),null));

        if (((AlunoMatriculado) alunoMatriculado).getAluno().disciplinaConcluida(((AlunoMatriculado) alunoMatriculado).getTurma().getDisciplina())){
            throw new Exception("Impossível Incluir Aluno matriculado - aluno já concluiu esta disciplina");
        }
        if (!this.possuiPreRequisitos(((AlunoMatriculado) alunoMatriculado).getAluno(),((AlunoMatriculado) alunoMatriculado).getTurma().getDisciplina())){
            throw new Exception("Impossível Incluir Aluno matriculado - aluno não possui pré-requisitos");
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

    public boolean possuiPreRequisitos(Aluno aluno, Disciplina disciplina){
        ServicoPreRequisito servicoPreRequisito = ServicoPreRequisito.getInstance();
        List<Disciplina> listaPreRequisitos=servicoPreRequisito.getPreRequisitosDisciplina(disciplina.getId());
        for (Disciplina preRequisito:listaPreRequisitos){
            if (!aluno.disciplinaConcluida(preRequisito)){
                return false;
            }
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

    public List<AlunoMatriculado> getListaMatriculasPorAluno(UUID idAluno, String semestreAno){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        if (semestreAno==null){
            for (ClasseBase alunoMatriculado:this.getLista()){
                if (((AlunoMatriculado)alunoMatriculado).getAluno().getId().equals(idAluno)){
                    listaFinal.add((AlunoMatriculado) alunoMatriculado);
                }
            }
        }
        else{
            for (ClasseBase alunoMatriculado:this.getLista()){
                if (((AlunoMatriculado)alunoMatriculado).getAluno().getId().equals(idAluno)
                    && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestreAno)){
                    listaFinal.add((AlunoMatriculado) alunoMatriculado);
                }
            }
        }

        listaFinal.sort(Comparator.comparing( (AlunoMatriculado t) ->t.getTurma().formataSemestreAnoParaOrdenacao())
                .thenComparing((AlunoMatriculado t) ->t.getAluno().getNome()));
        return listaFinal;
    }
    public List<AlunoMatriculado> getListaMatriculasPorTurma(UUID idTurma){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getTurma().getId().equals(idTurma)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        listaFinal.sort(Comparator.comparing( (AlunoMatriculado t) ->t.getTurma().formataSemestreAnoParaOrdenacao())
                .thenComparing((AlunoMatriculado t) ->t.getAluno().getNome()));
        return listaFinal;
    }

    public List<AlunoMatriculado> getListaMatriculasPorDisciplina(UUID idDisciplina, String semestreAno){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getTurma().getDisciplina().getId().equals(idDisciplina)
                && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestreAno)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        listaFinal.sort(Comparator.comparing( (AlunoMatriculado t) ->t.getTurma().formataSemestreAnoParaOrdenacao())
                .thenComparing((AlunoMatriculado t) ->t.getAluno().getNome()));
        return listaFinal;
    }

    public List<AlunoMatriculado> getListaMatriculasPorProfessor(UUID idProfessor, String semestreAno){
        List<AlunoMatriculado> listaFinal = new ArrayList<>();
        for (ClasseBase alunoMatriculado:this.getLista()){
            if (((AlunoMatriculado)alunoMatriculado).getTurma().getProfessor().getId().equals(idProfessor)
                    && ((AlunoMatriculado)alunoMatriculado).getTurma().getSemestreAno().equals(semestreAno)){
                listaFinal.add((AlunoMatriculado) alunoMatriculado);
            }
        }
        listaFinal.sort(Comparator.comparing( (AlunoMatriculado t) ->t.getTurma().formataSemestreAnoParaOrdenacao())
                .thenComparing((AlunoMatriculado t) ->t.getAluno().getNome()));
        return listaFinal;
    }

    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }

    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<AlunoMatriculado>>() {});
    }
}
