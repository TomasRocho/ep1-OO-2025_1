package com.tomas.matriculaunb;

import com.tomas.matriculaunb.servicos.*;
import com.tomas.matriculaunb.util.DadosTeste;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StarterApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("telaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Sistema UnB");
        stage.setScene(scene);
        stage.show();
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

        DadosTeste dadosTeste=new DadosTeste();
        dadosTeste.geraTudo();
//
//        servicoCurso.exibirLista();
//        servicoProfessor.exibirLista();
//        servicoTurma.exibirLista();
//        servicoPreRequisito.exibirLista();
//        servicoAlunoMatriculado.exibirLista();
//        servicoAluno.exibirLista();
//        servicoDisciplina.exibirLista();
//        servicoSala.exibirLista();



        launch();
    }
}