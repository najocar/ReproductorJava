package com.group1.reproductorjava.model.DTOs;

import com.group1.reproductorjava.model.Entity.Cancion;
import com.group1.reproductorjava.model.Entity.Lista;
import com.group1.reproductorjava.model.Entity.Usuario;

public class ControlDTO {

    private static ControlDTO _newInstance;

    private static Usuario _usuario = null;
    private static Lista _lista = null;
    private static Cancion _cancion = null;

    private ControlDTO(Usuario user, Lista lista, Cancion song){
        _usuario = user;
        _lista = lista;
        _cancion = song;
    }

    public static Usuario getUser(){
        if(_newInstance == null) return null;
        return _usuario;
    }

    public static Usuario setUser(Usuario usuario){
        _newInstance = new ControlDTO(usuario, _lista, _cancion);
        return _usuario;
    }

    public static Cancion getSong(){
        if(_newInstance == null) return null;
        return _cancion;
    }

    public static Cancion setSong(Cancion song){
        _newInstance = new ControlDTO(_usuario, _lista, song);
        return _cancion;
    }

    public static Lista getLista(){
        if(_newInstance == null) return null;
        return _lista;
    }
    public static Lista setLista(Lista lista){
        _newInstance = new ControlDTO(_usuario, lista, _cancion);
        return _lista;
    }
}
