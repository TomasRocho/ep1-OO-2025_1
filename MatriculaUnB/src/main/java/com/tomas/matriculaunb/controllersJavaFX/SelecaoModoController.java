package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.util.Util;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SelecaoModoController {
    public void sair(ActionEvent actionEvent) {
        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Sair do Sistema", "Encerrar?","Deseja fechar o sistema?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().equals("OK")) {

                System.exit(0);
            }
        });
    }
}
