package com.group1.reproductorjava.model.Entity;

import java.util.List;
import java.util.Objects;

public class Lista {
    private int id;
    private String name;
    private String description;
    protected Usuario userCreator;
    protected List<Cancion> canciones;
    protected List<Comentario> comentarios;

    public Lista(int id, String name, Usuario userCreator, String description) {
        this.id = id;
        this.name = name;
        this.userCreator = userCreator;
        this.description = description;
    }

    public Lista(int id) {
        this(id, "", null, "");
    }

    public Lista() {
        this(-1, "", null,"");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Usuario getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(Usuario userCreator) {
        this.userCreator = userCreator;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lista lista = (Lista) o;
        return id == lista.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Lista{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userCreator=" + userCreator +
                ", canciones=" + canciones +
                ", comentarios=" + comentarios +
                '}';
    }
}
