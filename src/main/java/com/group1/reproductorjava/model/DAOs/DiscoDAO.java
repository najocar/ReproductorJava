package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.Entity.Cancion;
import com.group1.reproductorjava.model.Entity.Disco;
import com.group1.reproductorjava.model.interfaces.IDiscoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscoDAO extends Disco implements IDiscoDAO {
    private final static String INSERT = "INSERT INTO disco (nombre, id_artista, fecha, foto) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE disco SET nombre = ?, id_artista = ?, fecha = ?, foto = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM disco WHERE id = ?";
    private final static String SELECT_BY_ID = "SELECT id, nombre, id_artista, fecha, foto FROM disco WHERE id = ?";
    private final static String SELECT_BY_NAME = "SELECT id, nombre, id_artista, fecha, foto FROM disco WHERE nombre = ?";
    private final static String SELECT_ALL = "SELECT id, nombre, id_artista, fecha, foto FROM disco";
    private final static String SELECT_BY_ARTISTA = "SELECT id, nombre, id_artista, foto, fecha FROM disco WHERE id_artista = ?";

    public DiscoDAO(int id, String nombre, LocalDate fecha, String photo, Artista artista, List<Cancion> canciones){

        super(id, nombre, fecha, photo, artista, canciones);
    }
    public DiscoDAO(int id){
        getDisco(id);
    }
    public DiscoDAO(Disco d){
        super(d.getId(), d.getName(), d.getFecha(),
                d.getPhoto(), d.getArtista(), d.getCanciones()
        );
    }

    @Override
    public boolean getDisco(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        Disco disco = new Disco();
                        disco.setId(rs.getInt("id"));
                        disco.setName(rs.getString("nombre"));

                        // Obtener un objeto Artista a partir del artista_id
                        int artistaId = rs.getInt("id_artista");
                        ArtistaDAO artistaDAO = new ArtistaDAO(); // Instancia de la implementación de IArtistaDAO

                        // Intenta obtener el objeto Artista
                        if (artistaDAO.getArtista(artistaId)) {
                            Artista artista = new Artista();
                            // Asigna las propiedades del artista aquí
                            disco.setArtista(artista);
                            disco.setFecha(rs.getDate("fecha").toLocalDate());
                            disco.setPhoto(rs.getString("foto"));
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean getDisco(String name) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME)) {
            ps.setString(1, name);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        Disco disco = new Disco();
                        disco.setId(rs.getInt("id"));
                        disco.setName(rs.getString("nombre"));

                        // Obtener un objeto Artista a partir del artista_id
                        int artistaId = rs.getInt("id_artista");
                        ArtistaDAO artistaDAO = new ArtistaDAO(); // Instancia de la implementación de IArtistaDAO

                        // Intenta obtener el objeto Artista
                        if (artistaDAO.getArtista(artistaId)) {
                            Artista artista = new Artista();
                            // Asigna las propiedades del artista aquí
                            disco.setArtista(artista);
                            disco.setFecha(rs.getDate("fecha").toLocalDate());
                            disco.setPhoto(rs.getString("foto"));
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Disco> getAllDiscos() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return null;
        List<Disco> result = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Disco disco = new Disco();
                    disco.setId(rs.getInt("id"));
                    disco.setName(rs.getString("nombre"));

                    // Obtener un objeto Artista a partir del artista_id
                    int artistaId = rs.getInt("id_artista");
                    ArtistaDAO artistaDAO = new ArtistaDAO(); // Instancia de la implementación de IArtistaDAO

                    // Intenta obtener el objeto Artista
                    if (artistaDAO.getArtista(artistaId)) {
                        Artista artista = new Artista();
                        // Asigna las propiedades del artista aquí
                        disco.setArtista(artista);
                        disco.setFecha(rs.getDate("fecha").toLocalDate());
                        disco.setPhoto(rs.getString("foto"));
                        result.add(disco);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean saveDisco() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try {
            // Verificar si el disco ya existe en la base de datos
            if (getDisco(getName())) {
                // El disco existe, realizar una actualización
                try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
                    ps.setString(1, getName());
                    int artistaId = getArtista().getId();
                    ps.setInt(2, artistaId);
                    ps.setDate(3, java.sql.Date.valueOf(getFecha()));
                    ps.setString(4, getName());  // Usar el nombre para identificar el disco a actualizar
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        return true;
                    }
                }
            } else {
                // El disco no existe, realizar una inserción
                try (PreparedStatement ps = conn.prepareStatement(INSERT)) {
                    ps.setString(1, getName());
                    int artistaId = getArtista().getId();
                    ps.setInt(2, artistaId);
                    ps.setDate(3, java.sql.Date.valueOf(getFecha()));
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDisco() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Disco> getAllDiscosByArtista(Artista a) {
        List<Disco> result =new ArrayList<Disco>();
        Connection conn = MariaDBConnection.getConnection();
        if(conn !=null){
            PreparedStatement ps;
            try{
                ps= conn.prepareStatement(SELECT_BY_ARTISTA);
                ps.setInt(1,a.getId());
                if(ps.execute()){
                    ResultSet rs =ps.getResultSet();
                    while (rs.next()){
                        Disco d = new Disco();
                        rs.getInt("id");
                        rs.getString("nombre");
                        d.setArtista(new Artista(rs.getInt("id_artista")));
                        rs.getDate("fecha");
                        rs.getString("foto");
                        result.add(d);
                    }
                    rs.close();
                }
                ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return  result;
    }
}
