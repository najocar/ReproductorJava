package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Comentario;
import com.group1.reproductorjava.model.Entity.Lista;
import com.group1.reproductorjava.model.Entity.Usuario;
import com.group1.reproductorjava.model.interfaces.IComentarioDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO extends Comentario implements IComentarioDAO {

    private final static String SELECTBYID = "SELECT id, fecha, mensaje, id_user, id_lista FROM comentario WHERE id = ?";
    private final static String SELECTALL = "SELECT id, fecha, mensaje, id_user, id_lista FROM comentario";
    private final static String SELECTBYOWNER = "SELECT id, fecha, mensaje, id_user, id_lista FROM comentario WHERE id_usuario = ?";
    private final static String SELECTBYLIST = "SELECT id, fecha, mensaje, id_user, id_lista FROM comentario WHERE id_lista = ?";
    private final static String INSERT = "INSERT INTO comentario (fecha, mensaje, id_user, id_lista) VALUES (?, ?, ?, ?) ";
    private final static String DELETE = "DELETE FROM comentario WHERE id = ?";

    @Override
    public boolean getComentario(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1, id);

            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setDate(rs.getDate("fecha").toLocalDate());
                        setMessage(rs.getString("mensaje"));
                        setUsuario(new UsuarioDAO(rs.getInt("id_user")));
                        setLista(new ListaDAO(rs.getInt("id_lista")));
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

    public static List<Comentario> getAllComentarios(){
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;
        List<Comentario> result = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){

            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Comentario aux = new Comentario(
                                rs.getInt("id"),
                                rs.getDate("fecha").toLocalDate(),
                                rs.getString("mensaje"),
                                new Usuario(rs.getInt("id_user")),
                                new Lista(rs.getInt("id_lista"))
                        );
                        result.add(aux);
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    public boolean saveComentario() {
        if(getId() != -1) return false;
        else{
            Connection conn = MariaDBConnection.getConnection();
            if(conn == null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT)){
                ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                ps.setString(2, getMessage());
                ps.setInt(3, getUsuario().getId());
                ps.setInt(4, getLista().getId());

                if(ps.executeUpdate() == 1) return true;
                return false;

            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }

    }

    @Override
    public boolean deleteComentario() {
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1, getId());

            if(ps.executeUpdate() == 1) return true;
            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
