package com.tomas.matriculaunb.controllersJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    public Label lblTeste;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onBtnTesteClick(ActionEvent actionEvent) {
        lblTeste.setText("La ele!");
    }
}