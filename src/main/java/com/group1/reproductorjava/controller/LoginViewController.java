package com.group1.reproductorjava.controller;

import com.group1.reproductorjava.AppTestView;
import com.group1.reproductorjava.HelloApplication;
import com.group1.reproductorjava.model.DAOs.ArtistaDAO;
import com.group1.reproductorjava.model.DAOs.UsuarioDAO;
import com.group1.reproductorjava.model.Entity.Artista;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private TextField UserField;

    @FXML
    private PasswordField PassField;

    @FXML
    private Button login_btn;

    @FXML
    private Button register_btn;

    @FXML
    private void login() throws IOException {
        String nickname = UserField.getText();
        if (nickname.isEmpty()) {
            showError("El campo del nickname está vacío");
        } else {

            UsuarioDAO UDAO=new UsuarioDAO(1);
            if(UDAO.getUsuario(nickname)){
                HelloApplication.setRoot("Home-view");
            }
            else{
                showError("no se ha encontrado el nickname");
            }
        }
    }
    @FXML
    private void register() throws IOException {
        System.out.println("no está implementado");
    }

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Log-in Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
