package com.group1.reproductorjava.model.Entity;

import com.group1.reproductorjava.model.DAOs.CancionDAO;
import com.group1.reproductorjava.model.DAOs.ListaDAO;

import java.util.List;
import java.util.Objects;

public class Cancion {
    int id;
    String name;
    int duration;//en segundos
    String gender;
    int nReproductions;
    Disco disco;

    public Cancion(int id) {
        this(id, "", 0, "", 0, null);
    }

    public Cancion(int id, String name, int duration, String gender, int nReproductions, Disco disco) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.gender = gender;
        this.nReproductions = nReproductions;
        this.disco = disco;
    }

    public Cancion() {
        this(-1, "", 0, "", 0, null);
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getnReproductions() {
        return nReproductions;
    }

    public void setnReproductions(int nReproductions) {
        this.nReproductions = nReproductions;
    }

    public Disco getDisco() {
        return disco;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cancion cancion = (Cancion) o;
        return id == cancion.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", gender='" + gender + '\'' +
                ", nReproductions=" + nReproductions +
                '}';
    }
}
