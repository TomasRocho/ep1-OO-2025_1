package com.tomas.matriculaunb.util;

import javafx.scene.control.Alert;

import java.time.LocalDate;

public class Util {
    public static String getSemestreAtual(){
        LocalDate dataAtual = LocalDate.now();
        int mes = dataAtual.getMonthValue();
        int ano = dataAtual.getYear();
        String semestreAno;
        if (mes <= 6){
            semestreAno = "1/"+ano;
        }
        else{
            semestreAno = "2/"+ano;
        }

        return semestreAno;
    }
    public static boolean semestreValido(String semestre){
        if (semestre.length() != 6){
            return false;
        }
        if (!semestre.startsWith("1/") && !semestre.startsWith("2/")){
            return false;
        }

        return true;
    }

    public static Alert getAlert(Alert.AlertType alertType, String titulo, String header, String texto){
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(texto);
        return alert;
    }
}
