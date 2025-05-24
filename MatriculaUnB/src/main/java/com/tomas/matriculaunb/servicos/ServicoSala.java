package com.tomas.matriculaunb.servicos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomas.matriculaunb.modelo.*;

import java.util.List;

public class ServicoSala extends ClasseServicoBase{
    final String nomeArquivo="sala.txt";

    //CLASSE SINGLETON
    private static ServicoSala instance = null;
    private ServicoSala(){}
    public static ServicoSala getInstance() {
        if (instance == null) {
            instance = new ServicoSala();
        }
        return instance;
    }
    @Override
    public boolean podeIncluir(ClasseBase sala) throws Exception{

        sala.validar();

        if (this.existe(sala.getId())){
            throw new Exception("Impossível Incluir Sala - id duplicado");
        }
        if (salaCampusDuplicado((Sala) sala,false)){
            throw new Exception("Impossível Incluir Sala - sala ja cadastrada");
        }
        return true;
    }

    @Override
    public boolean podeAlterar(ClasseBase salaAlterado) throws Exception{

        salaAlterado.validar();

        if (salaCampusDuplicado((Sala) salaAlterado,true)){
            throw new Exception("Impossível Alterar Sala - sala ja cadastrada");
        }
        return true;
    }

    @Override
    public boolean podeExcluir(ClasseBase sala) throws Exception{

        ServicoTurma servicoTurma = ServicoTurma.getInstance();
        if (servicoTurma.existeSala(sala.getId())){
            throw new Exception("Impossível excluir Sala - sala utilizada por alguma turma");
        }
        return true;
    }



    public boolean salaCampusDuplicado(Sala sala, boolean alteracao){
        if (this.getLista()==null){
            return false;
        }
        if (!alteracao){
            return this.getLista().stream()
                    .anyMatch( obj->((Sala)obj).getLocal().equals(sala.getLocal())
                    && ((Sala)obj).getCampus().equals(sala.getCampus()));
        }
        return this.getLista().stream()
                .anyMatch( obj->((Sala)obj).getLocal().equals(sala.getLocal())
                        && ((Sala)obj).getCampus().equals(sala.getCampus())
                        && !obj.getId().equals(sala.getId()));
    }

    public void salvarArquivo() throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);
    }

    public void carregarArquivo() throws Exception{
        this.lerArquivoParaLista(nomeArquivo,new TypeReference<List<Sala>>() {});
    }

    public void salvarArquivo(Sala salaAlterada) throws Exception{
        this.salvarListaParaArquivo(nomeArquivo);

        //alterar as salas da lista de turmas
        ServicoTurma servicoTurma = ServicoTurma.getInstance();
        List<ClasseBase> listaTurmas = servicoTurma.getTurmasPorSala(salaAlterada);
        for(ClasseBase turma:listaTurmas){
            ((Turma)turma).setSala(salaAlterada);
        }
        servicoTurma.salvarArquivo();

        //alterar as salas da lista de alunosMatriculados
        ServicoAlunoMatriculado servicoAlunoMatriculado=ServicoAlunoMatriculado.getInstance();
        List<ClasseBase> listaAlunosMatriculados = servicoAlunoMatriculado.getAlunosMatriculadosPorSala(salaAlterada);
        for(ClasseBase alunoMatriculado:listaAlunosMatriculados){
            ((AlunoMatriculado)alunoMatriculado).getTurma().setSala(salaAlterada);
        }
        servicoAlunoMatriculado.salvarArquivo();

    }
}
