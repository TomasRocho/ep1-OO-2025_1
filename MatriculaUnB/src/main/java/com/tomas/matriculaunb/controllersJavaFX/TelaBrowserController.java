package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.Optional;

public class TelaBrowserController {

    Dialog<ButtonType> dialog;
    private String strHtml;
    public WebView webView;

    public String getStrHtml() {
        return strHtml;
    }

    public void setStrHtml(String strHtml) {
        this.strHtml = strHtml;
    }



    public void carregarModal(String stringHTML){
        this.setStrHtml(stringHTML);
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("telaBrowser.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
            dialog.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().trim().equalsIgnoreCase("Apply")
                || buttonType.getText().trim().equalsIgnoreCase("Aplicar")) {
                System.out.println("imprimindo...");
                try {
                    PrinterJob job = PrinterJob.createPrinterJob();
                    job.getJobSettings().setJobName("Print WebEngine");
                    job.getJobSettings().setPageLayout(job.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT));
                    webView.getEngine().print(job);
                    job.endJob();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR, "Impressão de Relatório", "Impossível Imprimir o Relatório", "Nenhuma impressora selecionada").showAndWait();
                }
            }
        });
    }


    @FXML
    public void initialize() {
        webView.getEngine().loadContent(this.getStrHtml(),"text/html");
    }


}