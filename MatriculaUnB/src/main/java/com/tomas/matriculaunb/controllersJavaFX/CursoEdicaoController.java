package com.tomas.matriculaunb.controllersJavaFX;
import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.servicos.ServicoCurso;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class CursoEdicaoController {

    private Curso curso;
    public TextField txtTitulo;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void carregarModal(){

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("cursoEdicao.fxml"));
        fxmlLoader.setController(this);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setTitle("Edição de Curso");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")) {
                System.out.println("ok...");
                if (this.getCurso()==null){
                    //inclusao
                    this.setCurso(new Curso(txtTitulo.getText()));
                }
                else{
                    //alteração
                    this.getCurso().setTitulo(txtTitulo.getText());
                }
                dialog.close();
            }
            if (buttonType.getText().equals("Cancel")) {
                this.setCurso(null);
                System.out.println("fechei...");

            }
        });
    }

    public void initialize() {

        if (this.getCurso()!=null){
            this.txtTitulo.setText(this.getCurso().getTitulo());
        }

    }


}
