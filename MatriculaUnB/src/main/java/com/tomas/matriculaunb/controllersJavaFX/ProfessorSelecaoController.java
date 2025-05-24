package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Professor;
import com.tomas.matriculaunb.servicos.ServicoProfessor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProfessorSelecaoController {
    private Professor professorSelecionado;
    public TableView tabela;
    public TextField txtProcura;
    private ObservableList<Professor> listaTabela;
    private FilteredList<Professor> listaFiltrada;
    private Dialog<ButtonType> dialog;




    public Professor getProfessorSelecionado() {
        return professorSelecionado;
    }

    public void setProfessorSelecionado(Professor professorSelecionado) {
        this.professorSelecionado = professorSelecionado;
    }

    public void initialize(){
        ServicoProfessor servicoProfessor = ServicoProfessor.getInstance();
        List<Professor> listaSelecaoProfessores = new ArrayList<>();
        for (ClasseBase obj:servicoProfessor.getLista()){
            listaSelecaoProfessores.add((Professor) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaSelecaoProfessores);
        this.listaTabela.sort(Comparator.comparing(professor -> professor.getNome()));
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
        TableColumn<Professor, String> matriculaColumn = new TableColumn<>("Matrícula");
        matriculaColumn.setPrefWidth(100);
        matriculaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        TableColumn<Professor, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setPrefWidth(300);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        tabela.getColumns().addAll( matriculaColumn,nomeColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Professor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.setProfessorSelecionado((Professor) this.tabela.getSelectionModel().selectedItemProperty().get());
                    dialog.close();
                }
            });
            return row ;
        });

    }

    public void carregarModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("professorSelecao.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        dialog.setTitle("Seleção de Professor");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")){
                if (this.tabela.getSelectionModel().selectedItemProperty().get()!=null){
                    this.setProfessorSelecionado((Professor) this.tabela.getSelectionModel().selectedItemProperty().get());
                }
                else{
                    this.setProfessorSelecionado(null);
                }
            }
        });
    }
    public void txtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(professor -> {
            return professor.getNome().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || professor.getMatricula().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }
}
