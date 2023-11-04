package com.group1.reproductorjava.model.interfaces;

import com.group1.reproductorjava.model.Entity.Cancion;

import java.util.List;

public interface ICancionDAO {
    boolean getCancion(int id);
    boolean getCancion(String name);
    boolean saveCancion();
    boolean deleteCancion();
    void oneReproduction();
}
