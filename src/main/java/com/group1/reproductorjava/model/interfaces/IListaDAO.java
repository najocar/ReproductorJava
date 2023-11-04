package com.group1.reproductorjava.model.interfaces;

import com.group1.reproductorjava.model.Entity.Comentario;
import com.group1.reproductorjava.model.Entity.Lista;

import java.util.List;

public interface IListaDAO {
    boolean getLista(int id);
    boolean getLista(String name);
    List<Lista> getAllListas();
    boolean save();
    boolean deleteLista(Lista Lista);
    boolean addComment(Comentario coment);
    boolean deleteComment(Comentario coment);
}
