package com.tomas.matriculaunb.controllersJavaFX;
import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.Disciplina;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class DisciplinaEdicaoController {

    private Disciplina disciplina;
    public TextField txtTitulo;
    public TextField txtCodigo;
    public TextField txtCargaHoraria;

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void carregarModal(){

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("disciplinaEdicao.fxml"));
        fxmlLoader.setController(this);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setTitle("Edição de Disciplina");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().trim().equalsIgnoreCase("OK")) {
                System.out.println("ok...");
                if(txtCargaHoraria.getText().isEmpty() || txtCargaHoraria.getText().isBlank()){
                    txtCargaHoraria.setText("0");
                }
                if (this.getDisciplina()==null){
                    //inclusao
                    try {

                        this.setDisciplina(new Disciplina(txtTitulo.getText(),txtCodigo.getText(),Integer.parseInt(txtCargaHoraria.getText())));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    //alteração
                    this.getDisciplina().setTitulo(txtTitulo.getText());
                    this.getDisciplina().setCodigo((txtCodigo.getText()));
                    this.getDisciplina().setCargaHoraria(Integer.parseInt(txtCargaHoraria.getText()));
                }


                dialog.close();
            }
            if (buttonType.getText().trim().equalsIgnoreCase("Cancel") || buttonType.getText().trim().equalsIgnoreCase("Cancelar")) {
                this.setDisciplina(null);
                System.out.println("fechei...");

            }
        });
    }

    public void initialize() {

        if (this.getDisciplina()!=null){
            this.txtTitulo.setText(this.getDisciplina().getTitulo());
            this.txtCodigo.setText(this.getDisciplina().getCodigo());
            this.txtCargaHoraria.setText(String.valueOf(this.getDisciplina().getCargaHoraria()));
        }

    }


}