package com.group1.reproductorjava.model.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.interfaces.IArtistaDAO;

public class ArtistaDAO implements IArtistaDAO {

    private static ArtistaDAO instance;

    private final static String INSERT = "INSERT INTO artista (nombre, nacionalidad,foto) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE artista SET nombre=?, foto=?, nacionalidad=? WHERE id=?";
    private final static String DELETE = "DELETE FROM artista WHERE id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM artista WHERE id=?";
    private final static String SELECT_ALL = "SELECT * FROM artista";
    private final static String SELECT_BY_NAME = "SELECT * FROM artista WHERE nombre=?";


    private ArtistaDAO() {
        // Constructor privado para evitar la creación de instancias directas.
    }

    public static ArtistaDAO getInstance() {
        if (instance == null) {
            instance = new ArtistaDAO();
        }
        return instance;
    }

    public boolean saveArtista(Artista artista) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, artista.getName());
            ps.setString(2, artista.getNacionality());
            ps.setString(3, artista.getPhoto());

            if (ps.executeUpdate() == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        artista.setId(rs.getInt(1));
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateArtista(Artista artista) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, artista.getName());
            ps.setString(2, artista.getPhoto());
            ps.setString(3, artista.getNacionality());
            ps.setInt(4, artista.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteArtista(Artista artista) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, artista.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getArtista(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    return rs.next(); // Devuelve true si se encuentra un artista con el ID, de lo contrario, false.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean getArtista(String name) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME)) {
            ps.setString(1, name);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    return rs.next(); // Devuelve true si se encuentra un artista con el nombre, de lo contrario, false.
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<Artista> getAllArtists() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return null;

        List<Artista> artistas = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Artista artista = new Artista(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("nacionalidad"),
                                rs.getString("foto"),
                                null // Necesitas manejar la lista de discos apropiadamente
                        );
                        artistas.add(artista);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artistas;
    }
}
