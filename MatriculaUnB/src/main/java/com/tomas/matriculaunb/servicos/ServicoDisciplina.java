package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Disciplina;

import java.util.List;

public class ServicoDisciplina extends ClasseServicoBase{

    final String nomeArquivo="disciplina.txt";

    //CLASSE SINGLETON
    private static ServicoDisciplina instance = null;
    private ServicoDisciplina(){}
    public static ServicoDisciplina getInstance() {
        if (instance == null) {
            instance = new ServicoDisciplina();
        }
        return instance;
    }

    @Override
    public boolean podeIncluir(ClasseBase disciplina) throws Exception{

        disciplina.validar();

        if (this.existe(disciplina.getId())){
            throw new Exception("Impossível Incluir Disciplina - id duplicado");
        }
        if (tituloDuplicado((Disciplina) disciplina,false)){
            throw new Exception("Impossível Incluir Disciplina - título duplicado");
        }
        if (codigoDuplicado((Disciplina) disciplina,false)){
            throw new Exception("Impossível Incluir Disciplina - codigo duplicado");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase disciplinaAlterada) throws Exception{


        disciplinaAlterada.validar();

        if (tituloDuplicado((Disciplina) disciplinaAlterada,true)){
            throw new Exception("Impossível Alterar Disciplina - título duplicado");
        }
        if (codigoDuplicado((Disciplina) disciplinaAlterada,true)){
            throw new Exception("Impossível Alterar Disciplina - codigo duplicado");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase disciplina) throws Exception{

        ServicoTurma servicoTurma = ServicoTurma.getInstance();
        if (servicoTurma.existeDisciplina(disciplina.getId())){
            throw new Exception("Impossível Excluir Disciplina - Disciplina utilizada por alguma turma");
        }

        return true;
    }

    public Disciplina retornarPorTitulo(String titulo) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Disciplina disciplinaRetornada;
        disciplinaRetornada = (Disciplina) this.getLista().stream()
                .filter(obj -> ((Disciplina)obj).getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
        if (disciplinaRetornada!=null){
            return (Disciplina) disciplinaRetornada.clone();
        }
        return null;
    }

    public boolean tituloDuplicado(Disciplina disciplina, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Disciplina)obj).getTitulo().equals(disciplina.getTitulo()));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Disciplina)obj).getTitulo().equals(disciplina.getTitulo())
                        && !obj.getId().equals(disciplina.getId()));
    }
    public boolean codigoDuplicado(Disciplina disciplina, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Disciplina)obj).getCodigo().equals(disciplina.getCodigo()));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Disciplina)obj).getCodigo().equals(disciplina.getCodigo())
                        && !obj.getId().equals(disciplina.getId()));
    }

    public Disciplina retornarPorCodigo(String codigo) throws Exception{

        if (this.getLista()==null){
            return null;
        }
        Disciplina disciplinaRetornada;
        disciplinaRetornada = (Disciplina) this.getLista().stream()
                .filter(obj -> ((Disciplina)obj).getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
        if (disciplinaRetornada!=null){
            return (Disciplina) disciplinaRetornada.clone();
        }
        return null;
    }
    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }
    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<Disciplina>>() {});
    }

}
