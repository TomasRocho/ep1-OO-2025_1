package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.modelo.Disciplina;
import com.tomas.matriculaunb.modelo.Turma;
import com.tomas.matriculaunb.servicos.ServicoDisciplina;
import com.tomas.matriculaunb.servicos.ServicoTurma;
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

public class TurmaListaController {

    private ServicoTurma servicoTurma = ServicoTurma.getInstance();
    private ObservableList<Turma> listaTabela;
    private FilteredList<Turma> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;

    public void initialize(){

        carregaListasTableView();

        TableColumn<Turma, String> codigoColumn = new TableColumn<>("Código");
        codigoColumn.setPrefWidth(60);
        codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        TableColumn<Turma, String> disciplinaColumn = new TableColumn<>("Disciplina");
        disciplinaColumn.setPrefWidth(150);
        disciplinaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisciplina().getTitulo()));
        TableColumn<Turma, String> professorColumn = new TableColumn<>("Professor");
        professorColumn.setPrefWidth(150);
        professorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfessor().getNome()));
        TableColumn<Turma, String> salaColumn = new TableColumn<>("Sala");
        salaColumn.setPrefWidth(60);
        salaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSala().getLocal()));
        TableColumn<Turma, String> horarioColumn = new TableColumn<>("Horário");
        horarioColumn.setPrefWidth(60);
        horarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHorario()));
        TableColumn<Turma, String> tipoAvaliacaoColumn = new TableColumn<>("Tipo de Avaliação");
        tipoAvaliacaoColumn.setPrefWidth(120);
        tipoAvaliacaoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAvaliacaoMediaAritmetica()?"Média aritmética":"Média ponderada"));
        TableColumn<Turma, String> semestreColumn = new TableColumn<>("Semestre/Ano");
        semestreColumn.setPrefWidth(100);
        semestreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSemestreAno()));
        TableColumn<Turma, String> presencialColumn = new TableColumn<>("Presencial/Online");
        presencialColumn.setPrefWidth(140);
        presencialColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPresencial()?"Presencial":"Online"));
        TableColumn<Turma, Integer> qtdAlunoColumn = new TableColumn<>("Vagas Iniciais");
        qtdAlunoColumn.setPrefWidth(100);
        qtdAlunoColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQtdMaxAlunos()).asObject());
        tabela.getColumns().addAll( codigoColumn,disciplinaColumn,professorColumn,salaColumn,horarioColumn,tipoAvaliacaoColumn,semestreColumn,presencialColumn,qtdAlunoColumn);
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
        List<Turma> listaTurmas = new ArrayList<>();
        for (ClasseBase obj:servicoTurma.getLista()){
            listaTurmas.add((Turma) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaTurmas);
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
    }

    public void onTxtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(turma -> {
            return turma.getCodigo().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || turma.getDisciplina().getTitulo().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || turma.getProfessor().getNome().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }

    public void onBtnAlteraClick(ActionEvent actionEvent) {
    }

    public void onBtnExcluirClick(ActionEvent actionEvent) {
        Turma turma = (Turma) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (turma != null){

            Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Turma", "Excluir?","Deseja excluir a Turma "+turma.getCodigo() + "?");
            Optional<ButtonType> btnAlert = alert.showAndWait();
            btnAlert.ifPresent(btn->{
                if (btn.getText().equals("OK")){
                    try {
                        servicoTurma.excluir(turma);
                        servicoTurma.salvarArquivo();
                        carregaListasTableView();
                        tabela.setItems(this.listaFiltrada);
                        Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Turma","Exclusão com Sucesso","Turma "+ turma.getCodigo() + " excluído com sucesso").showAndWait();
                    } catch (Exception e) {
                        Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Turma","Erro ao excluir",e.getMessage()).showAndWait();
                    }
                }
            });
        }
        else {
            Util.getAlert(Alert.AlertType.WARNING,"Exclusão de Turma","Impossível Excluir","Selecione uma turma para excluir").showAndWait();
        }
    }


    public void onBtnNovoClick(ActionEvent actionEvent) {
    }
}
