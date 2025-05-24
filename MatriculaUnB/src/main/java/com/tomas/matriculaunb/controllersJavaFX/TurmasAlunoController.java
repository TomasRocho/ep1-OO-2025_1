package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.servicos.ServicoAlunoMatriculado;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class TurmasAlunoController {
    private Aluno alunoSelecionado;
    private String titulo;
    private boolean turmasAtuais;
    private boolean turmaConcluidas;
    private boolean trancamento;
    public TableView tabela;
    public Button btnTrancar;
    private ObservableList<ClasseBase> listaTabela;
    private Dialog<ButtonType> dialog;


    public boolean isTrancamento() {
        return trancamento;
    }

    public void setTrancamento(boolean trancamento) {
        this.trancamento = trancamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isTurmasAtuais() {
        return turmasAtuais;
    }

    public void setTurmasAtuais(boolean turmasAtuais) {
        this.turmasAtuais = turmasAtuais;
    }

    public boolean isTurmaConcluidas() {
        return turmaConcluidas;
    }

    public void setTurmaConcluidas(boolean turmaConcluidas) {
        this.turmaConcluidas = turmaConcluidas;
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
    }

    public void initialize(){

        btnTrancar.setVisible(false);
        if (this.isTrancamento()){
            btnTrancar.setVisible(true);
        }

        ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();
        List<ClasseBase> listaTurma = servicoAlunoMatriculado.getAlunosMatriculadosPorAluno(this.getAlunoSelecionado(),this.isTurmasAtuais(),this.isTurmaConcluidas());
        this.listaTabela = FXCollections.observableArrayList(listaTurma);
        this.listaTabela.sort(Comparator.comparing(alunoMatriculado -> ((AlunoMatriculado) alunoMatriculado).getTurma().getDisciplina().getTitulo()));
        TableColumn<AlunoMatriculado, String> codigoColumn = new TableColumn<>("Código");
        codigoColumn.setPrefWidth(60);
        codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTurma().getCodigo()));
        TableColumn<AlunoMatriculado, String> disciplinaColumn = new TableColumn<>("Disciplina");
        disciplinaColumn.setPrefWidth(150);
        disciplinaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTurma().getDisciplina().getTitulo()));
        TableColumn<AlunoMatriculado, String> professorColumn = new TableColumn<>("Professor");
        professorColumn.setPrefWidth(150);
        professorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTurma().getProfessor().getNome()));
        TableColumn<AlunoMatriculado, String> salaColumn = new TableColumn<>("Sala");
        salaColumn.setPrefWidth(60);
        salaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTurma().getSala().getLocal()));
        TableColumn<AlunoMatriculado, String> horarioColumn = new TableColumn<>("Horário");
        horarioColumn.setPrefWidth(60);
        horarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTurma().getHorario()));
        TableColumn<AlunoMatriculado, String> semestreColumn = new TableColumn<>("Semestre/Ano");
        semestreColumn.setPrefWidth(100);
        semestreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTurma().getSemestreAno()));
        TableColumn<AlunoMatriculado, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setPrefWidth(140);
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().retornarStatus().toString()));
        tabela.getColumns().addAll( codigoColumn,disciplinaColumn,professorColumn,salaColumn,horarioColumn,semestreColumn,statusColumn);


        tabela.setItems(listaTabela);


    }

    public void btnTrancarClick(){
        AlunoMatriculado alunoMatriculado = (AlunoMatriculado) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (alunoMatriculado == null){
            Util.getAlert(Alert.AlertType.WARNING,"Trancamento de Disciplina","Impossível Trancar a Disciplina","Selecione uma turma para trancamento").showAndWait();
            return;
        }
        alunoMatriculado.setTrancado(true);
        ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();
        try {
            servicoAlunoMatriculado.alterar(alunoMatriculado);
            servicoAlunoMatriculado.salvarArquivo();
            Util.getAlert(Alert.AlertType.INFORMATION,"Trancamento de Disciplina","Disciplina Trancada","Disciplina Trancada com Sucesso").showAndWait();
            dialog.close();
        } catch (Exception e) {
            Util.getAlert(Alert.AlertType.ERROR,"Trancamento de Disciplina","Erro ao Trancar a Disciplina",e.getMessage()).showAndWait();
        }

    }

    public void carregarModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("turmasAluno.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        dialog.setTitle(this.getTitulo());
        dialog.showAndWait();

    }

}
