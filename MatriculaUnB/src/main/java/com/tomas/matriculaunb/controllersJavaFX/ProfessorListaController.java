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
    private Professor professorEditado;
    public Button btnAltera;

    public Professor getProfessorEditado() {
        return professorEditado;
    }

    public void setProfessorEditado(Professor professorEditado) {
        this.professorEditado = professorEditado;
    }

    public void initialize(){

        carregaListasTableView();

        TableColumn<Professor, String> matriculaColumn = new TableColumn<>("Matrícula");
        matriculaColumn.setPrefWidth(80);
        matriculaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        TableColumn<Professor, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setPrefWidth(500);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        tabela.getColumns().addAll( matriculaColumn,nomeColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Professor> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    btnAltera.fire();
                }
            });


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
        ProfessorEdicaoController controllerEdicao=new ProfessorEdicaoController();
        controllerEdicao.setProfessor(null);
        controllerEdicao.carregarModal();

        if (controllerEdicao.getProfessor()!=null){
            this.setProfessorEditado(controllerEdicao.getProfessor());
            try {
                servicoProfessor.incluir(this.getProfessorEditado());
                servicoProfessor.salvarArquivo();
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Professor","Professor Salvo","Professor Salvo com Sucesso").showAndWait();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Professor","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }
    }
    public void onBtnAlteraClick(ActionEvent actionEvent) {
        Professor professor = (Professor) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (professor != null){
            ProfessorEdicaoController controllerEdicao=new ProfessorEdicaoController();
            try {
                controllerEdicao.setProfessor((Professor) professor.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            controllerEdicao.carregarModal();

            if (controllerEdicao.getProfessor()!=null){
                this.setProfessorEditado(controllerEdicao.getProfessor());
                try {
                    servicoProfessor.alterar(this.getProfessorEditado());
                    servicoProfessor.salvarArquivo();
                    carregaListasTableView();
                    tabela.setItems(this.listaFiltrada);
                    tabela.refresh();
                    Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Professor","Professor Salvo","Professor Salvo com Sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Professor","Erro ao Salvar",e.getMessage()).showAndWait();
                }
            }
        }
    }
}
