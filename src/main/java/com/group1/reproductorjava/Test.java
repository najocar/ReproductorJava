package com.group1.reproductorjava;

import com.group1.reproductorjava.model.DAOs.*;
import com.group1.reproductorjava.model.Entity.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Test {
    public static void main(String[] args) throws SQLException {
//        Artista artista = new Artista(-1,"jose","foto","España");
//        Artista artista = new ArtistaDAO(5);
//        artista.setName("josua II");
//        System.out.println(new ArtistaDAO().getAllArtists());

//        System.out.println(new ArtistaDAO(4).deleteArtista());

//        Disco disco = new Disco(-1,"los ecos el rocio", LocalDate.now(),"foto", new ArtistaDAO(5),null);

//        Cancion cancion = new Cancion(-1,"gasolina",120,"reggaetton",0, new Disco(6,"los ecos el rocio", LocalDate.now(),"foto", new ArtistaDAO(5),null));
//        new CancionDAO(cancion).saveCancion();

//        Usuario usuario = new Usuario(-1,"elPepe","a@gmail.com","foto",1);
//        new UsuarioDAO(usuario).saveUsuario();

//        Lista lista = new Lista(-1,"me gustan las aceitunas", new UsuarioDAO(3),"con nocilla");
//        new ListaDAO(lista).save();


        Cancion cancion = new CancionDAO(5);
        cancion.setName("y se prendió");
        new CancionDAO(cancion).saveCancion();
        System.out.println(cancion);
        System.out.println(CancionDAO.getCancionesByList(3));

//        Lista lista = new ListaDAO(3);
//        lista.setName("subnormal");
//        new ListaDAO(lista).save();
    }
}
