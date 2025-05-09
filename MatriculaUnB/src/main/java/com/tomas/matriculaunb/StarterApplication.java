package com.tomas.matriculaunb;

import com.tomas.matriculaunb.modelo.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class StarterApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Curso curso = new Curso("Engenharia");
        curso.setTitulo("engenharia");
        System.out.println(curso.toString());
        Curso curso2 = new Curso("Ciencias da computaçao");
        System.out.println(curso2.toString());
        Curso curso3 = new Curso(UUID.randomUUID(),"Direito");
        System.out.println(curso3.toString());
        Curso curso4 = new Curso(UUID.randomUUID(),100);
        System.out.println(curso4.toString());
        if (curso2.equals(curso3)){
            System.out.println("são iguais");
        }
        Aluno aluno = new Aluno("Tomás","242024988",curso2,false);
        aluno.exibirDados();
        Professor professor = new Professor("Luciano");
        professor.exibirDados();
        Disciplina disciplina = new Disciplina("calculo 1","2599",60);
        disciplina.exibirDados();
        Turma turma =  new Turma(disciplina,professor,"S10","243524","1/2025");
        turma.exibirDados();
        AlunoMatriculado alunoMatriculado = new AlunoMatriculado(turma,aluno);
        alunoMatriculado.exibirDados();

        Disciplina disciplina2 = new Disciplina("calculo 2","2511",60);
        Disciplina disciplina3 = new Disciplina("calculo 3","2522",60);

        disciplina3.incluirPreRequisito(disciplina);
        disciplina3.incluirPreRequisito(disciplina2);
        disciplina3.exibirPreRequisitos();

        disciplina3.excluirPreRequisito(disciplina);
        disciplina3.exibirPreRequisitos();

        alunoMatriculado.setNotaP1(5.5F);
        alunoMatriculado.setNotaP2(4.5F);
        alunoMatriculado.setNotaP3(9.2F);
        alunoMatriculado.setNotaL(6.83F);
        alunoMatriculado.setNotaS(5.7F);


        alunoMatriculado.getTurma().setAtiva(false);
        //alunoMatriculado.getAluno().setEspecial(true);
        alunoMatriculado.getTurma().setAvaliacaoMediaAritmetica(false);
        System.out.println("A media final de " + alunoMatriculado.getAluno().getNome()+ " em "
                + alunoMatriculado.getTurma().getDisciplina().getTitulo()+ " é");
        System.out.println(alunoMatriculado.calcularMediaFinal());
        alunoMatriculado.setFaltas(5);
        System.out.println(alunoMatriculado.calcularPercentualFaltas());
        System.out.println(alunoMatriculado.getStatus());
        System.out.println(alunoMatriculado.exibirResultado());

        launch();
    }
}