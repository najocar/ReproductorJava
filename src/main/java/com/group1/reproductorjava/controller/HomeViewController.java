package com.group1.reproductorjava.controller;

import com.group1.reproductorjava.HelloApplication;
import com.group1.reproductorjava.model.DAOs.CancionDAO;
import com.group1.reproductorjava.model.DAOs.ListaDAO;
import com.group1.reproductorjava.model.DAOs.UsuarioDAO;
import com.group1.reproductorjava.model.DTOs.ControlDTO;
import com.group1.reproductorjava.model.Entity.Lista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    @FXML
    private TableView<Lista> ListTable;
    @FXML
    private TableColumn NameColumn;
    @FXML
    private TableColumn DescriptionColumn;

    @FXML
    private Label userName;

    @FXML
    private ImageView image;



    private ObservableList<Lista> listas;

    UsuarioDAO userDao = new UsuarioDAO(2); //cambiar esto por el id del usuario loggeado


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        listas = FXCollections.observableArrayList();
        this.NameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.DescriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));

        generateListTable();
        setInfoUser();
    }

    @FXML
    public void generateListTable() {

        listas.setAll(ListaDAO.getAllListas());
        this.ListTable.setItems(listas);
    }

    @FXML
    public void setInfoUser() {
        userName.setText(userDao.getName());

        String imagePath = userDao.getPhoto();
        if (imagePath != null) {
            Image imagenJ = new Image(new File("../resources/com/group1/reproductorjava/images/"+imagePath).toURI().toString());
            image.setImage(imagenJ);
        }
    }

    @FXML
    public void goLogin() {
        try {
            HelloApplication.setRoot("LoginView");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goList() {
        try {
            ControlDTO.setLista(selectList());
            HelloApplication.setRoot("CancionListView");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void newList() {
        try {
            HelloApplication.setRoot("newList");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteList() {
        if (selectList()!=null) {
            listas.remove(selectList());
            new ListaDAO(selectList()).deleteLista(selectList());
        }
    }

    @FXML
    public Lista selectList(){
        Lista result = null;
        Lista aux = this.ListTable.getSelectionModel().getSelectedItem();
        if (aux != null){
            result = aux;
        }
        return result;
    }
}
