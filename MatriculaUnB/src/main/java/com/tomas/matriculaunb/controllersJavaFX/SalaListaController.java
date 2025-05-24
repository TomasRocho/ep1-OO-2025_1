package com.tomas.matriculaunb.controllersJavaFX;

import com.tomas.matriculaunb.modelo.ClasseBase;
import com.tomas.matriculaunb.modelo.Sala;
import com.tomas.matriculaunb.servicos.ServicoSala;
import com.tomas.matriculaunb.util.Util;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaListaController {

    private ServicoSala servicoSala = ServicoSala.getInstance();
    private ObservableList<Sala> listaTabela;
    private FilteredList<Sala> listaFiltrada;
    public TableView tabela;
    public TextField txtProcura;
    public Button btnAltera;
    private Sala salaEditada;

    public Sala getSalaEditada() {
        return salaEditada;
    }

    public void setSalaEditada(Sala salaEditada) {
        this.salaEditada = salaEditada;
    }

    public void initialize(){

        carregaListasTableView();

        TableColumn<Sala, String> localColumn = new TableColumn<>("Local");
        localColumn.setPrefWidth(300);
        localColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocal()));
        TableColumn<Sala, String> campusColumn = new TableColumn<>("Campus");
        campusColumn.setPrefWidth(100);
        campusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCampus().toString()));
        tabela.getColumns().addAll( localColumn,campusColumn);
        tabela.setItems(listaFiltrada);
        tabela.setRowFactory( tv -> {
            TableRow<Sala> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    btnAltera.fire();
                }
            });


            return row ;
        });
    }

    public void carregaListasTableView(){
        List<Sala> listaSalas = new ArrayList<>();
        for (ClasseBase obj:servicoSala.getLista()){
            listaSalas.add((Sala) obj);
        }
        this.listaTabela = FXCollections.observableArrayList(listaSalas);
        this.listaFiltrada = new FilteredList<>(this.listaTabela);
    }

    public void onTxtProcuraChange(KeyEvent keyEvent) {
        this.listaFiltrada.setPredicate(sala -> {
            return sala.getLocal().toUpperCase().contains(txtProcura.getText().toUpperCase())
                    || sala.getCampus().toString().toUpperCase().contains(txtProcura.getText().toUpperCase());
        });
    }

    public void onBtnAlteraClick(ActionEvent actionEvent) {
        Sala sala = (Sala) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (sala == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }
        SalaEdicaoController controllerEdicao=new SalaEdicaoController();
        controllerEdicao.setSala(sala);
        controllerEdicao.carregarModal();

        if (controllerEdicao.getSala()!=null){
            this.setSalaEditada(controllerEdicao.getSala());
            try {
                servicoSala.alterar(this.getSalaEditada());
                servicoSala.salvarArquivo(sala);
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                tabela.refresh();
                Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Sala","Sala Salva","Sala Salva com Sucesso").showAndWait();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Sala","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }


    }

    public void onBtnExcluirClick(ActionEvent actionEvent) {
        Sala sala = (Sala) this.tabela.getSelectionModel().selectedItemProperty().get();
        if (sala == null){
            Util.getAlert(Alert.AlertType.WARNING,"Alteração/Exclusão de Registro","Impossível Alterar/Excluir","Selecione um registro para alterar/excluir").showAndWait();
            return;
        }
        Alert alert = Util.getAlert(Alert.AlertType.CONFIRMATION,"Exclusão de Sala", "Excluir?","Deseja excluir a sala "+sala.getLocal() + "?");
        Optional<ButtonType> btnAlert = alert.showAndWait();
        btnAlert.ifPresent(btn->{
            if (btn.getText().equals("OK")){
                try {
                    servicoSala.excluir(sala);
                    servicoSala.salvarArquivo();
                    carregaListasTableView();
                    tabela.setItems(this.listaFiltrada);
                    Util.getAlert(Alert.AlertType.INFORMATION,"Exclusão de Sala","Exclusão com Sucesso","Sala "+ sala.getLocal() + " excluído com sucesso").showAndWait();
                } catch (Exception e) {
                    Util.getAlert(Alert.AlertType.ERROR,"Exclusão de Sala","Erro ao excluir",e.getMessage()).showAndWait();
                }
            }
        });

    }


    public void onBtnNovoClick(ActionEvent actionEvent) {
        SalaEdicaoController controllerEdicao=new SalaEdicaoController();
        controllerEdicao.setSala(null);
        controllerEdicao.carregarModal();

        if (controllerEdicao.getSala()!=null){
            this.setSalaEditada(controllerEdicao.getSala());
            try {
                servicoSala.incluir(this.getSalaEditada());
                servicoSala.salvarArquivo();
                carregaListasTableView();
                tabela.setItems(this.listaFiltrada);
                Util.getAlert(Alert.AlertType.INFORMATION,"Salvamento de Sala","Sala Salva","Sala Salva com Sucesso").showAndWait();
            } catch (Exception e) {
                Util.getAlert(Alert.AlertType.ERROR,"Salvamento de Sala","Erro ao Salvar",e.getMessage()).showAndWait();
            }
        }
    }
}
