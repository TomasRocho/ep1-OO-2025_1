package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.Professor;
import com.tomas.matriculaunb.servicos.ServicoCurso;
import com.tomas.matriculaunb.servicos.ServicoProfessor;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CursoListaController {

    private ServicoCurso servicoCurso = ServicoCurso.getInstance();
    private ObservableList<Curso> listaTabela;
    private FilteredList<Curso> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;

    public void initialize(){

        carregaListasTableView();

        TableColumn<Curso, String> tituloColumn = new TableColumn<>("titulo");
        tituloColumn.setPrefWidth(300);
        tituloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        tabela.getColumns().addAll( tituloColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Curso> row = new TableRow<>();
            /*
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    btnAltera.fire();
                }
            });

             */
            return row ;
        });
    }

    public void carregaListasTableView(){
        List<Curso> listaCursos = new ArrayList<>();
        for (ClasseBase obj:servicoCurso.getLista()){
            listaCursos.add((Curso) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaCursos);
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
    }

    public void onTxtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(curso -> {
            return curso.getTitulo().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }

    public void onBtnAlteraClick(ActionEvent actionEvent) {
    }

    public void onBtnExcluirClick(ActionEvent actionEvent) {
        Curso curso = (Curso) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (curso != null){

            Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Curso", "Excluir?","Deseja excluir o curso "+curso.getTitulo() + "?");
            Optional<ButtonType> btnAlert = alert.showAndWait();
            btnAlert.ifPresent(btn->{
                if (btn.getText().equals("OK")){
                    try {
                        servicoCurso.excluir(curso);
                        servicoCurso.salvarArquivo();
                        carregaListasTableView();
                        tabela.setItems(this.listaFiltrada);
                        Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Curso","Exclusão com Sucesso","Curso "+ curso.getTitulo() + " excluído com sucesso").showAndWait();
                    } catch (Exception e) {
                        Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Curso","Erro ao excluir",e.getMessage()).showAndWait();
                    }
                }
            });
        }
        else {
            Util.getAlert(Alert.AlertType.WARNING,"Exclusão de Curso","Impossível Excluir","Selecione um curso para excluir").showAndWait();
        }
    }


    public void onBtnNovoClick(ActionEvent actionEvent) {
    }
}
