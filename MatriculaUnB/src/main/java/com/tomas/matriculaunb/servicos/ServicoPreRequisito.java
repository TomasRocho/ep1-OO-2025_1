package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicoPreRequisito extends ClasseServicoBase{

    final String nomeArquivo="preRequisito.txt";

    //CLASSE SINGLETON
    private static ServicoPreRequisito instance = null;
    private ServicoPreRequisito(){}
    public static ServicoPreRequisito getInstance() {
        if (instance == null) {
            instance = new ServicoPreRequisito();
        }
        return instance;
    }

    @Override
    public boolean podeIncluir(ClasseBase preRequisito) throws Exception{

        preRequisito.validar();

        if (this.existe(preRequisito.getId())){
            throw new Exception("Impossível Incluir pre requisito - id duplicado");
        }
        if (preRequisitoDuplicado((PreRequisito) preRequisito,false)){
            throw new Exception("Impossível Incluir pre requisito - pre requisito duplicado");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase preRequisito) throws Exception{

        preRequisito.validar();

        if (preRequisitoDuplicado((PreRequisito) preRequisito,true)){
            throw new Exception("Impossível alterar pre requisito - pre requisito duplicado");
        }
        return true;
    }



    public boolean preRequisitoDuplicado(PreRequisito preRequisito, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((PreRequisito)obj).getIdDisciplina().equals(preRequisito.getIdDisciplina())
                                                && ((PreRequisito)obj).getIdDisciplinaPreRequisito().equals(preRequisito.getIdDisciplinaPreRequisito()));
        }

        return this.getLista().stream()
                .anyMatch( obj->((PreRequisito)obj).getIdDisciplina().equals(preRequisito.getIdDisciplina())
                        && ((PreRequisito)obj).getIdDisciplinaPreRequisito().equals(preRequisito.getIdDisciplinaPreRequisito())
                        && !obj.getId().equals(preRequisito.getId()));

    }

    public List<Disciplina> getPreRequisitosDisciplina(UUID idDisciplina){
        ServicoDisciplina servicoDisciplina=ServicoDisciplina.getInstance();
        List<Disciplina> listaFinal = new ArrayList<>();
        for (ClasseBase preRequisito:this.getLista()){
            if (((PreRequisito)preRequisito).getIdDisciplina().equals(idDisciplina)){
                Disciplina disciplinaPreRequisito;
                try {
                    disciplinaPreRequisito = (Disciplina) servicoDisciplina.buscarObjeto(((PreRequisito) preRequisito).getIdDisciplinaPreRequisito());
                    listaFinal.add(disciplinaPreRequisito);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listaFinal;
    }
    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }
    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<PreRequisito>>() {});
    }

    public void excluir(UUID idDisciplina, UUID idPreRequisito)throws Exception {
        for(ClasseBase pre: this.getLista()){
            if (((PreRequisito)pre).getIdDisciplina().equals(idDisciplina)
                    && ((PreRequisito)pre).getIdDisciplinaPreRequisito().equals(idPreRequisito)){
                this.getLista().remove(pre);
                return;
            }
        }

    }


}
