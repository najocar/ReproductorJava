package com.group1.reproductorjava.controller;

import com.group1.reproductorjava.model.DAOs.CancionDAO;
import com.group1.reproductorjava.model.DAOs.ListaDAO;
import com.group1.reproductorjava.model.DAOs.UsuarioDAO;
import com.group1.reproductorjava.model.DTOs.ControlDTO;
import com.group1.reproductorjava.model.Entity.Cancion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CancionListViewController implements Initializable {

    @FXML
    private Button btnHomem, btnPlay;

    @FXML
    private TableView<Cancion> table;

    @FXML
    private TableColumn colName;

    @FXML
    private Label labelListaName, labelUserName;

    private ObservableList<Cancion> cancionList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("inicializando");
        cancionList = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory("name"));

        ControlDTO.setUser(new UsuarioDAO(5));
        ControlDTO.setLista(new ListaDAO(3));

        loadTable();
        loadView();
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

    public void loadView(){
        if(ControlDTO.getLista() != null)labelListaName.setText(ControlDTO.getLista().getName());
        if(ControlDTO.getUser() != null)labelUserName.setText(ControlDTO.getUser().getName());

    }

    @FXML
    private Cancion selectSong(){
        Cancion result = null;
        Cancion aux = this.table.getSelectionModel().getSelectedItem();
        if(aux!=null)result = aux;
        return result;
    }

    @FXML
    public void play(){
        Cancion aux = selectSong();
        if(aux == null) return;
        ControlDTO.setSong(aux);
        //AQUI NAVEGARIAMOS A LA VISTA REPRODUCTOR
        System.out.println(ControlDTO.getSong());
    }
}
