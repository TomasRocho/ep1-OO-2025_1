package com.tomas.matriculaunb.controllersJavaFX;
import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.Sala;
import com.tomas.matriculaunb.modelo.enumerations.EnumCampus;
import com.tomas.matriculaunb.servicos.ServicoSala;
import com.tomas.matriculaunb.util.Util;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class SalaEdicaoController {

    private Sala sala;
    public ComboBox cboCampus;
    public TextField txtLocal;
    private ServicoSala servicoSala = ServicoSala.getInstance();

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void carregarModal(){

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("salaEdicao.fxml"));
        fxmlLoader.setController(this);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setTitle("Edição de Sala");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")) {
                System.out.println("ok...");
                if (cboCampus.getValue()==null){
                    Util.getAlert(Alert.AlertType.ERROR,"Sala Inválida","Salvamento de Sala", "Selecione um Campus").showAndWait();
                    return;
                }
                if (this.getSala()==null){
                    //inclusao
                    this.setSala(new Sala(
                            txtLocal.getText(),
                            (EnumCampus.valueOf(cboCampus.getValue().toString()))
                    ));
                }
                else{
                    //alteração
                    this.getSala().setLocal(txtLocal.getText());
                    this.getSala().setCampus((EnumCampus.valueOf(cboCampus.getValue().toString())));
                }


                dialog.close();
            }
            if (buttonType.getText().equals("Cancel")) {
                this.setSala(null);
                System.out.println("fechei...");

            }
        });
    }

    public void initialize() {

        for (EnumCampus campus : EnumCampus.values()) {
            cboCampus.getItems().add(campus.toString());
        }
        if (this.getSala()!=null){
            this.txtLocal.setText(this.getSala().getLocal());
            this.cboCampus.setValue(this.getSala().getCampus().toString());
        }

    }


}