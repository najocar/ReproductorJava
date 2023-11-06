package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.Entity.Disco;
import com.group1.reproductorjava.model.interfaces.IDiscoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscoDAO implements IDiscoDAO {
    private final static String INSERT = "INSERT INTO disco (nombre, artista_id, fecha) VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE disco SET nombre = ?, artista_id = ?, fecha = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM disco WHERE id = ?";
    private final static String SELECT_BY_ID = "SELECT id, nombre, artista_id, fecha FROM disco WHERE id = ?";
    private final static String SELECT_BY_NAME = "SELECT id, nombre, artista_id, fecha FROM disco WHERE nombre = ?";
    private final static String SELECT_ALL = "SELECT id, nombre, artista_id, fecha FROM disco";

    @Override
    public boolean getDisco(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Disco disco = new Disco();
                    disco.setId(rs.getInt("id"));
                    disco.setName(rs.getString("nombre"));

                    // Obtener un objeto Artista a partir del artista_id
                    int artistaId = rs.getInt("artista_id");
                    ArtistaDAO artistaDAO = new ArtistaDAO(); // Instancia de la implementación de IArtistaDAO
                    Artista artista = artistaDAO.getArtista(artistaId);

                    disco.setArtista(artista);
                    disco.setFecha(rs.getDate("fecha"));
                    return true;
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
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Disco disco = new Disco();
                    disco.setId(rs.getInt("id"));
                    disco.setName(rs.getString("nombre"));

                    // Obtener un objeto Artista a partir del artista_id
                    int artistaId = rs.getInt("artista_id");
                    ArtistaDAO artistaDAO = new ArtistaDAO(); // Instancia de la implementación de IArtistaDAO
                    Artista artista = artistaDAO.getArtista(artistaId);

                    disco.setArtista(artista);
                    disco.setFecha(rs.getDate("fecha"));
                    return true;
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
                    int artistaId = rs.getInt("artista_id");
                    ArtistaDAO artistaDAO = new ArtistaDAO(); // Instancia de la implementación de IArtistaDAO
                    Artista artista = artistaDAO.getArtista(artistaId);

                    disco.setArtista(artista);
                    disco.setFecha(rs.getDate("fecha"));
                    result.add(disco);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public boolean saveDisco(Disco disco) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setString(1, disco.getName());

            // Obtener el ID del artista desde el objeto Artista
            int artistaId = disco.getArtista().getId();
            ps.setInt(2, artistaId);

            ps.setDate(3, new java.sql.Date(disco.getFecha().getTime()));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteDisco(Disco disco) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, disco.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
