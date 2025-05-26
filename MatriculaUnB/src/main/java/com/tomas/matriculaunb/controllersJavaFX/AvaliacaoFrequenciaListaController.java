package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.servicos.ServicoAlunoMatriculado;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AvaliacaoFrequenciaListaController {

    private ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();
    private ObservableList<AlunoMatriculado> listaTabela;
    private FilteredList<AlunoMatriculado> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;
    private AlunoMatriculado alunoMatriculadoEditado;
    private Turma turmaSelecionada;
    public Button btnNotas;

    public AlunoMatriculado getAlunoMatriculadoEditado() {
        return alunoMatriculadoEditado;
    }

    public void setAlunoMatriculadoEditado(AlunoMatriculado alunoMatriculadoEditado) {
        this.alunoMatriculadoEditado = alunoMatriculadoEditado;
    }

    public void initialize(){

        //carregaListasTableView();

        TableColumn<AlunoMatriculado, String> alunoColumn = new TableColumn<>("Aluno");
        alunoColumn.setPrefWidth(150);
        alunoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAluno().getNome()));
        TableColumn<AlunoMatriculado, Float> p1Column = new TableColumn<>("P1");
        p1Column.setPrefWidth(60);
        p1Column.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNotaP1()).asObject());
        TableColumn<AlunoMatriculado, Float> p2Column = new TableColumn<>("P2");
        p2Column.setPrefWidth(60);
        p2Column.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNotaP2()).asObject());
        TableColumn<AlunoMatriculado, Float> p3Column = new TableColumn<>("P3");
        p3Column.setPrefWidth(60);
        p3Column.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNotaP3()).asObject());
        TableColumn<AlunoMatriculado, Float> p4Column = new TableColumn<>("Lista");
        p4Column.setPrefWidth(60);
        p4Column.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNotaL()).asObject());
        TableColumn<AlunoMatriculado, Float> p5Column = new TableColumn<>("Seminário");
        p5Column.setPrefWidth(100);
        p5Column.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNotaS()).asObject());
        TableColumn<AlunoMatriculado, Integer> faltaColumn = new TableColumn<>("Faltas");
        faltaColumn.setPrefWidth(60);
        faltaColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFaltas()).asObject());
        TableColumn<AlunoMatriculado, String> trancadoColumn = new TableColumn<>("Trancado");
        trancadoColumn.setPrefWidth(70);
        trancadoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isTrancado()?"Sim":"Não"));
        TableColumn<AlunoMatriculado, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setPrefWidth(150);
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().retornarStatus().toString()));
        TableColumn<AlunoMatriculado, Float> mediaFinalColumn = new TableColumn<>("Média Final");
        mediaFinalColumn.setPrefWidth(100);
        mediaFinalColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().calcularMediaFinal()).asObject());
        TableColumn<AlunoMatriculado, Float> percentualFaltasColumn = new TableColumn<>("% Faltas");
        percentualFaltasColumn.setPrefWidth(80);
        percentualFaltasColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().calcularPercentualFaltas()).asObject());

        tabela.getColumns().addAll( alunoColumn,p1Column,p2Column,p3Column,p4Column,p5Column,faltaColumn,trancadoColumn,statusColumn,mediaFinalColumn,percentualFaltasColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<AlunoMatriculado> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    btnNotas.fire();
                }
            });

            return row ;
        });
    }

    public void carregaListasTableView(){
        List<AlunoMatriculado> listaAlunoMatriculado = new ArrayList<>();
        for (ClasseBase obj:servicoAlunoMatriculado.getLista()){
            listaAlunoMatriculado.add((AlunoMatriculado) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaAlunoMatriculado);
        this.listaTabela.sort(Comparator.comparing(alunoMatriculado -> alunoMatriculado.getAluno().getNome()));
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
        this.listaFiltrada.setPredicate(alunoMatriculado -> {
            return alunoMatriculado.getTurma().equals(turmaSelecionada);
        });
    }


    public void btnExcluirClick(ActionEvent actionEvent) {
        AlunoMatriculado alunoMatriculado = (AlunoMatriculado) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (alunoMatriculado == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }

        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Matrícula", "Excluir?","Deseja excluir "+alunoMatriculado.getAluno().getNome() + " da turma " + alunoMatriculado.getTurma().getCodigo() + "?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().trim().equalsIgnoreCase("OK")){
                try {
                    servicoAlunoMatriculado.excluir(alunoMatriculado);
                    servicoAlunoMatriculado.salvarArquivo();
                    carregaListasTableView();
                    tabela.setItems(this.listaFiltrada);
                    Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Matrícula","Exclusão com Sucesso",alunoMatriculado.getAluno().getNome() + " excluído com sucesso da turma " + alunoMatriculado.getTurma().getCodigo()).showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Matrícula","Erro ao excluir",e.getMessage()).showAndWait();
                }
            }
        });


    }



    public void btnSelecionaTurmaClick(ActionEvent actionEvent) {
        TurmaSelecaoController controllerSelecao=new TurmaSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getTurmaSelecionado()!=null){
                turmaSelecionada=controllerSelecao.getTurmaSelecionado();
                txtProcura.setText(turmaSelecionada.getCodigo()+"-"+turmaSelecionada.getDisciplina().getTitulo()+"-"+turmaSelecionada.getProfessor().getNome()+"-" + turmaSelecionada.getSemestreAno());
                carregaListasTableView();
                tabela.setItems(listaFiltrada);
            }
            else{
                txtProcura.setText("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnNotasClick(ActionEvent actionEvent) {
        AlunoMatriculado alunoMatriculado = (AlunoMatriculado) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (alunoMatriculado == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }
        NotaEdicaoController controllerEdicao=new NotaEdicaoController();
        try {
            controllerEdicao.setAlunoMatriculado((AlunoMatriculado) alunoMatriculado.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        controllerEdicao.carregarModal();

        if (controllerEdicao.getAlunoMatriculado()!=null){
            this.setAlunoMatriculadoEditado(controllerEdicao.getAlunoMatriculado());
            try {
                servicoAlunoMatriculado.alterar(this.getAlunoMatriculadoEditado());
                servicoAlunoMatriculado.salvarArquivo();
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                tabela.refresh();
                Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Notas","Notas Salvas","Notas Salvas com Sucesso").showAndWait();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Notas","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }

    }

    public void mnuIncluirFaltaClick(ActionEvent actionEvent) {
        AlunoMatriculado alunoMatriculado = (AlunoMatriculado) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (alunoMatriculado == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }
        alunoMatriculado.setFaltas(alunoMatriculado.getFaltas()+1);
        try {
            servicoAlunoMatriculado.alterar(alunoMatriculado);
            servicoAlunoMatriculado.salvarArquivo();
            carregaListasTableView();
            tabela.setItems(this.listaFiltrada);
            tabela.refresh();
        } catch (Exception e) {
            Util.getAlert(Alert.AlertType.ERROR,"Inclusão de falta","Erro ao Salvar",e.getMessage()).showAndWait();
        }

    }

    public void mnuExcluirFaltaClick(ActionEvent actionEvent) {
        AlunoMatriculado alunoMatriculado = (AlunoMatriculado) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (alunoMatriculado == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }
        if (alunoMatriculado != null && alunoMatriculado.getFaltas() > 0){
            alunoMatriculado.setFaltas(alunoMatriculado.getFaltas()-1);
            try {
                servicoAlunoMatriculado.alterar(alunoMatriculado);
                servicoAlunoMatriculado.salvarArquivo();
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                tabela.refresh();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Inclusão de falta","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }
    }
}
