package com.tomas.matriculaunb.controllersJavaFX;
import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.AlunoMatriculado;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class NotaEdicaoController {

    private AlunoMatriculado alunoMatriculado;
    public TextField txtNota1;
    public TextField txtNota2;
    public TextField txtNota3;
    public TextField txtNotaLista;
    public TextField txtNotaSeminario;

    public AlunoMatriculado getAlunoMatriculado() {
        return alunoMatriculado;
    }

    public void setAlunoMatriculado(AlunoMatriculado alunoMatriculado) {
        this.alunoMatriculado = alunoMatriculado;
    }

    public void carregarModal(){

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("notasEdicao.fxml"));
        fxmlLoader.setController(this);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setTitle("Edição de Nota");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")) {
                System.out.println("ok...");
                //alteração
                this.getAlunoMatriculado().setNotaP1(Float.valueOf(txtNota1.getText()));
                this.getAlunoMatriculado().setNotaP2(Float.valueOf(txtNota2.getText()));
                this.getAlunoMatriculado().setNotaP3(Float.valueOf(txtNota3.getText()));
                this.getAlunoMatriculado().setNotaL(Float.valueOf(txtNotaLista.getText()));
                this.getAlunoMatriculado().setNotaS(Float.valueOf(txtNotaSeminario.getText()));

                dialog.close();
            }
            if (buttonType.getText().equals("Cancel")) {
                this.setAlunoMatriculado(null);
                System.out.println("fechei...");

            }
        });
    }

    public void initialize() {
        if (this.getAlunoMatriculado()!=null){
            this.txtNota1.setText(this.getAlunoMatriculado().getNotaP1().toString());
            this.txtNota2.setText(this.getAlunoMatriculado().getNotaP2().toString());
            this.txtNota3.setText(this.getAlunoMatriculado().getNotaP3().toString());
            this.txtNotaLista.setText(this.getAlunoMatriculado().getNotaL().toString());
            this.txtNotaSeminario.setText(this.getAlunoMatriculado().getNotaS().toString());
        }

    }


}