package com.group1.reproductorjava.model.Entity;

import com.group1.reproductorjava.model.DAOs.CancionDAO;
import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.Entity.Cancion;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Disco {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private String photo;
    private Artista artista;
    private List<Cancion> canciones;

    public Disco() {
        // Constructor vacío
    }

    public Disco(int id, String nombre, LocalDate fecha, String photo, Artista artista, List<Cancion> canciones) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.photo = photo;
        this.artista = artista;
        this.canciones = canciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public List<Cancion> getCanciones() {
        if(canciones == null){
            List<Cancion> aux = CancionDAO.getCancionesByList(getId());
            if(aux != null) canciones = aux;
        }
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    @Override
    public String toString() {
        return "Disco{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", date=" + fecha +
                ", photo='" + photo + '\'' +
                ", artista=" + artista +
                ", canciones=" + canciones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disco disco = (Disco) o;
        return id == disco.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

