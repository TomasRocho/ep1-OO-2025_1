package com.tomas.matriculaunb.controllersJavaFX;
import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Curso;
import com.tomas.matriculaunb.servicos.ServicoCurso;
import com.tomas.matriculaunb.util.Util;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Optional;

public class AlunoEdicaoController  {

    private Aluno aluno;
    public ComboBox cboCurso;
    public TextField txtMatricula;
    public TextField txtNome;
    public CheckBox chkEspecial;
    private ServicoCurso servicoCurso = ServicoCurso.getInstance();

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void carregarModal(){

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("alunoEdicao.fxml"));
        fxmlLoader.setController(this);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setTitle("Edição de Aluno");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().trim().equalsIgnoreCase("OK")) {
                System.out.println("ok...");
                if (cboCurso.getValue()==null){
                    Util.getAlert(Alert.AlertType.ERROR,"Aluno Inválido","Salvamento de Aluno", "Selecione um Curso").showAndWait();
                    return;
                }
                if (this.getAluno()==null){
                    //inclusao
                    try {
                        this.setAluno(new Aluno(txtNome.getText(),txtMatricula.getText(),servicoCurso.retornarPorTitulo(cboCurso.getValue().toString()),chkEspecial.isSelected()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    //alteração
                    this.getAluno().setMatricula(txtMatricula.getText());
                    this.getAluno().setNome((txtNome.getText()));
                    this.getAluno().setEspecial(chkEspecial.isSelected());
                    try {
                        this.getAluno().setCurso(servicoCurso.retornarPorTitulo(cboCurso.getValue().toString()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }


                dialog.close();
            }
            if (buttonType.getText().trim().equalsIgnoreCase("Cancel") || buttonType.getText().trim().equalsIgnoreCase("Cancelar")) {
                this.setAluno(null);
                System.out.println("fechei...");

            }
        });
    }

    public void initialize() {

        for(ClasseBase curso:servicoCurso.getLista()){
            cboCurso.getItems().add(((Curso)curso).getTitulo());
        }
        if (this.getAluno()!=null){
            this.txtMatricula.setText(this.getAluno().getMatricula());
            this.txtNome.setText(this.getAluno().getNome());
            this.cboCurso.setValue(this.getAluno().getCurso().getTitulo());
            this.chkEspecial.setSelected(this.getAluno().isEspecial());
        }

    }


}