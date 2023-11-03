package com.group1.reproductorjava.interfaces;

import com.group1.reproductorjava.model.Artista;
import com.group1.reproductorjava.model.Disco;

import java.util.List;

public interface IDiscoDAO {
    boolean getDisco(int id);
    boolean getDisco(String name);
    List<Disco> getAllDiscos();
    boolean saveDisco(Disco disco);
    boolean deleteDisco(Disco disco);
}
