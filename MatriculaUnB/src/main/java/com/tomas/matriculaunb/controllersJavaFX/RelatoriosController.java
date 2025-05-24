package com.tomas.matriculaunb.controllersJavaFX;

import javafx.event.ActionEvent;
import javafx.scene.control.TextInputDialog;

public class RelatoriosController {

    public void initialize(){
    }

    public void btnAvaliacaoTurmaClick(ActionEvent actionEvent) {
        TextInputDialog td = new TextInputDialog("1/2025");
        td.setHeaderText("Informe o semestre");
        td.showAndWait();
        System.out.println(td.getEditor().getText());
    }

    public void btnAvaliacaoDisciplinaClick(ActionEvent actionEvent) {
    }

    public void btnAvaliacaoProfessorClick(ActionEvent actionEvent) {
    }

    public void btnBoletimCompletoClick(ActionEvent actionEvent) {
    }

    public void btnBoletimResumidoClick(ActionEvent actionEvent) {
    }
}
