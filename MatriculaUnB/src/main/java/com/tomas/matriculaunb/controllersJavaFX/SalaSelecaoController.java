package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.StarterApplication;
import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Sala;
import com.tomas.matriculaunb.servicos.ServicoSala;
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

public class SalaSelecaoController {
    private Sala salaSelecionada;
    public TableView tabela;
    public TextField txtProcura;
    private ObservableList<Sala> listaTabela;
    private FilteredList<Sala> listaFiltrada;
    private Dialog<ButtonType> dialog;




    public Sala getSalaSelecionada() {
        return salaSelecionada;
    }

    public void setSalaSelecionada(Sala salaSelecionada) {
        this.salaSelecionada = salaSelecionada;
    }

    public void initialize(){
        ServicoSala servicoSala = ServicoSala.getInstance();
        List<Sala> listaSelecaoSala = new ArrayList<>();
        for (ClasseBase obj:servicoSala.getLista()){
            listaSelecaoSala.add((Sala) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaSelecaoSala);
        this.listaTabela.sort(Comparator.comparing(sala -> sala.getLocal()));
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
        TableColumn<Sala, String> localColumn = new TableColumn<>("Local");
        localColumn.setPrefWidth(100);
        localColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocal()));
        TableColumn<Sala, String> campusColumn = new TableColumn<>("Campus");
        campusColumn.setPrefWidth(300);
        campusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCampus().toString()));
        tabela.getColumns().addAll( localColumn,campusColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Sala> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.setSalaSelecionada((Sala) this.tabela.getSelectionModel().selectedItemProperty().get());
                    System.out.println("tem sala");
                    dialog.close();
                }
            });
            return row ;
        });

    }

    public void carregarModal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("salaSelecao.fxml"));
        fxmlLoader.setController(this);
        dialog = new Dialog<>();
        dialog.setDialogPane(fxmlLoader.load());
        dialog.setTitle("Seleção de Sala");
        Optional<ButtonType> result = dialog.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType.getText().equals("OK")){
                if (this.tabela.getSelectionModel().selectedItemProperty().get()!=null){
                    this.setSalaSelecionada((Sala) this.tabela.getSelectionModel().selectedItemProperty().get());
                    System.out.println("tem sala");
                }
                else{
                    this.setSalaSelecionada(null);
                    System.out.println("não tem sala");
                }
            }
        });
    }
    public void txtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(sala -> {
            return sala.getLocal().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || sala.getCampus().toString().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }
}
