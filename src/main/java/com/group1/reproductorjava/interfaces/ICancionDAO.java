package com.group1.reproductorjava.interfaces;

import com.group1.reproductorjava.model.Cancion;

import java.util.List;

public interface ICancionDAO {
    boolean getCancion(int id);
    boolean getCancion(String name);
    List<Cancion> getAllCanciones();
    boolean saveCancion(Cancion cancion);
    boolean deleteCancion(Cancion cancion);
    void oneReproduction();
    List<Cancion> selectByGender(String gender);
}
