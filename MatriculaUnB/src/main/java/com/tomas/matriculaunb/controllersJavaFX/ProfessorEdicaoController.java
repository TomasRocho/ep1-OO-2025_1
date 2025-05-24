package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.Professor;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class ProfessorEdicaoController {

    private Professor professor;
    public TextField txtNome;
    public TextField txtMatricula;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void carregarModal(){

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("professorEdicao.fxml"));
        fxmlLoader.setController(this);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setTitle("Edição de Professor");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")) {
                System.out.println("ok...");
                if (this.getProfessor()==null){
                    //inclusao
                    this.setProfessor(new Professor(txtMatricula.getText(),txtNome.getText()));
                }
                else{
                    //alteração
                    this.getProfessor().setNome(txtNome.getText());
                    this.getProfessor().setMatricula(txtMatricula.getText());
                }
                dialog.close();
            }
            if (buttonType.getText().equals("Cancel")) {
                this.setProfessor(null);
                System.out.println("fechei...");

            }
        });
    }

    public void initialize() {

        if (this.getProfessor()!=null){
            this.txtMatricula.setText(this.getProfessor().getMatricula());
            this.txtNome.setText(this.getProfessor().getNome());
        }

    }


}
