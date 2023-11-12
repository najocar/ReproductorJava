package com.group1.reproductorjava.controller;

import com.group1.reproductorjava.HelloApplication;
import com.group1.reproductorjava.model.DAOs.CancionDAO;
import com.group1.reproductorjava.model.DAOs.ListaDAO;
import com.group1.reproductorjava.model.DAOs.UsuarioDAO;
import com.group1.reproductorjava.model.Entity.Cancion;
import com.group1.reproductorjava.model.Entity.Lista;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class NewListController implements Initializable {

    @FXML
    private TableView<Cancion> SongTable;
    @FXML
    private TableColumn NameColumn;
    @FXML
    private TableColumn DiscColumn;

    @FXML
    private Label userName;
    @FXML
    private TextField nameListField;
    @FXML
    private TextField descListField;




    private Set<Cancion> can = new HashSet();

    private ObservableList<Cancion> canciones = FXCollections.observableArrayList();

    UsuarioDAO userDao = new UsuarioDAO(2); //cambiar esto por el id del usuario loggeado


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        canciones = FXCollections.observableArrayList();
        this.NameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.DiscColumn.setCellValueFactory(new PropertyValueFactory("gender"));
        SongTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        SongTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Cancion> c) -> {
            Set<Cancion> selectedCanciones = new HashSet<>(SongTable.getSelectionModel().getSelectedItems());

            for (Cancion cancion : selectedCanciones) {
                can.add(cancion);
            }
        });

        generateListTable();
        setInfoUser();
    }

    @FXML
    public void generateListTable() {

        canciones.setAll(CancionDAO.getAllCanciones());
        this.SongTable.setItems(canciones);
    }

    @FXML
    public void setInfoUser() {
        userName.setText(userDao.getName());
    }

    @FXML
    public void goHome() {
        try {
            HelloApplication.setRoot("Home-View");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goLogin() {
        try {
            HelloApplication.setRoot("Home-view");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void createList() throws SQLException {


        Lista lista = new Lista(-1, nameListField.getText(), userDao, descListField.getText());

        ListaDAO l = new ListaDAO(lista);

        l.save();
        for (Cancion cancion : can) {
//            l.addCancion(cancion);
            l.saveSongRelation(cancion);
        }
        goHome();




    }
}
