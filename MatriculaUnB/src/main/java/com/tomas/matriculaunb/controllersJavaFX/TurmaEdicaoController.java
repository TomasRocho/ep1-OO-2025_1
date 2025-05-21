package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.Disciplina;
import com.tomas.matriculaunb.modelo.Professor;
import com.tomas.matriculaunb.modelo.Sala;
import com.tomas.matriculaunb.modelo.Turma;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class TurmaEdicaoController {

    private Turma turma;
    public TextField txtCodigo;
    public TextField txtDisciplina;
    public TextField txtProfessor;
    public TextField txtSala;
    public TextField txtHorario;
    public TextField txtSemestre;
    public TextField txtVagas;
    public CheckBox chkPresencial;
    public CheckBox chkAvaliacaoAritmetica;
    private Disciplina disciplinaSelecianada;
    private Sala salaSelecionada;
    private Professor professorSelecianado;

    public Disciplina getDisciplinaSelecianada() {
        return disciplinaSelecianada;
    }

    public void setDisciplinaSelecianada(Disciplina disciplinaSelecianada) {
        this.disciplinaSelecianada = disciplinaSelecianada;
    }

    public Sala getSalaSelecionada() {
        return salaSelecionada;
    }

    public void setSalaSelecionada(Sala salaSelecionada) {
        this.salaSelecionada = salaSelecionada;
    }

    public Professor getProfessorSelecianado() {
        return professorSelecianado;
    }

    public void setProfessorSelecianado(Professor professorSelecianado) {
        this.professorSelecianado = professorSelecianado;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public void carregarModal(){

        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("turmaEdicao.fxml"));
        fxmlLoader.setController(this);
        Dialog<ButtonType> dialog = new Dialog<>();
        try {
            dialog.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setTitle("Edição de Turma");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")) {
                System.out.println("ok...");
                if(txtVagas.getText().equals("") || txtVagas.getText().isBlank()){
                    txtVagas.setText("0");
                }
                if (this.getTurma()==null){
                    //inclusao
                    try {

                        this.setTurma(new Turma(txtCodigo.getText(),
                                                this.getDisciplinaSelecianada(),
                                                this.getProfessorSelecianado(),
                                                this.getSalaSelecionada(),
                                                txtHorario.getText(),
                                                txtSemestre.getText(),
                                                Integer.parseInt(txtVagas.getText()),
                                                chkPresencial.isSelected(),
                                                chkAvaliacaoAritmetica.isSelected()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    //alteração
                    this.getTurma().setCodigo(txtCodigo.getText());
                    this.getTurma().setDisciplina(this.getDisciplinaSelecianada());
                    this.getTurma().setProfessor(this.getProfessorSelecianado());
                    this.getTurma().setSala(this.getSalaSelecionada());
                    this.getTurma().setHorario(txtHorario.getText());
                    this.getTurma().setSemestreAno( txtSemestre.getText());
                    this.getTurma().setQtdMaxAlunos(Integer.parseInt(txtVagas.getText()));
                    this.getTurma().setPresencial(chkPresencial.isSelected());
                    this.getTurma().setAvaliacaoMediaAritmetica(chkAvaliacaoAritmetica.isSelected());
                }


                dialog.close();
            }
            if (buttonType.getText().equals("Cancel")) {
                this.setTurma(null);
                System.out.println("fechei...");

            }
        });
    }

    public void initialize() {

        if (this.getTurma()!=null){
            txtCodigo.setText(this.getTurma().getCodigo());
            txtDisciplina.setText(this.getTurma().getDisciplina().getTitulo());
            txtProfessor.setText(this.getTurma().getProfessor().getNome());
            txtSala.setText(this.getTurma().getSala().getLocal() + " - Campus: " + this.getTurma().getSala().getCampus());
            txtHorario.setText(this.getTurma().getHorario());
            chkPresencial.setSelected(this.getTurma().isPresencial());
            chkAvaliacaoAritmetica.setSelected(this.getTurma().isAvaliacaoMediaAritmetica());
            txtSemestre.setText(this.getTurma().getSemestreAno());
            txtVagas.setText(String.valueOf( this.getTurma().getQtdMaxAlunos()));

            this.setDisciplinaSelecianada(this.getTurma().getDisciplina());
            this.setSalaSelecionada(this.getTurma().getSala());
            this.setProfessorSelecianado(this.getTurma().getProfessor());

        }

    }


    public void onSelecaoDisciplina(ActionEvent actionEvent) {
        DisciplinaSelecaoController controllerSelecao=new DisciplinaSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getDisciplinaSelecionada()!=null){
                this.setDisciplinaSelecianada(controllerSelecao.getDisciplinaSelecionada());
                txtDisciplina.setText(this.getDisciplinaSelecianada().getTitulo());
            }
            else{
                txtDisciplina.setText("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSelecaoProfessor(ActionEvent actionEvent) {
        ProfessorSelecaoController controllerSelecao=new ProfessorSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getProfessorSelecionado()!=null){
                this.setProfessorSelecianado(controllerSelecao.getProfessorSelecionado());
                txtProfessor.setText(this.getProfessorSelecianado().getNome());
            }
            else{
                txtProfessor.setText("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSelecaoSala(ActionEvent actionEvent) {
        SalaSelecaoController controllerSelecao=new SalaSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getSalaSelecionada()!=null){
                this.setSalaSelecionada(controllerSelecao.getSalaSelecionada());
                txtSala.setText(this.getSalaSelecionada().getLocal() + " - Campus: " + this.getSalaSelecionada().getCampus());
            }
            else{
                txtSala.setText("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}