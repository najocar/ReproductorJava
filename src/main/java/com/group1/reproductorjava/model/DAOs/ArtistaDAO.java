package com.group1.reproductorjava.model.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.Entity.Disco;
import com.group1.reproductorjava.model.DAOs.DiscoDAO;
import com.group1.reproductorjava.model.interfaces.IArtistaDAO;

public class ArtistaDAO extends Artista implements IArtistaDAO {


    private final static String INSERT = "INSERT INTO artista (nombre, nacionalidad,foto) VALUES(?, ?, ?)";
    private final static String UPDATE = "UPDATE artista SET nombre=?, foto=?, nacionalidad=? WHERE id=?";
    private final static String DELETE = "DELETE FROM artista WHERE id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM artista WHERE id=?";
    private final static String SELECT_ALL = "SELECT * FROM artista";
    private final static String SELECT_BY_NAME = "SELECT * FROM artista WHERE nombre=?";

    public ArtistaDAO(int id) {
        getArtista(id);
    }

    public ArtistaDAO(Artista a){
        super(a.getId(),a.getName(),a.getPhoto(),a.getNacionality());
        this.discos=a.getDiscos();
    }

    public ArtistaDAO(int id, String name, String photo, String nacionality){
        super(id,name,photo,nacionality);
    }

    public ArtistaDAO() {

    }

    public boolean saveArtista() {
        if (getId() != -1) {
            return updateArtista();
        }else {
            Connection conn = MariaDBConnection.getConnection();
            if (conn == null)
                return false;

            try (PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, getName());
                ps.setString(2, getNacionality());
                ps.setString(3, getPhoto());

                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
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
    }

    public boolean updateArtista() {
        if(getId()==-1) return false;
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return false;
        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, getName());
            ps.setString(2, getPhoto());
            ps.setString(3, getNacionality());
            ps.setInt(4, getId());
            if(ps.executeUpdate()==1) return true;
            setId(-1);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteArtista() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null)
            return false;

        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, getId());

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
                   if(rs.next()){
                       setId(rs.getInt("id"));
                       setName(rs.getString("nombre"));
                       setNacionality(rs.getString("nacionalidad"));
                       setPhoto(rs.getString("foto"));
                   }
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
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setNacionality(rs.getString("nacionalidad"));
                        setPhoto(rs.getString("foto"));
                    }
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
                                rs.getString("foto")
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

   public List<Disco>getDiscos(){
        if(super.getDiscos()==null){
            setDiscos(DiscoDAO.getAllDiscosByArtista(this));
        }
        return super.getDiscos();
   }


}
