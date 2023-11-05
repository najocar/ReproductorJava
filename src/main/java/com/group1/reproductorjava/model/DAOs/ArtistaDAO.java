package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.interfaces.IArtistaDAO;

import java.util.List;

public class ArtistaDAO implements IArtistaDAO {



    @Override
    public boolean getArtista(int id) {
        return false;
    }

    @Override
    public boolean getArtista(String name) {
        return false;
    }

    @Override
    public List<Artista> getAllArtists() {
        return null;
    }

    @Override
    public boolean saveArtista(Artista artista) {
        return false;
    }

    @Override
    public boolean deleteArtista(Artista artista) {
        return false;
    }
}
