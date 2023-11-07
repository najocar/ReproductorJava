package com.group1.reproductorjava.model.interfaces;

public interface IComentarioDAO {
    boolean getComentario(int id);
    boolean saveComentario();
    boolean deleteComentario();
}
