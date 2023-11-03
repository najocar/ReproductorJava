package com.group1.reproductorjava.interfaces;

import com.group1.reproductorjava.model.Comentario;
import com.group1.reproductorjava.model.Lista;

import java.util.List;

public interface IListaDAO {
    boolean getLista(int id);
    boolean getLista(String name);
    List<Lista> getAllListas();
    boolean saveLista(Lista Lista);
    boolean deleteLista(Lista Lista);
    boolean addComment(Comentario coment);
    boolean deleteComment(Comentario coment);
}
