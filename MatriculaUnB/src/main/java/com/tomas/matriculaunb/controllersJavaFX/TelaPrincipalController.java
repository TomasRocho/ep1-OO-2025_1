package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TelaPrincipalController {

    public AnchorPane telaCarregada;
    public Label lblNomeTela;
    public Label lblNomeModo;
    public Button btnAluno;
    public Button btnCurso;
    public Button btnDisciplina;
    public Button btnProfessor;
    public Button btnSala;
    public Button btnTurma;
    public Button btnAvaliacaoPresenca;
    public Button btnRelatorios;
    public Button btnConfiguracao;

    public void configuraTela(boolean modoAluno, boolean modoDisciplina, boolean modoAvaliacao){
        btnAluno.setDisable(true);
        btnCurso.setDisable(true);
        btnDisciplina.setDisable(true);
        btnProfessor.setDisable(true);
        btnSala.setDisable(true);
        btnTurma.setDisable(true);
        btnAvaliacaoPresenca.setDisable(true);
        btnRelatorios.setDisable(true);
        btnConfiguracao.setDisable(true);
        if (modoAluno){
            btnAluno.setDisable(false);
            lblNomeModo.setText("Modo ALUNO");
        }
        if (modoDisciplina){
            btnCurso.setDisable(false);
            btnDisciplina.setDisable(false);
            btnProfessor.setDisable(false);
            btnSala.setDisable(false);
            btnTurma.setDisable(false);
            btnConfiguracao.setDisable(false);
            lblNomeModo.setText("Modo DISCIPLINA/TURMA");
        }
        if (modoAvaliacao){
            btnAvaliacaoPresenca.setDisable(false);
            btnRelatorios.setDisable(false);
            lblNomeModo.setText("Modo AVALIAÇÃO/FREQUÊNCIA");
        }
    }


    public void btnAlunoClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Alunos");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("alunoLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnCursoClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Cursos");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("cursoLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnDisciplinaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Disciplinas");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("disciplinaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnProfessorClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Professores");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("professorLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnSalaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Salas");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("salaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnTurmaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Turmas");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("turmaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }


    public void btnRelatoriosClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Relatórios");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("relatorios.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnSairClick(ActionEvent actionEvent) {
        Stage stageAtual = (Stage) btnAluno.getScene().getWindow();
        Stage selecaoModo = new Stage();
        selecaoModo.initOwner(stageAtual);
        selecaoModo.initModality(Modality.APPLICATION_MODAL);
        selecaoModo.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader2 = new FXMLLoader(StarterApplication.class.getResource("selecaoModo.fxml"));
        SelecaoModoController selecaoModoController = new SelecaoModoController();
        fxmlLoader2.setController(selecaoModoController);
        Scene scene1 = null;
        try {
            scene1 = new Scene(fxmlLoader2.load(),500,400);
            selecaoModo.setScene(scene1);
            selecaoModo.setTitle("SELEÇÃO DE MODO");
            selecaoModo.showAndWait();
            configuraTela(selecaoModoController.isModoAluno(), selecaoModoController.isModoDisciplina(), selecaoModoController.isModoAvaliacao());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public void btnAvaliacaoPresencaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Avaliação/Frequência");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("avaliacaoFrequenciaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnConfiguracaoClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Configuração");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("configuracao.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void btnSobreClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Sobre...");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("sobre.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }
}
