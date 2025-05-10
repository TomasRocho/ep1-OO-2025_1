package com.tomas.matriculaunb;

import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.servicos.*;
import com.tomas.matriculaunb.util.DadosTeste;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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

        /*
        Curso curso = new Curso("Engenharia");
        curso.setTitulo("engenharia");
        System.out.println(curso.toString());
        Curso curso2 = new Curso("Ciencias da computaçao");
        System.out.println(curso2.toString());
        Curso curso3 = new Curso(UUID.randomUUID(),"Direito");
        System.out.println(curso3.toString());

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
        alunoMatriculado.getAluno().setEspecial(true);
        alunoMatriculado.getTurma().setAvaliacaoMediaAritmetica(false);
        System.out.println("A media final de " + alunoMatriculado.getAluno().getNome()+ " em "
                + alunoMatriculado.getTurma().getDisciplina().getTitulo()+ " é");
        System.out.println(alunoMatriculado.calcularMediaFinal());
        alunoMatriculado.setFaltas(5);
        System.out.println(alunoMatriculado.calcularPercentualFaltas());
        System.out.println(alunoMatriculado.getStatus());
        System.out.println(alunoMatriculado.exibirResultado());

        System.out.println("emitindo boletim:");
        aluno.setListaMatriculas(new ArrayList<>());
        aluno.getListaMatriculas().add(alunoMatriculado);
        aluno.emitirBoletim();



        ServicoCurso servicoCurso = new ServicoCurso();

        try {
            servicoCurso.incluir(new Curso("arquitetura"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Curso direito = new Curso("Direito");
        Curso engenharia = new Curso("Engenharia");
        Curso medicina = new Curso("Medicina");
        try {
            servicoCurso.incluir(direito);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            servicoCurso.incluir(direito);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            servicoCurso.incluir(engenharia);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            servicoCurso.incluir(medicina);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("lista original: ");
        servicoCurso.exibirLista();

        Curso copiaCursoEngenharia = null;
        try {
            copiaCursoEngenharia = (Curso) servicoCurso.retornar(engenharia.getId());
        } catch (Exception e) {
            System.out.println("Erro ao retornar objeto - " + e.getMessage());
        }
        System.out.println("Curso retornado:");
        copiaCursoEngenharia.exibirDados();

        copiaCursoEngenharia.setTitulo("Nova Engenharia");
        System.out.println("Curso alterado:");
        try {
            servicoCurso.alterar(copiaCursoEngenharia);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        servicoCurso.exibirLista();

        try {
            servicoCurso.excluir(medicina);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Elemento excluido: ");
        servicoCurso.exibirLista();


        Disciplina disciplina = new Disciplina("Calculo 2", "7299123", 60);
        ServicoDisciplina servicoDisciplina = new ServicoDisciplina();
        try {
            servicoDisciplina.incluir(disciplina);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            servicoDisciplina.incluir(new Disciplina("TED", "852935123", 45));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        servicoDisciplina.exibirLista();

        Curso meuCurso = null;
        try {
            meuCurso = servicoCurso.retornarPorTitulo("arquitetura");
            meuCurso.exibirDados();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        meuCurso.setTitulo("nova arquitetura");
        try {
            servicoCurso.alterar(meuCurso);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        meuCurso.exibirDados();


        ServicoDisciplina servicoDisciplina1 = new ServicoDisciplina();
        try {
            servicoDisciplina1.incluir(new Disciplina("Calculo 2","5150",60));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            servicoDisciplina1.incluir(new Disciplina("IAL","5654",45));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        servicoDisciplina1.exibirLista();
        Disciplina disciplina1=null;
        try {
            disciplina1 = servicoDisciplina1.retornarPorCodigo("5150");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        disciplina1.exibirDados();
        disciplina1.setTitulo("IAL");
        try {
            servicoDisciplina1.alterar(disciplina1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            servicoCurso.incluir(new Curso(null,"odonto"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


         */

        DadosTeste dadosTeste=new DadosTeste();
        dadosTeste.geraTudo();

        ServicoPreRequisito servicoPreRequisito=ServicoPreRequisito.getInstance();
        ServicoDisciplina servicoDisciplina=ServicoDisciplina.getInstance();
        try {
            servicoPreRequisito.incluir(new PreRequisito(servicoDisciplina.getLista().get(0).getId(),servicoDisciplina.getLista().get(1).getId()));
            servicoPreRequisito.incluir(new PreRequisito(servicoDisciplina.getLista().get(0).getId(),servicoDisciplina.getLista().get(2).getId()));
            servicoPreRequisito.incluir(new PreRequisito(servicoDisciplina.getLista().get(3).getId(),servicoDisciplina.getLista().get(1).getId()));
            servicoPreRequisito.exibirLista();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        launch();
    }
}