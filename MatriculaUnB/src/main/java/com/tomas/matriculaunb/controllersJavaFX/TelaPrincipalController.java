package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

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
        }
        if (modoAvaliacao){
            btnAvaliacaoPresenca.setDisable(false);
            btnRelatorios.setDisable(false);
        }
    }


    public void onBtnAlunoClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Alunos");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("alunoLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnCursoClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Cursos");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("cursoLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnDisciplinaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Disciplinas");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("disciplinaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnProfessorClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Professores");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("professorLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnSalaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Salas");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("salaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnTurmaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Turmas");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("turmaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }


    public void onBtnRelatoriosClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Relatórios");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("relatorios.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnSairClick(ActionEvent actionEvent) {
        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Sair do Sistema", "Encerrar?","Deseja fechar o sistema?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().equals("OK")) {
                Platform.exit();
            }
        });
    }

    public void onBtnAvaliacaoPresencaClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Avaliação/Frequência");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("avaliacaoFrequenciaLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnConfiguracaoClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Configuração");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("configuracao.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnSobreClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Sobre...");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("sobre.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }
}
