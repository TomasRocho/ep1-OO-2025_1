package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Turma;
import com.tomas.matriculaunb.servicos.ServicoTurma;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TurmaSelecaoController {
    private Turma turmaSelecionado;
    public TableView tabela;
    public TextField txtProcura;
    private ObservableList<Turma> listaTabela;
    private FilteredList<Turma> listaFiltrada;
    private Dialog<ButtonType> dialog;




    public Turma getTurmaSelecionado() {
        return turmaSelecionado;
    }

    public void setTurmaSelecionado(Turma turmaSelecionado) {
        this.turmaSelecionado = turmaSelecionado;
    }

    public void initialize(){
        ServicoTurma servicoTurma = ServicoTurma.getInstance();
        List<Turma> listaSelecaoTurma= new ArrayList<>();
        for (ClasseBase obj:servicoTurma.getLista()){
            listaSelecaoTurma.add((Turma) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaSelecaoTurma);
        this.listaTabela.sort(Comparator.comparing(turma -> turma.getDisciplina().getTitulo()));
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
        TableColumn<Turma, String> codigoColumn = new TableColumn<>("Código");
        codigoColumn.setPrefWidth(60);
        codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        TableColumn<Turma, String> disciplinaColumn = new TableColumn<>("Disciplina");
        disciplinaColumn.setPrefWidth(150);
        disciplinaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisciplina().getTitulo()));
        TableColumn<Turma, String> professorColumn = new TableColumn<>("Professor");
        professorColumn.setPrefWidth(150);
        professorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfessor().getNome()));
        TableColumn<Turma, String> semestreColumn = new TableColumn<>("Semestre");
        semestreColumn.setPrefWidth(80);
        semestreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSemestreAno()));
        tabela.getColumns().addAll(codigoColumn,disciplinaColumn,professorColumn,semestreColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Turma> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.setTurmaSelecionado((Turma) this.tabela.getSelectionModel().selectedItemProperty().get());
                    dialog.close();
                }
            });
            return row ;
        });

    }

    public void carregarModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("turmaSelecao.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        dialog.setTitle("Seleção de Turma");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().trim().equalsIgnoreCase("OK")){
                if (this.tabela.getSelectionModel().selectedItemProperty().get()!=null){
                    this.setTurmaSelecionado((Turma) this.tabela.getSelectionModel().selectedItemProperty().get());
                }
                else{
                    this.setTurmaSelecionado(null);
                }
            }
        });
    }
    public void txtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(turma -> {
            return turma.getCodigo().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || turma.getDisciplina().getTitulo().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }
}
