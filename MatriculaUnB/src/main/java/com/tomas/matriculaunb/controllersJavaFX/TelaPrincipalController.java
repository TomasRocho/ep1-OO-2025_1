package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class TelaPrincipalController {

    public AnchorPane telaCarregada;
    public Label lblNomeTela;
    public ImageView imageView;

    public void initialize(){
        File file = new File("src/Images/7309681.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
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

    public void onBtnAvaliacaoPresencaClick(ActionEvent actionEvent) {
    }
}
