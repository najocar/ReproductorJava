package com.group1.reproductorjava.model.Entity;

import com.group1.reproductorjava.model.DAOs.UsuarioDAO;
import com.group1.reproductorjava.model.Entity.Lista;

import java.util.List;
import java.util.Objects;

public class Usuario {
    int id;
    String name;
    String email;
    String photo;
    int rol;
    List<Lista> playlists;

    public Usuario() {
        this(-1, "", "", "", 1);
    }

    public Usuario(int id, String name, String email, String photo, int rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.rol = rol;
    }

    public Usuario(int id){
        this(id, "", "", "", 1);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public List<Lista> getPlaylists() {
        if(playlists == null){
            List<Lista> aux = new UsuarioDAO(this).getLista();
            if(aux != null) playlists = aux;
        }
        return playlists;
    }

    public void setPlaylists(List<Lista> playlists) {
        this.playlists = playlists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", rol=" + rol +
                '}';
    }
}
