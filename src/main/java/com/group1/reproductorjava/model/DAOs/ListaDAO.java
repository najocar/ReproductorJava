package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Cancion;
import com.group1.reproductorjava.model.Entity.Comentario;
import com.group1.reproductorjava.model.Entity.Lista;
import com.group1.reproductorjava.model.Entity.Usuario;
import com.group1.reproductorjava.model.interfaces.IListaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaDAO extends Lista implements IListaDAO {

    private final static String INSERT ="INSERT INTO lista (nombre,id_user,descripcion) VALUES(?,?,?)";
    private final static String UPDATE ="UPDATE lista SET nombre=?,descripcion=? WHERE id=?";
    private final static String DELETE="DELETE FROM lista WHERE id=?";
    private final static String SELECTBYID="SELECT id,nombre,id_user,description FROM lista WHERE id=?";
    private final static String SELECTALL="SELECT id,nombre,localizacion,jefe,area,id_sede FROM Complejo";
    private final static String SELECTBYCREADOR="SELECT id,nombre,id_user,description FROM artista WHERE id_user=?";

    public ListaDAO(int id, String nombre,String descripcion){
        super(id,nombre,descripcion);
    }
    public ListaDAO(int id){
        getLista(id);
    }
    public ListaDAO(Lista lista){
        super(lista.getId(), lista.getName(), lista.getDescription());
        this.canciones = lista.getCanciones();
        this.comentarios = lista.getComentarios();
        this.userCreator = lista.getUserCreator();
    }
    @Override
    public boolean getLista(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if(conn==null) return false;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1,id);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setDescription(rs.getString("descripcion"));
                        canciones(CancionDAO.getCancionByLista(this.getId()));
                        comentarios(ComentarioDAO.getComentarioByLista(this.getId()));
                        userCreator = new UsuarioDAO(rs.getUsuario("id_user"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean getLista(String name) {
        return false;
    }

    @Override
    public List<Lista> getAllListas() {
        Connection conn = MariaDBConnection.getConnection();
        if(conn==null) return null;
        List<Lista> result=new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Lista l = new Lista();
                        l.setId(rs.getInt("id"));
                        l.setName(rs.getString("nombre"));
                        l.setDescription(rs.getString("descripcion"));
                        l.setCanciones(CancionDAO.getCancionByLista(rs.getInt("id")));
                        l.setComentarios(ComentarioDAO.getComentarioByLista(rs.getInt("id")));
                        l.setUserCreator(new UsuarioDAO(rs.getUsuario("id_user")));

                        result.add(l);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public boolean save() {
        if (getId() != -1){
            return update();
        }else{
            Connection conn = MariaDBConnection.getConnection();
            if(conn == null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1,getName());
                ps.setInt(2,getUserCreator().getId());
                ps.setString(3,getDescription());

                if (ps.executeUpdate()==1){
                    try(ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
//                            if (canciones!=null){
//                                for(Cancion c:canciones){
//                                    CancionDAO c2 = new CancionDAO(c);
//                                    c2.lista=this;
//                                    c2.saveCancion();
//                                }
//                            }
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
                setId(-1);
                return false;

            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean update(){
        if(getId()==-1) return false;

        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,getName());
            ps.setString(3,getDescription());
            ps.setInt(3,getId());
            if (ps.executeUpdate()==1){
                return true;
            }
            setId(-1);
            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteLista(Lista Lista) {
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

    @Override
    public boolean addComment(Comentario coment) {
        if(!comentarios.contains(coment))
            return comentarios.add(coment);
        return false;
    }

    @Override
    public boolean deleteComment(Comentario coment) {
        if(comentarios.contains(coment))
            return comentarios.remove(coment);
        return false;
    }

    @Override
    public List<Cancion> getCanciones() {
        if (canciones==null){
            setCanciones(CancionDAO.getCancionByLista(getId()));
        }
        return super.getCanciones();
    }

    @Override
    public List<Comentario> getComentarios() {
        if (comentarios==null){
            setComentarios(//preguntar si debería haber un DAO para comentarios);
        }
        return super.getComentarios();
    }

    @Override
    public Usuario getUserCreator() {
        if (userCreator==null){
            setComentarios(UsuarioDAO.getUsuario(getId()));
        }
        return super.getUserCreator();
    }
}
