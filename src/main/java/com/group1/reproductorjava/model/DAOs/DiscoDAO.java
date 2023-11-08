package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Artista;
import com.group1.reproductorjava.model.Entity.Disco;
import com.group1.reproductorjava.model.interfaces.IDiscoDAO;
import com.group1.reproductorjava.utils.LoggerClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiscoDAO extends Disco implements IDiscoDAO {
    private final static String INSERT = "INSERT INTO disco (nombre, id_artista, fecha, foto) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE disco SET nombre = ?, id_artista = ?, fecha = ?, foto = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM disco WHERE id = ?";
    private final static String SELECT_BY_ID = "SELECT id, nombre, id_artista, fecha, foto FROM disco WHERE id = ?";
    private final static String SELECT_BY_NAME = "SELECT id, nombre, id_artista, fecha, foto FROM disco WHERE nombre = ?";
    private final static String SELECT_ALL = "SELECT id, nombre, id_artista, fecha, foto FROM disco";
    private final static String SELECT_BY_ARTISTA = "SELECT id, nombre, id_artista, foto, fecha FROM disco WHERE id_artista = ?";

    static LoggerClass logger = new LoggerClass(DiscoDAO.class.getName());

    public DiscoDAO(int id, String nombre, LocalDate fecha, String photo, Artista artista){

        super(id, nombre, fecha, photo, artista);
    }
    public DiscoDAO(int id){
        getDisco(id);
    }
    public DiscoDAO(Disco d){
        super(d.getId(), d.getName(), d.getFecha(), d.getPhoto(), d.getArtista());
    }

    /**
     * Method to get Disco by id
     * @param id: int
     * @return boolean
     * true if success
     */
    @Override
    public boolean getDisco(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);

            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setFecha(rs.getDate("fecha").toLocalDate());
                        setPhoto(rs.getString("foto"));
                        setArtista(new ArtistaDAO(rs.getInt("id_artista")));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.warning("Error to try get Disco by id");
            logger.warning(e.getMessage());
            return false;
        }
        return false;
    }


    /**
     * Method to get Disco by name
     * @param name: string
     * @return boolean
     * true if success
     */
    @Override
    public boolean getDisco(String name) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME)) {
            ps.setString(1, name);

            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setFecha(rs.getDate("fecha").toLocalDate());
                        setPhoto(rs.getString("foto"));
                        setArtista(new ArtistaDAO(rs.getInt("id_artista")));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.warning("Error to try get Disco by name");
            logger.warning(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Method to get all Disco
     * @return List<Disco> | null
     * if dont return null, success
     */
    @Override
    public List<Disco> getAllDiscos() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return null;
        List<Disco> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {

            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while(rs.next()){
                        Disco aux = new Disco(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getDate("fecha").toLocalDate(),
                                rs.getString("foto"),
                                new ArtistaDAO(rs.getInt("id_artista"))
                        );
                        result.add(aux);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warning("Error to try get all Disco");
            logger.warning(e.getMessage());
            return null;
        }
        return result;
    }

    /**
     * Method to save Disco
     * @return boolean
     * true if success
     */
    @Override
    public boolean saveDisco() {
        if(getId() != -1) return updateDisco();

        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(INSERT)){
            ps.setString(1, getName());
            ps.setInt(2, getArtista().getId());
            ps.setDate(3, java.sql.Date.valueOf(getFecha()));
            ps.setString(4, getPhoto());

            if(ps.executeUpdate() == 1) return true;
            return false;

        }catch (SQLException e){
            logger.warning("Error to try save Disco");
            logger.warning(e.getMessage());
            return false;
        }
    }

    /**
     * Method to delete Disco
     * @return boolean
     * true if success
     */
    @Override
    public boolean deleteDisco() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, getId());
            if(ps.executeUpdate() == 1)return true;
            return false;
        } catch (SQLException e) {
            logger.warning("Error to try delete Disco");
            logger.warning(e.getMessage());
            return false;
        }
    }

    /**
     * Method to get all Disco by Artista owner
     * @param artista: Artista
     * @return List<Disco> | null
     * if dont return null, success
     */
    public static List<Disco> getAllDiscosByArtista(Artista artista) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;
        List<Disco> result = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(SELECT_BY_ARTISTA)){
            ps.setInt(1, artista.getId());

            if(ps.execute()){

                try(ResultSet rs = ps.getResultSet()){

                    while (rs.next()){
                        Disco d = new Disco(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("foto"),
                            new Artista(rs.getInt("id_artista"))
                        );
                        result.add(d);
                    }
                }
            }

        }catch (SQLException e){
            logger.warning("Error to try get all Disco by Artista");
            logger.warning(e.getMessage());
            return null;
        }

        return result;
    }

    /**
     * Method to update Disco
     * @return boolean
     * true if success
     * if Disco dont exist return false
     * Params to update ( nombre, Artista, fecha, foto)
     */
    public boolean updateDisco(){
        if(getId() == -1) return false;
        Connection conn = MariaDBConnection.getConnection();
        if(conn != null) return false;
        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1, getName());
            ps.setInt(2, getArtista().getId());
            ps.setDate(3, java.sql.Date.valueOf(getFecha()));
            ps.setString(4, getPhoto());
            if(ps.executeUpdate() == 1) return true;
            return false;
        }catch (SQLException e){
            logger.warning("Error to try update Disco");
            logger.warning(e.getMessage());
            return false;
        }
    }
}
