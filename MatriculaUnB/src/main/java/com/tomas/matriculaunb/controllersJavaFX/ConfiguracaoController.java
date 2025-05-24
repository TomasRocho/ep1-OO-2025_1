package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.util.DadosTeste;
import com.tomas.matriculaunb.util.Util;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfiguracaoController {

    public void btnGerarDados(ActionEvent actionEvent) {

        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Geração de dados para teste", "Dados teste","Deseja criar dados para teste?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().equals("OK")){
                try {
                    DadosTeste dadosTeste = new DadosTeste();
                    dadosTeste.geraTudo();
                    Util.getAlert(Alert.AlertType.INFORMATION,"Geração de dados teste","Dados teste","Dados gerados com sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Geração de dados teste","Erro ao gerar dados teste",e.getMessage()).showAndWait();
                }
            }
        });
    }
    public void btnApagarDados(ActionEvent actionEvent) {
        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Apagar dados", "Apagar dados","Deseja apagar todos os dados?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().equals("OK")){
                try {
                    DadosTeste dadosTeste = new DadosTeste();
                    dadosTeste.apagaTudo();
                    Util.getAlert(Alert.AlertType.INFORMATION,"Apagar dados","Apagar dados","Dados apagados com sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Apagar dados","Erro ao apagar dados",e.getMessage()).showAndWait();
                }
            }
        });

    }

    public void initialize(){
    }


}
