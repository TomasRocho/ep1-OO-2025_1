package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Disciplina;
import com.tomas.matriculaunb.modelo.Sala;
import com.tomas.matriculaunb.servicos.ServicoDisciplina;
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

public class DisciplinaSelecaoController {
    private Disciplina disciplinaSelecionada;
    public TableView tabela;
    public TextField txtProcura;
    private ObservableList<Disciplina> listaTabela;
    private FilteredList<Disciplina> listaFiltrada;
    private Dialog<ButtonType> dialog;


    public Disciplina getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

    public void initialize(){
        ServicoDisciplina servicoDisciplina = ServicoDisciplina.getInstance();
        List<Disciplina> listaSelecaoDisciplina = new ArrayList<>();
        for (ClasseBase obj:servicoDisciplina.getLista()){
            listaSelecaoDisciplina.add((Disciplina) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaSelecaoDisciplina);
        this.listaTabela.sort(Comparator.comparing(disciplina -> disciplina.getTitulo()));
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
        TableColumn<Disciplina, String> codigoColumn = new TableColumn<>("Código");
        codigoColumn.setPrefWidth(100);
        codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        TableColumn<Disciplina, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setPrefWidth(300);
        tituloColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        tabela.getColumns().addAll( codigoColumn,tituloColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Sala> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.setDisciplinaSelecionada((Disciplina) this.tabela.getSelectionModel().selectedItemProperty().get());
                    dialog.close();
                }
            });
            return row ;
        });

    }

    public void carregarModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("disciplinaSelecao.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        dialog.setTitle("Seleção de Disciplina");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")){
                if (this.tabela.getSelectionModel().selectedItemProperty().get()!=null){
                    this.setDisciplinaSelecionada((Disciplina) this.tabela.getSelectionModel().selectedItemProperty().get());
                }
                else{
                    this.setDisciplinaSelecionada(null);
                }
            }
        });
    }
    public void txtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(disciplina -> {
            return disciplina.getCodigo().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || disciplina.getTitulo().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }
}
