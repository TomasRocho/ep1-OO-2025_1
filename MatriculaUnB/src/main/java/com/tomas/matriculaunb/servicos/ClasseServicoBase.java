package com.tomas.matriculaunb.servicos;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tomas.matriculaunb.modelo.ClasseBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public abstract class ClasseServicoBase {
    private List<ClasseBase> lista;
    final String diretorioArquivos="dados";



    public List<ClasseBase> getLista() {
        return lista;
    }

    public void setLista(List<ClasseBase> lista) {
        this.lista = lista;
    }

    public boolean podeIncluir(ClasseBase objClasseBase)throws Exception{
        return true;
    }

    public boolean podeAlterar(ClasseBase objClasseBase)throws Exception{
        return true;
    }

    public boolean podeExcluir(ClasseBase objClasseBase)throws Exception{
        return true;
    }

    public void incluir(ClasseBase objClasseBase) throws Exception{
        if (this.podeIncluir(objClasseBase)){
            if (this.getLista() == null){
                this.setLista(new ArrayList<>());
            }
            this.getLista().add(objClasseBase);
        }

    }

    public void excluir(ClasseBase objClasseBase)throws Exception {
        if (this.podeExcluir(objClasseBase)){
            if (this.getLista() != null) {
                this.getLista().remove(objClasseBase);
            }
        }

    }

    public void alterar(ClasseBase objAlterado) throws Exception{
        if (this.podeAlterar(objAlterado)){

            int index = IntStream.range(0, this.getLista().size())
                    .filter(i -> this.getLista().get(i).getId().equals(objAlterado.getId()))
                    .findFirst()
                    .orElse(-1);
            if (index!=-1){
                this.getLista().set(index,objAlterado);
            }

        }

    }

    public ClasseBase buscarObjeto(UUID id) throws Exception{

        if (lista==null){
            return null;
        }
        ClasseBase objRetornado;
        objRetornado = lista.stream()
                .filter(obj -> obj.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (objRetornado!=null){
            return objRetornado.clone();
        }
        return null;
    }

    public boolean existe(UUID id){
        if (lista==null){
            return false;
        }
        return lista.stream().anyMatch(obj -> obj.getId()==id);
    }

    public void exibirLista() {
        for (ClasseBase obj : this.getLista()) {
            obj.exibirDados();
        }
    }
    public void salvarListaParaArquivo(String nomeArquivo) throws Exception{

        Path path = Paths.get(diretorioArquivos);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        ObjectMapper mapper = new ObjectMapper();
        File arquivo = new File(diretorioArquivos + File.separator + nomeArquivo);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(arquivo, this.getLista());
    }

    public <T> void  lerArquivoParaLista(String nomeArquivo, TypeReference<List<T>> typeRef) throws Exception{
        Path path = Paths.get(diretorioArquivos);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<T> listaLida =mapper.readValue(new File(diretorioArquivos + File.separator + nomeArquivo),typeRef);
            this.setLista((List<ClasseBase>) listaLida);
        } catch (IOException e) {
            this.setLista(new ArrayList<>());
            //throw new Exception("Erro ao carregar a lista");
        }

    }
}

