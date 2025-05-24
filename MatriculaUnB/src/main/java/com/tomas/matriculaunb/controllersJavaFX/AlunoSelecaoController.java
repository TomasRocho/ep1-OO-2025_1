package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.Aluno;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.servicos.ServicoAluno;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoSelecaoController {
    private Aluno alunoSelecionado;
    public TableView tabela;
    public TextField txtProcura;
    private ObservableList<Aluno> listaTabela;
    private FilteredList<Aluno> listaFiltrada;
    private Dialog<ButtonType> dialog;




    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
    }

    public void initialize(){
        ServicoAluno servicoAluno = ServicoAluno.getInstance();
        List<Aluno> listaSelecaoAlunos = new ArrayList<>();
        for (ClasseBase obj:servicoAluno.getLista()){
            listaSelecaoAlunos.add((Aluno) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaSelecaoAlunos);
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
        TableColumn<Aluno, String> matriculaColumn = new TableColumn<>("Matrícula");
        matriculaColumn.setPrefWidth(100);
        matriculaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        TableColumn<Aluno, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setPrefWidth(300);
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        tabela.getColumns().addAll( matriculaColumn,nomeColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Aluno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.setAlunoSelecionado((Aluno) this.tabela.getSelectionModel().selectedItemProperty().get());
                    System.out.println("tem aluno");
                    dialog.close();
                }
            });
            return row ;
        });

    }

    public void carregarModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("alunoSelecao.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        dialog.setTitle("Seleção de Aluno");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")){
                if (this.tabela.getSelectionModel().selectedItemProperty().get()!=null){
                    this.setAlunoSelecionado((Aluno) this.tabela.getSelectionModel().selectedItemProperty().get());
                    System.out.println("tem aluno");
                }
                else{
                    this.setAlunoSelecionado(null);
                    System.out.println("não tem aluno");
                }
            }
        });
    }
    public void onTxtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(aluno -> {
            return aluno.getNome().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || aluno.getMatricula().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }
}
