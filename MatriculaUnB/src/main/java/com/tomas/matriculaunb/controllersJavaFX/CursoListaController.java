package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.servicos.ServicoCurso;
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

public class CursoListaController {

    private ServicoCurso servicoCurso = ServicoCurso.getInstance();
    private ObservableList<Curso> listaTabela;
    private FilteredList<Curso> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;
    private Curso cursoEditado;
    public Button btnAltera;

    public Curso getCursoEditado() {
        return cursoEditado;
    }

    public void setCursoEditado(Curso cursoEditado) {
        this.cursoEditado = cursoEditado;
    }

    public void initialize(){

        carregaListasTableView();

        TableColumn<Curso, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setPrefWidth(400);
        tituloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        tabela.getColumns().addAll( tituloColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Curso> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    btnAltera.fire();
                }
            });


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
        Curso curso = (Curso) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (curso == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }

        CursoEdicaoController controllerEdicao=new CursoEdicaoController();
        try {
            controllerEdicao.setCurso((Curso) curso.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        controllerEdicao.carregarModal();

        if (controllerEdicao.getCurso()!=null){
            this.setCursoEditado(controllerEdicao.getCurso());
            try {
                servicoCurso.alterar(this.getCursoEditado());
                servicoCurso.salvarArquivo(this.getCursoEditado());
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                tabela.refresh();
                Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Curso","Curso Salvo","Curso Salvo com Sucesso").showAndWait();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Curso","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }

    }

    public void onBtnExcluirClick(ActionEvent actionEvent) {
        Curso curso = (Curso) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (curso == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }
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


    public void onBtnNovoClick(ActionEvent actionEvent) {

            CursoEdicaoController controllerEdicao=new CursoEdicaoController();
            controllerEdicao.setCurso(null);
            controllerEdicao.carregarModal();

            if (controllerEdicao.getCurso()!=null){
                this.setCursoEditado(controllerEdicao.getCurso());
                try {
                    servicoCurso.incluir(this.getCursoEditado());
                    servicoCurso.salvarArquivo();
                    carregaListasTableView();
                    tabela.setItems(this.listaFiltrada);
                    Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Curso","Curso Salvo","Curso Salvo com Sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Curso","Erro ao Salvar",e.getMessage()).showAndWait();
                }
            }


    }
}
