package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Cancion;
import com.group1.reproductorjava.model.Entity.Disco;
import com.group1.reproductorjava.model.Entity.Lista;
import com.group1.reproductorjava.model.interfaces.ICancionDAO;
import com.group1.reproductorjava.utils.LoggerClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancionDAO extends Cancion implements ICancionDAO {

    private final static String SELECTALL = "SELECT id, nombre, duracion, genero, n_reproducciones, id_disco FROM cancion";
    private final static String SELECTBYID = "SELECT id, nombre, duracion, genero, n_reproducciones, id_disco FROM cancion WHERE id = ?";
    private final static String SELECTBYNAME = "SELECT id, nombre, duracion, genero, n_reproducciones, id_disco FROM cancion WHERE nombre LIKE ?";
    private final static String SELECTBYGENDER = "SELECT id, nombre, duracion, genero, n_reproducciones, id_disco FROM cancion WHERE genero LIKE ?";
    private final static String INSERT = "INSERT INTO cancion (nombre, duracion, genero, n_reproducciones, id_disco) VALUES (?, ?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM cancion WHERE id = ?";
    private final static String SELECTSONGBYLIST = "SELECT c.id, c.nombre, c.duracion, c.genero, c.n_reproducciones, c.id_disco FROM cancion c JOIN cancion_lista cl ON c.id = cl.id_cancion WHERE cl.id_lista = ?";
    private final static String UPDATE = "UPDATE cancion SET nombre = ?, duracion = ?, genero = ?, n_reproducciones = ?, id_disco = ? WHERE id = ?";

    static LoggerClass logger = new LoggerClass(CancionDAO.class.getName());

    public CancionDAO(int id){
        getCancion(id);
    }

    public CancionDAO(Cancion song){
        super(song.getId(), song.getName(), song.getDuration(), song.getGender(), song.getnReproductions(), song.getDisco());
    }

    public CancionDAO(int id, String nombre, int duration, String gender, int nReproductions, Disco disco){
        super(id, nombre, duration, gender, nReproductions, disco);
    }

    /**
     * Method to get Cancion by id to cancion
     * @param id: int
     * @return boolean
     * If return true, success
     */
    @Override
    public boolean getCancion(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1, id);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setDuration(rs.getInt("duracion"));
                        setGender(rs.getString("genero"));
                        setnReproductions(rs.getInt("n_reproducciones"));
                        setDisco(new DiscoDAO(rs.getInt("id_disco")));
                        return true;
                    }
                }
            }

        }catch (SQLException e){
            logger.warning("Error to try get Cancion by id");
            logger.warning(e.getMessage());
            return false;
        }

        return false;
    }

    /**
     * Method to get Cancion by name to cancion
     * @param name: string
     * @return boolean
     * If return true, sucess
     */
    @Override
    public boolean getCancion(String name) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(SELECTBYNAME)){
            ps.setString(1, name);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setDuration(rs.getInt("duracion"));
                        setGender(rs.getString("genero"));
                        setnReproductions(rs.getInt("n_reproducciones"));
                        setDisco(new DiscoDAO(rs.getInt("id_disco")));
                        return true;
                    }
                }
            }

        }catch (SQLException e){
            logger.warning("Error to try get Cancion by name");
            logger.warning(e.getMessage());
            return false;
        }

        return false;
    }

    /**
     * Static Method to get all Cancion
     * @return List<Cancion> | null
     * if dont return null, success
     */
    public static List<Cancion> getAllCanciones() {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;

        List<Cancion> result = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){

            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){

                        Cancion song = new Cancion(
                          rs.getInt("id"),
                          rs.getString("nombre"),
                          rs.getInt("duracion"),
                          rs.getString("genero"),
                          rs.getInt("n_reproducciones"),
                          new DiscoDAO(rs.getInt("id_disco"))
                        );
                        result.add(song);
                    }
                }
            }

        }catch (SQLException e){
            logger.warning("Error to try get all Cancion");
            logger.warning(e.getMessage());
            return null;
        }

        return result;
    }

    /**
     * Method to save Cancion
     * @return boolean
     * If return true, success
     * If Cancion exist, update Cancion
     */
    @Override
    public boolean saveCancion() {
        if(getId() != -1){
            return updateCancion();
        }
        else{
            Connection conn = MariaDBConnection.getConnection();
            if(conn == null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT)){
                ps.setString(1, getName());
                ps.setInt(2, getDuration());
                ps.setString(3, getGender());
                ps.setInt(4, getnReproductions());
                ps.setInt(5, getDisco().getId());
                if(ps.executeUpdate() == 1) return true;
                return false;
            }catch (SQLException e){
                logger.warning("Error to try save Cancion");
                logger.warning(e.getMessage());
                return false;
            }
        }
    }

    /**
     * Method to remove Cancion
     * @return boolean
     * If return true, success
     */
    @Override
    public boolean deleteCancion() {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1, getId());

            if(ps.executeUpdate() == 1) return true;
            return false;

        }catch (SQLException e){
            logger.warning("Error to try delete Cancion");
            logger.warning(e.getMessage());
            return false;
        }
    }

    /**
     * Method to increase plus one the nReproducciones of a Cancion
     */
    @Override
    public void oneReproduction() {
        setnReproductions(getnReproductions()+1);
        saveCancion();
    }

    /**
     * Static Method to get all Cancion by gender
     * @param gender: string
     * @return List<Lista> | null
     * If dont return null, success
     */
    public static List<Cancion> selectByGender(String gender) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;

        List<Cancion> result = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(SELECTBYGENDER)){
            ps.setString(1, gender);

            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Cancion song = new Cancion(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getInt("duracion"),
                                rs.getString("genero"),
                                rs.getInt("n_reproducciones"),
                                new DiscoDAO(rs.getInt("id_disco"))
                        );
                        result.add(song);
                    }
                }
            }

        }catch (SQLException e){
            logger.warning("Error to try get all Cancion by gender");
            logger.warning(e.getMessage());
            return null;
        }

        return result;
    }

    /**
     * Method to get all Canciones on Disco
     * @param idList: int
     * @return List<Cancion> | null
     * if dont return null, success
     */
    public static List<Cancion> getCancionesByList(int idList){
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;
        List<Cancion> result = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(SELECTSONGBYLIST)){
            ps.setInt(1, idList);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Cancion song = new Cancion(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getInt("duracion"),
                                rs.getString("genero"),
                                rs.getInt("n_reproducciones"),
                                new DiscoDAO(rs.getInt("id_disco"))
                        );
                        result.add(song);
                    }
                }
            }

        }catch (SQLException e){
            logger.warning("Error to try get all Cancion by List");
            logger.warning(e.getMessage());
            return null;
        }

        return result;
    }

    /**
     * Method to update Cancion
     * @return boolean
     * true if success
     * params to update (nombre, duracion, genero, nReproducciones, id_disco)
     */
    public boolean updateCancion(){
        if(getId() == -1) return false;
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1, getName());
            ps.setInt(2, getDuration());
            ps.setString(3, getGender());
            ps.setInt(4, getnReproductions());
            ps.setInt(5, getDisco().getId());
            ps.setInt(6, getId());

            if(ps.executeUpdate() == 1) return true;
            return false;

        }catch (SQLException e){
            logger.warning("Error to try update Cancion");
            logger.warning(e.getMessage());
            return false;
        }
    }
}
