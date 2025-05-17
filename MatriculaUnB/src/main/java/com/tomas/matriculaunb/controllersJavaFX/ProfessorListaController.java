package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.Professor;
import com.tomas.matriculaunb.servicos.ServicoCurso;
import com.tomas.matriculaunb.servicos.ServicoProfessor;
import com.tomas.matriculaunb.util.Util;
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

public class ProfessorListaController {

    private ServicoProfessor servicoProfessor = ServicoProfessor.getInstance();
    private ObservableList<Professor> listaTabela;
    private FilteredList<Professor> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;

    public void initialize(){

        carregaListasTableView();

        TableColumn<Professor, String> matriculaColumn = new TableColumn<>("matricula");
        matriculaColumn.setPrefWidth(300);
        matriculaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        TableColumn<Professor, String> nomeColumn = new TableColumn<>("nome");
        nomeColumn.setPrefWidth(300);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        tabela.getColumns().addAll( matriculaColumn,nomeColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Professor> row = new TableRow<>();
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
        List<Professor> listaProfessores = new ArrayList<>();
        for (ClasseBase obj:servicoProfessor.getLista()){
            listaProfessores.add((Professor) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaProfessores);
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
    }

    public void onTxtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(professor -> {
            return professor.getNome().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || professor.getMatricula().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }

    public void onBtnAlteraClick(ActionEvent actionEvent) {
    }

    public void onBtnExcluirClick(ActionEvent actionEvent) {
        Professor professor = (Professor) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (professor != null){

            Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Professor", "Excluir?","Deseja excluir o professor "+professor.getNome() + "?");
            Optional<ButtonType> btnAlert = alert.showAndWait();
            btnAlert.ifPresent(btn->{
                if (btn.getText().equals("OK")){
                    try {
                        servicoProfessor.excluir(professor);
                        servicoProfessor.salvarArquivo();
                        carregaListasTableView();
                        tabela.setItems(this.listaFiltrada);
                        Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Professor","Exclusão com Sucesso","Professor "+ professor.getNome() + " excluído com sucesso").showAndWait();
                    } catch (Exception e) {
                        Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Professor","Erro ao excluir",e.getMessage()).showAndWait();
                    }
                }
            });
        }
        else {
            Util.getAlert(Alert.AlertType.WARNING,"Exclusão de Professor","Impossível Excluir","Selecione um Professor para excluir").showAndWait();
        }
    }


    public void onBtnNovoClick(ActionEvent actionEvent) {
    }
}
