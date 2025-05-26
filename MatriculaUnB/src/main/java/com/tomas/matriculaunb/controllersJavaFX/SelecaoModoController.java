package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.util.Util;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class SelecaoModoController {


    public Button btnAluno;
    public Button btnDisciplina;
    public Button btnAvaliacao;
    private boolean modoAluno;
    private boolean modoDisciplina;
    private boolean modoAvaliacao;

    public boolean isModoDisciplina() {
        return modoDisciplina;
    }

    public void setModoDisciplina(boolean modoDisciplina) {
        this.modoDisciplina = modoDisciplina;
    }

    public boolean isModoAvaliacao() {
        return modoAvaliacao;
    }

    public void setModoAvaliacao(boolean modoAvaliacao) {
        this.modoAvaliacao = modoAvaliacao;
    }

    public boolean isModoAluno() {
        return modoAluno;
    }

    public void setModoAluno(boolean modoAluno) {
        this.modoAluno = modoAluno;
    }

    public void btnSairClick(ActionEvent actionEvent) {
        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Sair do Sistema", "Encerrar?","Deseja fechar o sistema?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().trim().equalsIgnoreCase("OK")) {

                System.exit(0);
            }
        });
    }

    public void btnAlunoClick(ActionEvent actionEvent) {
        this.modoAluno=true;
        this.modoAvaliacao=false;
        this.modoDisciplina=false;
        ((Stage) btnAluno.getScene().getWindow()).close();
    }
    public void btnDisciplinaClick(ActionEvent actionEvent) {
        this.modoAluno=false;
        this.modoAvaliacao=false;
        this.modoDisciplina=true;
        ((Stage) btnDisciplina.getScene().getWindow()).close();
    }
    public void btnAvaliacaoClick(ActionEvent actionEvent) {
        this.modoAluno=false;
        this.modoAvaliacao=true;
        this.modoDisciplina=false;
        ((Stage) btnAvaliacao.getScene().getWindow()).close();
    }
}
