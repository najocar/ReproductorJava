package com.group1.reproductorjava.model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Artista {
   private int id;
    private String name;
    private String photo;
   private String nacionality;
    protected List<Disco> discos;

    public Artista(int id, String name, String photo, String nacionality) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.nacionality = nacionality;
    }

    public Artista(){
        this(0,"","","");
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    public List<Disco> getDiscos() {
        return discos;
    }

    public void setDiscos(List<Disco> discos) {
        this.discos = discos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artista artista = (Artista) o;
        return id == artista.id && Objects.equals(name, artista.name) && Objects.equals(photo, artista.photo) && Objects.equals(nacionality, artista.nacionality) && Objects.equals(discos, artista.discos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photo, nacionality, discos);
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", nacionality='" + nacionality + '\'' +
                ", discos=" + discos +
                '}';
    }
}
