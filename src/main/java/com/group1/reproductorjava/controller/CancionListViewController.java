package com.group1.reproductorjava.controller;

import com.group1.reproductorjava.model.DAOs.CancionDAO;
import com.group1.reproductorjava.model.DAOs.ListaDAO;
import com.group1.reproductorjava.model.DTOs.ControlDTO;
import com.group1.reproductorjava.model.Entity.Cancion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CancionListViewController implements Initializable {

    @FXML
    private Button btnHome;

    @FXML
    private TableView<Cancion> table;

    @FXML
    private TableColumn colName;

    private ObservableList<Cancion> cancionList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("inicializando");
        cancionList = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory("name"));

        ControlDTO.setLista(new ListaDAO(3));

        loadTable();
        System.out.println("terminado");
    }

    public void loadTable(){
        cancionList.clear();
        List<Cancion> aux = CancionDAO.getCancionesByList(ControlDTO.getLista().getId());
        if(aux == null) return;
        cancionList.addAll(aux);
        System.out.println(cancionList);
        table.setItems(cancionList);
        table.refresh();
    }
}
