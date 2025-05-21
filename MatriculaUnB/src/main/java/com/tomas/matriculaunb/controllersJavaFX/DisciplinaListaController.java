package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.Disciplina;
import com.tomas.matriculaunb.servicos.ServicoAluno;
import com.tomas.matriculaunb.servicos.ServicoDisciplina;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleIntegerProperty;
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

public class DisciplinaListaController {

    private ServicoDisciplina servicoDisciplina = ServicoDisciplina.getInstance();
    private ObservableList<Disciplina> listaTabela;
    private FilteredList<Disciplina> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;
    private Disciplina disciplinaEditada;
    public Button btnAltera;

    public Disciplina getDisciplinaEditada() {
        return disciplinaEditada;
    }

    public void setDisciplinaEditada(Disciplina disciplinaEditada) {
        this.disciplinaEditada = disciplinaEditada;
    }

    public void initialize(){

        carregaListasTableView();

        TableColumn<Disciplina, String> codigoColumn = new TableColumn<>("Código");
        codigoColumn.setPrefWidth(50);
        codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        TableColumn<Disciplina, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setPrefWidth(400);
        tituloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        TableColumn<Disciplina, Integer> cargaHorariaColumn = new TableColumn<>("Carga horária");
        cargaHorariaColumn.setPrefWidth(100);
        cargaHorariaColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCargaHoraria()).asObject());
        tabela.getColumns().addAll( codigoColumn,tituloColumn,cargaHorariaColumn);
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
        List<Disciplina> listaDisciplina = new ArrayList<>();
        for (ClasseBase obj:servicoDisciplina.getLista()){
            listaDisciplina.add((Disciplina) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaDisciplina);
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
    }

    public void onTxtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(disciplina -> {
            return disciplina.getCodigo().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || disciplina.getTitulo().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }

    public void onBtnAlteraClick(ActionEvent actionEvent) {
        Disciplina disciplina = (Disciplina) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (disciplina != null){
            DisciplinaEdicaoController controllerEdicao=new DisciplinaEdicaoController();
            try {
                controllerEdicao.setDisciplina((Disciplina) disciplina.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            controllerEdicao.carregarModal();

            if (controllerEdicao.getDisciplina()!=null){
                this.setDisciplinaEditada(controllerEdicao.getDisciplina());
                try {
                    servicoDisciplina.alterar(this.getDisciplinaEditada());
                    servicoDisciplina.salvarArquivo();
                    carregaListasTableView();
                    tabela.setItems(this.listaFiltrada);
                    tabela.refresh();
                    Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Disciplina","Disciplina Salva","Disciplina Salva com Sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Disciplina","Erro ao Salvar",e.getMessage()).showAndWait();
                }
            }
        }
    }

    public void onBtnExcluirClick(ActionEvent actionEvent) {
        Disciplina disciplina = (Disciplina) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (disciplina != null){

            Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Disciplina", "Excluir?","Deseja excluir a disciplina "+disciplina.getTitulo() + "?");
            Optional<ButtonType> btnAlert = alert.showAndWait();
            btnAlert.ifPresent(btn->{
                if (btn.getText().equals("OK")){
                    try {
                        servicoDisciplina.excluir(disciplina);
                        servicoDisciplina.salvarArquivo();
                        carregaListasTableView();
                        tabela.setItems(this.listaFiltrada);
                        Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Disciplina","Exclusão com Sucesso","Disciplina "+ disciplina.getTitulo() + " excluído com sucesso").showAndWait();
                    } catch (Exception e) {
                        Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Disciplina","Erro ao excluir",e.getMessage()).showAndWait();
                    }
                }
            });
        }
        else {
            Util.getAlert(Alert.AlertType.WARNING,"Exclusão de Disciplina","Impossível Excluir","Selecione uma disciplina para excluir").showAndWait();
        }
    }


    public void onBtnNovoClick(ActionEvent actionEvent) {
        DisciplinaEdicaoController controllerEdicao=new DisciplinaEdicaoController();
        controllerEdicao.setDisciplina(null);
        controllerEdicao.carregarModal();

        if (controllerEdicao.getDisciplina()!=null){
            this.setDisciplinaEditada(controllerEdicao.getDisciplina());
            try {
                servicoDisciplina.incluir(this.getDisciplinaEditada());
                servicoDisciplina.salvarArquivo();
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Disciplina","Disciplina Salva","Disciplina Salva com Sucesso").showAndWait();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Disciplina","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }
    }

    public void onBtnPreRequisitosClick(ActionEvent actionEvent) {
    }
}
