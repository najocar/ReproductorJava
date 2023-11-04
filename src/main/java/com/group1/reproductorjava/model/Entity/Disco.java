package com.group1.reproductorjava.model.Entity;

import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.Entity.Cancion;

import java.util.Date;
import java.util.List;

public class Disco {
    int id;
    String name;
    Date date;
    String photo;
    Artista artista;
    List<Cancion> canciones;
}
