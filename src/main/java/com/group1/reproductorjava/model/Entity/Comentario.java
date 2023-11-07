package com.group1.reproductorjava.model.Entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Comentario {
    int id;
    LocalDate date;
    String message;
    Usuario usuario;
    Lista lista;

    public Comentario(int id) {
        this(id, null, "", null, null);
    }

    public Comentario(int id, LocalDate date, String message, Usuario user, Lista list){
        this.id = id;
        this.date = date;
        this.message = message;
        this.usuario = user;
        this.lista = list;
    }

    public Comentario(){
        this(-1, null, "", null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista list) {
        this.lista = lista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comentario that = (Comentario) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", usuario=" + usuario + '\'' +
                ", lista=" + lista +
                '}';
    }
}