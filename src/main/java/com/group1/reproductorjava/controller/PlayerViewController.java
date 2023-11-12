package com.group1.reproductorjava.controller;

import com.group1.reproductorjava.AppTestView;
import com.group1.reproductorjava.model.DAOs.CancionDAO;
import com.group1.reproductorjava.model.DTOs.ControlDTO;
import com.group1.reproductorjava.model.Entity.Cancion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Controlador para la vista del reproductor de música.
 */
public class PlayerViewController {

    @FXML
    private Button back_btn;

    @FXML
    private Button play_btn;

    @FXML
    private Button stop_btn;

    @FXML
    private Button last_btn;

    @FXML
    private Button next_btn;

    @FXML
    private ImageView songImage;

    @FXML
    private Label songNameLabel;

    private String selectedSongName;

    /**
     * Establece el nombre de la canción seleccionada.
     *
     * @param songName Nombre de la canción seleccionada.
     */
    public void setSongName(String songName) {
        this.selectedSongName = songName;
        loadSelectedSong();
    }

    private boolean isPlaying = false;

    /**
     * Maneja el evento de reproducción y pausa del reproductor.
     */
    @FXML
    private void playAndPause() {
        if (isPlaying) {
            play_btn.setText("▶");
        } else {
            play_btn.setText("⏸");
        }
        isPlaying = !isPlaying;
    }

    /**
     * Navega de vuelta a la vista de inicio.
     *
     * @throws IOException Si hay un error al cambiar la vista.
     */
    @FXML
    private void goHome() throws IOException {
        AppTestView.setRoot("homeview");
    }

    /**
     * Carga la información de la canción seleccionada en la interfaz.
     */
    private void loadSelectedSong() {
        Cancion currentSong = ControlDTO.getSong();
        if (currentSong != null){
            songNameLabel.setText(currentSong.getName());
        }
        updateReproductions();

    }
    /*
     * Actualiza el número de reproducciones de la canción seleccionada.
     *
     * @param songName Nombre de la canción para la cual se actualizarán las reproducciones.
     */
    private void updateReproductions() {
        Cancion currentSong = ControlDTO.getSong();
        if (currentSong != null) {
            CancionDAO cancionDAO= new CancionDAO(currentSong);
            cancionDAO.oneReproduction();
            ControlDTO.getSong().setnReproductions(ControlDTO.getSong().getnReproductions()+1);
            }
        }
    }

