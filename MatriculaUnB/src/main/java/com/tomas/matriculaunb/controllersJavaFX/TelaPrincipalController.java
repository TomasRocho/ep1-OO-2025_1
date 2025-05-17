package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class TelaPrincipalController {

    public AnchorPane telaCarregada;
    public Label lblNomeTela;

    public void onBtnAlunoClick(ActionEvent actionEvent) {
    }

    public void onBtnCursoClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Cursos");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("cursoLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnDisciplinaClick(ActionEvent actionEvent) {
    }

    public void onBtnProfessorClick(ActionEvent actionEvent) throws IOException {
        lblNomeTela.setText("Cadastro de Professores");
        AnchorPane anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("professorLista.fxml"));
        anchorPane=fxmlLoader.load();
        telaCarregada.getChildren().setAll(anchorPane);
    }

    public void onBtnSalaClick(ActionEvent actionEvent) {
    }

    public void onBtnTurmaClick(ActionEvent actionEvent) {
    }

    public void onBtnAvaliacaoClick(ActionEvent actionEvent) {
    }

    public void onBtnPresencaClick(ActionEvent actionEvent) {
    }

    public void onBtnRelatoriosClick(ActionEvent actionEvent) {
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
}
