package com.group1.reproductorjava.model.Entity;

import java.util.Date;

public class Comentario {
    int id;
    Date date;
    String message;
    Usuario usuario;

    public Comentario(int id, Date date, String message, Usuario usuario) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.usuario = usuario;
    }

    public Comentario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}