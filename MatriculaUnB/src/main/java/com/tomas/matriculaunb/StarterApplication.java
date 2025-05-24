package com.tomas.matriculaunb;

import com.tomas.matriculaunb.servicos.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StarterApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("telaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Sistema UnB");
        stage.setScene(scene);
        stage.show();

        /*

        Stage selecaoModo = new Stage();
        selecaoModo.initOwner(stage);
        selecaoModo.initModality(Modality.APPLICATION_MODAL);
        selecaoModo.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader2 = new FXMLLoader(StarterApplication.class.getResource("selecaoModo.fxml"));
        Scene scene1 = new Scene(fxmlLoader2.load(),500,400);
        selecaoModo.setScene(scene1);
        selecaoModo.setTitle("SELEÇÃO DE MODO");
        selecaoModo.showAndWait();

         */


    }

    public static void main(String[] args) {

        ServicoCurso servicoCurso=ServicoCurso.getInstance();
        try {
            servicoCurso.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de cursos");
        }
        ServicoAluno servicoAluno=ServicoAluno.getInstance();
        try {
            servicoAluno.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de aluno");
        }
        ServicoAlunoMatriculado servicoAlunoMatriculado=ServicoAlunoMatriculado.getInstance();
        try {
            servicoAlunoMatriculado.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de aluno matriculado");
        }

        ServicoDisciplina servicoDisciplina=ServicoDisciplina.getInstance();
        try {
            servicoDisciplina.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de disciplinas");
        }

        ServicoPreRequisito servicoPreRequisito=ServicoPreRequisito.getInstance();
        try {
            servicoPreRequisito.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de pre requisitos");
        }

        ServicoProfessor servicoProfessor=ServicoProfessor.getInstance();
        try {
            servicoProfessor.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de professor");
        }

        ServicoTurma servicoTurma=ServicoTurma.getInstance();
        try {
            servicoTurma.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de turma");
        }

        ServicoSala servicoSala=ServicoSala.getInstance();
        try {
            servicoSala.carregarArquivo();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de sala");
        }

        launch();
    }
}