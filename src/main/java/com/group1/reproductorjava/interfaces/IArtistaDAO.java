package com.group1.reproductorjava.interfaces;

import com.group1.reproductorjava.model.Artista;

import java.util.List;

public interface IArtistaDAO {
    boolean getArtista(int id);
    boolean getArtista(String name);
    List<Artista> getAllArtists();
    boolean saveArtista(Artista artista);
    boolean deleteArtista(Artista artista);
}
