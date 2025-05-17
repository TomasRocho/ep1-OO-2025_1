package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.servicos.ServicoAluno;
import com.tomas.matriculaunb.servicos.ServicoCurso;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleBooleanProperty;
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

public class AlunoListaController {

    private ServicoAluno servicoAluno = ServicoAluno.getInstance();
    private ObservableList<Aluno> listaTabela;
    private FilteredList<Aluno> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;

    public void initialize(){

        carregaListasTableView();

        TableColumn<Aluno, String> matriculaColumn = new TableColumn<>("Matrícula");
        matriculaColumn.setPrefWidth(100);
        matriculaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        TableColumn<Aluno, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setPrefWidth(300);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        TableColumn<Aluno, String> cursoColumn = new TableColumn<>("Curso");
        cursoColumn.setPrefWidth(200);
        cursoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurso().getTitulo()));
        TableColumn<Aluno, String> especialColumn = new TableColumn<>("Especial");
        especialColumn.setPrefWidth(70);
        especialColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isEspecial() ?"Sim":"Não"));
        tabela.getColumns().addAll( matriculaColumn,nomeColumn,cursoColumn,especialColumn);
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
        List<Aluno> listaAlunos = new ArrayList<>();
        for (ClasseBase obj:servicoAluno.getLista()){
            listaAlunos.add((Aluno) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaAlunos);
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
    }

    public void onTxtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(aluno -> {
            return aluno.getNome().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || aluno.getMatricula().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }

    public void onBtnAlteraClick(ActionEvent actionEvent) {
    }

    public void onBtnExcluirClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno != null){

            Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Aluno", "Excluir?","Deseja excluir o aluno "+aluno.getNome() + "?");
            Optional<ButtonType> btnAlert = alert.showAndWait();
            btnAlert.ifPresent(btn->{
                if (btn.getText().equals("OK")){
                    try {
                        servicoAluno.excluir(aluno);
                        servicoAluno.salvarArquivo();
                        carregaListasTableView();
                        tabela.setItems(this.listaFiltrada);
                        Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Aluno","Exclusão com Sucesso","Aluno "+ aluno.getNome() + " excluído com sucesso").showAndWait();
                    } catch (Exception e) {
                        Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Aluno","Erro ao excluir",e.getMessage()).showAndWait();
                    }
                }
            });
        }
        else {
            Util.getAlert(Alert.AlertType.WARNING,"Exclusão de Aluno","Impossível Excluir","Selecione um aluno para excluir").showAndWait();
        }
    }


    public void onBtnNovoClick(ActionEvent actionEvent) {
    }
}
