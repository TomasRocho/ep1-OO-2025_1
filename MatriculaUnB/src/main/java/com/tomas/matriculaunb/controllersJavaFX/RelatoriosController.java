package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.Disciplina;
import com.tomas.matriculaunb.modelo.Professor;
import com.tomas.matriculaunb.modelo.Turma;
import com.tomas.matriculaunb.servicos.ServicoAlunoMatriculado;
import com.tomas.matriculaunb.util.Util;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Optional;

public class RelatoriosController {

    ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();

    public void initialize(){
    }

    public void btnAvaliacaoTurmaClick(ActionEvent actionEvent) {
        TextInputDialog td = new TextInputDialog(Util.getSemestreAtual());
        td.setHeaderText("Informe o semestre");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            if (td.getEditor().getText().isEmpty() || !Util.semestreValido(td.getEditor().getText())){
                Util.getAlert(Alert.AlertType.WARNING,"Emissão de Relatório","Impossível Emitir o Relatório","Selecione um semestre válido para emitir o relatório").showAndWait();
                return;
            }
            String semestreInformado = td.getEditor().getText();

            Turma turma=null;
            TurmaSelecaoController controllerSelecao=new TurmaSelecaoController();
            try {
                controllerSelecao.carregarModal();
                if (controllerSelecao.getTurmaSelecionado()!=null){
                    turma=controllerSelecao.getTurmaSelecionado();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (turma == null){
                Util.getAlert(Alert.AlertType.WARNING,"Emissão de Relatório","Impossível Emitir o Relatório","Selecione uma turma para emitir o relatório").showAndWait();
                return;
            }

            String strRelatorio = servicoAlunoMatriculado.gerarHtmlMatriculas(null,null,null,turma,"Relatório de Avaliação por Turma",true,semestreInformado);
            TelaBrowserController telaBrowserController = new TelaBrowserController();
            telaBrowserController.carregarModal(strRelatorio);
        }
    }

    public void btnAvaliacaoDisciplinaClick(ActionEvent actionEvent) {
        TextInputDialog td = new TextInputDialog(Util.getSemestreAtual());
        td.setHeaderText("Informe o semestre");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            if (td.getEditor().getText().isEmpty() || !Util.semestreValido(td.getEditor().getText())){
                Util.getAlert(Alert.AlertType.WARNING,"Emissão de Relatório","Impossível Emitir o Relatório","Selecione um semestre válido para emitir o relatório").showAndWait();
                return;
            }
            String semestreInformado = td.getEditor().getText();

            Disciplina disciplina=null;
            DisciplinaSelecaoController controllerSelecao=new DisciplinaSelecaoController();
            try {
                controllerSelecao.carregarModal();
                if (controllerSelecao.getDisciplinaSelecionada()!=null){
                    disciplina=controllerSelecao.getDisciplinaSelecionada();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (disciplina == null){
                Util.getAlert(Alert.AlertType.WARNING,"Emissão de Relatório","Impossível Emitir o Relatório","Selecione uma disciplina para emitir o relatório").showAndWait();
                return;
            }

            String strRelatorio = servicoAlunoMatriculado.gerarHtmlMatriculas(null,disciplina,null,null,"Relatório de Avaliação por Disciplina",true,semestreInformado);
            TelaBrowserController telaBrowserController = new TelaBrowserController();
            telaBrowserController.carregarModal(strRelatorio);
        }
    }

    public void btnAvaliacaoProfessorClick(ActionEvent actionEvent) {
        TextInputDialog td = new TextInputDialog(Util.getSemestreAtual());
        td.setHeaderText("Informe o semestre");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            if (td.getEditor().getText().isEmpty() || !Util.semestreValido(td.getEditor().getText())){
                Util.getAlert(Alert.AlertType.WARNING,"Emissão de Relatório","Impossível Emitir o Relatório","Selecione um semestre válido para emitir o relatório").showAndWait();
                return;
            }
            String semestreInformado = td.getEditor().getText();

            Professor professor=null;
            ProfessorSelecaoController controllerSelecao=new ProfessorSelecaoController();
            try {
                controllerSelecao.carregarModal();
                if (controllerSelecao.getProfessorSelecionado()!=null){
                    professor=controllerSelecao.getProfessorSelecionado();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (professor == null){
                Util.getAlert(Alert.AlertType.WARNING,"Emissão de Relatório","Impossível Emitir o Relatório","Selecione um professor para emitir o relatório").showAndWait();
                return;
            }

            String strRelatorio = servicoAlunoMatriculado.gerarHtmlMatriculas(null,null,professor,null,"Relatório de Avaliação por Professor",true,semestreInformado);
            TelaBrowserController telaBrowserController = new TelaBrowserController();
            telaBrowserController.carregarModal(strRelatorio);
        }
    }

    public void btnBoletimCompletoClick(ActionEvent actionEvent) {
        Aluno aluno=null;
        AlunoSelecaoController controllerSelecao=new AlunoSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getAlunoSelecionado()!=null){
                aluno=controllerSelecao.getAlunoSelecionado();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Emissão de Boletim","Impossível Emitir o Boletim","Selecione um aluno para emitir o boletim").showAndWait();
            return;
        }
        String strRelatorio = servicoAlunoMatriculado.gerarHtmlMatriculas(aluno,null,null,null,"Boletim Completo",true,null);
        TelaBrowserController telaBrowserController = new TelaBrowserController();
        telaBrowserController.carregarModal(strRelatorio);
    }

    public void btnBoletimResumidoClick(ActionEvent actionEvent) {
        Aluno aluno=null;
        AlunoSelecaoController controllerSelecao=new AlunoSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getAlunoSelecionado()!=null){
                aluno=controllerSelecao.getAlunoSelecionado();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Emissão de Boletim","Impossível Emitir o Boletim","Selecione um aluno para emitir o boletim").showAndWait();
            return;
        }
        String strRelatorio = servicoAlunoMatriculado.gerarHtmlMatriculas(aluno,null,null,null,"Boletim Resumido",false,null);
        TelaBrowserController telaBrowserController = new TelaBrowserController();
        telaBrowserController.carregarModal(strRelatorio);
    }
}
