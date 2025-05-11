package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Professor;

import java.util.List;

public class ServicoProfessor extends ClasseServicoBase{

    final String nomeArquivo="professor.txt";

    //CLASSE SINGLETON
    private static ServicoProfessor instance = null;
    private ServicoProfessor(){}
    public static ServicoProfessor getInstance() {
        if (instance == null) {
            instance = new ServicoProfessor();
        }
        return instance;
    }

    @Override
    public boolean podeIncluir(ClasseBase professor) throws Exception{

        professor.validar();

        if (this.existe(professor.getId())){
            throw new Exception("Impossível Incluir Professor - id duplicado");
        }

        if (matriculaDuplicada((Professor) professor,false)){
            throw new Exception("Impossível Incluir Professor - matricula duplicada");
        }

        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase professorAlterado) throws Exception{

        professorAlterado.validar();

        if (matriculaDuplicada((Professor) professorAlterado,true)){
            throw new Exception("Impossível Alterar Professor - matricula duplicada");
        }

        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase professor) throws Exception{

        ServicoTurma servicoTurma = ServicoTurma.getInstance();
        if (servicoTurma.existeProfessor(professor.getId())){
            throw new Exception("Impossível Excluir Professor - Professor utilizado por alguma turma");
        }


        return true;
    }

    public boolean matriculaDuplicada(Professor professor, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Professor)obj).getMatricula().equals(professor.getMatricula()));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Professor)obj).getMatricula().equals(professor.getMatricula())
                        && !obj.getId().equals(professor.getId()));
    }

    public Professor retornarPorNome(String nome) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Professor professorRetornado;
        professorRetornado = (Professor) this.getLista().stream()
                .filter(obj -> ((Professor)obj).getNome().equals(nome))
                .findFirst()
                .orElse(null);
        if (professorRetornado!=null){
            return (Professor) professorRetornado.clone();
        }
        return null;
    }
    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }
    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<Professor>>() {});
    }
}
