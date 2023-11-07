package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Lista;
import com.group1.reproductorjava.model.Entity.Usuario;
import com.group1.reproductorjava.model.interfaces.IUsuarioDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends Usuario implements IUsuarioDAO {

    private final static String SELECTALL = "SELECT id, nombre, correo, foto, rol FROM user";
    private final static String SELECTBYID = "SELECT id, nombre, correo, foto, rol FROM user WHERE id = ?";
    private final static String SELECTBYNAME = "SELECT id, nombre, correo, foto, rol FROM user WHERE nombre LIKE ?";
    private final static String INSERT = "INSERT INTO user (nombre, correo, foto, rol) VALUES (?,?,?,?)";
    private final static String DELETE = "DELETE FROM user WHERE id = ?";
    private final static String SELECTLISTBYUSEROWNER = "SELECT id, nombre, id_user, description FROM lista WHERE id_user = ?";
    private final static String UPDATE = "UPDATE user SET nombre = ?, correo = ?, foto = ? WHERE id = ?";

    public UsuarioDAO(int id){
        getUsuario(id);
    }

    public UsuarioDAO(int id, String nombre, String email, String photo, int rol){
        super(id, nombre, email, photo, rol);
    }

    public UsuarioDAO(Usuario user){
        super(user.getId(), user.getName(), user.getEmail(), user.getPhoto(), user.getRol());
    }

    /**
     * Method to get User by id to user
     * @param id: int
     * @return boolean
     * true if success
     */
    @Override
    public boolean getUsuario(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1, id);

            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setEmail(rs.getString("correo"));
                        setPhoto(rs.getString("foto"));
                        setRol(rs.getInt("rol"));
                        return true;
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Method to get User by name to user
     * @param name: string
     * @return boolean
     * true if success
     */
    @Override
    public boolean getUsuario(String name) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(SELECTBYNAME)){
            ps.setString(1, name);

            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setEmail(rs.getString("correo"));
                        setPhoto(rs.getString("foto"));
                        setRol(rs.getInt("rol"));
                        return true;
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Static method to get all Users
     * @return List<Usuario> | null
     * if dont return null, success
     */
    public static List<Usuario> getAllUsuarios() {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;

        List<Usuario> result = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            if(ps.execute()){

                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Usuario user = new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("foto"),
                            rs.getInt("rol")
                        );
                        result.add(user);
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * Method to save a user
     * @return boolean
     * If return true success
     * If User exist, update User
     */
    @Override
    public boolean saveUsuario() {
        if(getId() != -1){
            return updateUsuario();
        }
        else{
            Connection conn = MariaDBConnection.getConnection();
            if(conn == null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT)){
                ps.setString(1, getName());
                ps.setString(2, getEmail());
                ps.setString(3, getPhoto());
                ps.setInt(4, getRol());

                if(ps.executeUpdate() == 1) return true;
                return false;
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Method to remove User
     * @return boolean
     * If return true, success
     */
    @Override
    public boolean deleteUsuario() {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1, getId());
            if(ps.executeUpdate()==1) return true;
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to get all Lista of a owner user
     * @return List<Lista> | null
     * If dont return null, success
     */
    public List<Lista> getLista(){
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;
        List<Lista> result = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(SELECTLISTBYUSEROWNER)){
            ps.setInt(1, getId());

            if (ps.execute()) {
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Lista list = new Lista(
                                rs.getInt("id"),
                                rs.getString("name"),
                                getUsuario(rs.getInt("id_user")),
                                rs.getString("descripcion")
                        );
                        result.add(list);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return result;
    }

    /**
     * Method to update Usuario
     * @return boolean
     * true if success
     * params to update (nombre, correo, foto)
     */
    public boolean updateUsuario(){
        if(getId() == 1) return false;
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1, getName());
            ps.setString(2, getEmail());
            ps.setString(3, getPhoto());
            if(ps.executeUpdate() == 1) return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
