package com.group1.reproductorjava.model.interfaces;

import com.group1.reproductorjava.model.Entity.Usuario;

import java.util.List;

public interface IUsuarioDAO {
    boolean getUsuario(int id);
    boolean getUsuario(String name);
//    static List<Usuario> getAllUsuarios()
    boolean saveUsuario();
    boolean deleteUsuario();
}
