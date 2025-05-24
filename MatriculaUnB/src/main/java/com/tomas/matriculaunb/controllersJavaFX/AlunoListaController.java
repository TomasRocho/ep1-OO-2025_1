package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.*;
import com.tomas.matriculaunb.servicos.ServicoAluno;
import com.tomas.matriculaunb.servicos.ServicoAlunoMatriculado;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AlunoListaController {

    private ServicoAluno servicoAluno = ServicoAluno.getInstance();
    private ServicoAlunoMatriculado servicoAlunoMatriculado = ServicoAlunoMatriculado.getInstance();
    private ObservableList<Aluno> listaTabela;
    private FilteredList<Aluno> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;
    private Aluno alunoEditado;
    public Button btnAltera;
    private Turma turmaSelecionada;

    public Aluno getAlunoEditado() {
        return alunoEditado;
    }

    public void setAlunoEditado(Aluno alunoEditado) {
        this.alunoEditado = alunoEditado;
    }

    public Turma getTurmaSelecionada() {
        return turmaSelecionada;
    }

    public void setTurmaSelecionada(Turma turmaSelecionada) {
        this.turmaSelecionada = turmaSelecionada;
    }

    public void initialize(){

        carregaListasTableView();

        TableColumn<Aluno, String> matriculaColumn = new TableColumn<>("Matrícula");
        matriculaColumn.setPrefWidth(100);
        matriculaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        TableColumn<Aluno, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setPrefWidth(300);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        TableColumn<Aluno, String> cursoColumn = new TableColumn<>("Curso");
        cursoColumn.setPrefWidth(200);
        cursoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurso().getTitulo()));
        TableColumn<Aluno, String> especialColumn = new TableColumn<>("Especial");
        especialColumn.setPrefWidth(70);
        especialColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isEspecial() ?"Sim":"Não"));
        tabela.getColumns().addAll( matriculaColumn,nomeColumn,cursoColumn,especialColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Aluno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    btnAltera.fire();
                }
            });
            return row ;
        });
    }

    public void carregaListasTableView(){
        List<Aluno> listaAlunos = new ArrayList<>();
        for (ClasseBase obj:servicoAluno.getLista()){
            listaAlunos.add((Aluno) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaAlunos);
        this.listaTabela.sort(Comparator.comparing(Aluno::getNome));
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
    }

    public void txtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(aluno -> {
            return aluno.getNome().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || aluno.getMatricula().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }

    public void btnAlteraClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteraçao/Exclusão de Registro","Impossível Alterar/Exclui","Selecione um aluno para alterar/excluir").showAndWait();
            return;
        }
        AlunoEdicaoController controllerEdicao=new AlunoEdicaoController();
        try {
            controllerEdicao.setAluno((Aluno) aluno.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        controllerEdicao.carregarModal();

        if (controllerEdicao.getAluno()!=null){
            this.setAlunoEditado(controllerEdicao.getAluno());
            try {
                servicoAluno.alterar(this.getAlunoEditado());
                servicoAluno.salvarArquivo(aluno);
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                tabela.refresh();
                Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Aluno","Aluno Salvo","Aluno Salvo com Sucesso").showAndWait();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Aluno","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }


    }

    public void btnExcluirClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteraçao/Exclusão de Registro","Impossível Alterar/Exclui","Selecione um aluno para alterar/excluir").showAndWait();
            return;
        }


        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Aluno", "Excluir?","Deseja excluir o aluno "+aluno.getNome() + "?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().equals("OK")){
                try {
                    servicoAluno.excluir(aluno);
                    servicoAluno.salvarArquivo();
                    carregaListasTableView();
                    tabela.setItems(this.listaFiltrada);
                    Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Aluno","Exclusão com Sucesso","Aluno "+ aluno.getNome() + " excluído com sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Aluno","Erro ao excluir",e.getMessage()).showAndWait();
                }
            }
        });

    }


    public void btnNovoClick(ActionEvent actionEvent) {
            AlunoEdicaoController controllerEdicao=new AlunoEdicaoController();
            controllerEdicao.setAluno(null);
            controllerEdicao.carregarModal();

            if (controllerEdicao.getAluno()!=null){
                this.setAlunoEditado(controllerEdicao.getAluno());
                try {
                    servicoAluno.incluir(this.getAlunoEditado());
                    servicoAluno.salvarArquivo();
                    carregaListasTableView();
                    tabela.setItems(this.listaFiltrada);
                    Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Aluno","Aluno Salvo","Aluno Salvo com Sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Aluno","Erro ao Salvar",e.getMessage()).showAndWait();
                }
            }
        }

    public void mnuMatricularClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Matrícula de Aluno","Impossível Matricula","Selecione um aluno para matricular").showAndWait();
            return;
        }

        TurmaSelecaoController controllerSelecao=new TurmaSelecaoController();
        try {
            controllerSelecao.carregarModal();
            if (controllerSelecao.getTurmaSelecionado()!=null){
                this.setTurmaSelecionada(controllerSelecao.getTurmaSelecionado());
                AlunoMatriculado alunoMatriculado = new AlunoMatriculado(this.getTurmaSelecionada(),aluno);
                try {
                    servicoAlunoMatriculado.incluir(alunoMatriculado);
                    servicoAlunoMatriculado.salvarArquivo();
                    Util.getAlert(Alert.AlertType.INFORMATION,"Matrícula de Aluno","Aluno Matriculado","Matricula Salva com Sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Matrícula de Aluno","Erro ao Matricular",e.getMessage()).showAndWait();
                }


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void mnuTurmasAtuaisClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Lista de Turmas","Impossível Exibir Lista de Turmas","Selecione um aluno para exibir").showAndWait();
            return;
        }
        TurmasAlunoController controller=new TurmasAlunoController();
        controller.setTurmasAtuais(true);
        controller.setTurmaConcluidas(false);
        controller.setAlunoSelecionado(aluno);
        controller.setTitulo("Turmas Atuais de "+  aluno.getNome());
        try {
            controller.carregarModal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void mnuTurmasConcluidasClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Lista de Turmas","Impossível Exibir Lista de Turmas","Selecione um aluno para exibir").showAndWait();
            return;
        }
        TurmasAlunoController controller=new TurmasAlunoController();
        controller.setAlunoSelecionado(aluno);
        controller.setTurmaConcluidas(true);
        controller.setTurmasAtuais(false);
        controller.setTitulo("Turmas Concluídas de " + aluno.getNome());
        try {
            controller.carregarModal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mnuTrancarTurmaClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Lista de Turmas","Impossível Exibir Lista de Turmas","Selecione um aluno para exibir").showAndWait();
            return;
        }
        TurmasAlunoController controller=new TurmasAlunoController();
        controller.setTurmasAtuais(true);
        controller.setTurmaConcluidas(false);
        controller.setAlunoSelecionado(aluno);
        controller.setTrancamento(true);
        controller.setTitulo("Trancamento de Disciplina de "+  aluno.getNome());
        try {
            controller.carregarModal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mnuTrancarSemestreClick(ActionEvent actionEvent) {
        Aluno aluno = (Aluno) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (aluno == null){
            Util.getAlert(Alert.AlertType.WARNING,"Trancamento de Semestre","Impossível Trancar Semestre","Selecione um aluno para trancar o semestre").showAndWait();
            return;
        }

        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Trancamento de Semestre", "Trancar o semestre?","Deseja trancar o semestre do aluno "+aluno.getNome() + "?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn-> {
            if (btn.getText().equals("OK")) {
                servicoAluno.trancarSemestre(aluno,Util.getSemestreAtual());
                try {
                    servicoAluno.salvarArquivo();
                    Util.getAlert(Alert.AlertType.INFORMATION,"Trancamento de Semestre","Semestre Trancado","Semestre Trancado com Sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Trancamento de Semestre","Erro ao Trancar o Semestre",e.getMessage()).showAndWait();
                }
            }
        });

    }

    public void mnuBoletimCompletoClick(ActionEvent actionEvent) {
    }

    public void mnuBoletimResumidoClick(ActionEvent actionEvent) {
    }
}
