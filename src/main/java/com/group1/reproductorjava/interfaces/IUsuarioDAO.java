package com.group1.reproductorjava.interfaces;

import com.group1.reproductorjava.model.Lista;
import com.group1.reproductorjava.model.Usuario;

import java.util.List;

public interface IUsuarioDAO {
    boolean getUsuario(int id);
    boolean getUsuario(String name);
    List<Usuario> getAllUsuarios();
    boolean saveUsuario(Usuario usuario);
    boolean deleteUsuario(Usuario usuario);
}
