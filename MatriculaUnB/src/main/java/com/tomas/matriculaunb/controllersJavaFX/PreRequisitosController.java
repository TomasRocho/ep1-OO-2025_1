package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.servicos.ServicoPreRequisito;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PreRequisitosController {
    private Disciplina disciplinaSelecionada;
    private Disciplina preRequisitoSelecionado;
    public TableView tabela;
    private ObservableList<Disciplina> listaTabela;
    private Dialog<ButtonType> dialog;
    ServicoPreRequisito servicoPreRequisito = ServicoPreRequisito.getInstance();

    public Disciplina getPreRequisitoSelecionado() {
        return preRequisitoSelecionado;
    }

    public void setPreRequisitoSelecionado(Disciplina preRequisitoSelecionado) {
        this.preRequisitoSelecionado = preRequisitoSelecionado;
    }

    public Disciplina getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

    public void initialize(){

        List<Disciplina> listaPreRequisitos = servicoPreRequisito.getPreRequisitosDisciplina(this.getDisciplinaSelecionada().getId());
        this.listaTabela = FXCollections.observableArrayList(listaPreRequisitos);
        this.listaTabela.sort(Comparator.comparing(disciplina -> disciplina.getTitulo()));
        TableColumn<Disciplina, String> codigoColumn = new TableColumn<>("Código");
        codigoColumn.setPrefWidth(100);
        codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        TableColumn<Disciplina, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setPrefWidth(300);
        tituloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        tabela.getColumns().addAll( codigoColumn,tituloColumn);
        tabela.setItems(listaTabela);

    }

    public void carregarModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("preRequisitos.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        dialog.setTitle("Pré-Requisitos");
        dialog.showAndWait();
    }
    public void btnIncluirClick() {
        DisciplinaSelecaoController controllerSelecao=new DisciplinaSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getDisciplinaSelecionada()!=null){
                this.setPreRequisitoSelecionado(controllerSelecao.getDisciplinaSelecionada());
                PreRequisito preRequisito = new PreRequisito(this.getDisciplinaSelecionada().getId(),this.getPreRequisitoSelecionado().getId());
                try {
                    servicoPreRequisito.incluir(preRequisito);
                    servicoPreRequisito.salvarArquivo();
                    List<Disciplina> listaPreRequisitos = servicoPreRequisito.getPreRequisitosDisciplina(this.getDisciplinaSelecionada().getId());
                    listaTabela = FXCollections.observableArrayList(listaPreRequisitos);
                    listaTabela.sort(Comparator.comparing(disciplina -> disciplina.getTitulo()));
                    tabela.setItems(listaTabela);
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Inclusão de Pré-requisito","Erro ao incluir",e.getMessage()).showAndWait();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void btnExcluirClick() {
        Disciplina disciplina = (Disciplina) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (disciplina == null){
            Util.getAlert(Alert.AlertType.WARNING,"Exclusão de Pré-Requisito","Impossível Excluir","Selecione um pré-requisito para excluir").showAndWait();
            return;
        }
        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Pré-requisito", "Excluir?","Deseja excluir o pré-requisito "+disciplina.getTitulo() + "?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().trim().equalsIgnoreCase("OK")){
                try {
                    servicoPreRequisito.excluir(this.getDisciplinaSelecionada().getId(),disciplina.getId());
                    servicoPreRequisito.salvarArquivo();
                    List<Disciplina> listaPreRequisitos = servicoPreRequisito.getPreRequisitosDisciplina(this.getDisciplinaSelecionada().getId());
                    listaTabela = FXCollections.observableArrayList(listaPreRequisitos);
                    listaTabela.sort(Comparator.comparing(disciplinaLista -> disciplinaLista.getTitulo()));
                    tabela.setItems(listaTabela);
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Pré-requisito","Erro ao excluir",e.getMessage()).showAndWait();
                }
            }
        });

    }
}
