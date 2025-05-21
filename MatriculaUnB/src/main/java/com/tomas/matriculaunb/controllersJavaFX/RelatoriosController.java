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
}
