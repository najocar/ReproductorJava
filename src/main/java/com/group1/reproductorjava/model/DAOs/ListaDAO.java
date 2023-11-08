package com.group1.reproductorjava.model.DAOs;

import com.group1.reproductorjava.model.Connection.MariaDBConnection;
import com.group1.reproductorjava.model.Entity.Cancion;
import com.group1.reproductorjava.model.Entity.Comentario;
import com.group1.reproductorjava.model.Entity.Lista;
import com.group1.reproductorjava.model.Entity.Usuario;
import com.group1.reproductorjava.model.interfaces.IListaDAO;
import com.group1.reproductorjava.utils.LoggerClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaDAO extends Lista implements IListaDAO {

    private final static String INSERT = "INSERT INTO lista (nombre,id_user,descripcion) VALUES(?,?,?)";
    private final static String UPDATE = "UPDATE lista SET nombre=?,descripcion=? WHERE id=?";
    private final static String DELETE = "DELETE FROM lista WHERE id=?";
    private final static String SELECTBYID = "SELECT id,nombre,id_user,descripcion FROM lista WHERE id=?";
    private final static String SELECTALL = "SELECT id,nombre,id_user,descripcion FROM lista";
    private final static String SELECTBYCREADOR = "SELECT id,nombre,id_user,descripcion FROM user WHERE id_user=?";
    private final static String SAVESONGS = "INSERT INTO cancion_lista (id_lista, id_cancion) VALUES(?,?)";
    private final static String DELETESONGS = "DELETE FROM cancion_lista WHERE id=?";

    static LoggerClass logger = new LoggerClass(ListaDAO.class.getName());

    public ListaDAO(int id, String nombre, Usuario userCreator, String descripcion) {
        super(id, nombre, userCreator, descripcion);
    }

    public ListaDAO(int id) {
        getLista(id);
    }

    public ListaDAO(Lista lista) {
        super(lista.getId(), lista.getName(), lista.getUserCreator(), lista.getDescription());
        this.canciones = lista.getCanciones();
        this.comentarios = lista.getComentarios();
        this.userCreator = lista.getUserCreator();
    }

    @Override
    public boolean getLista(int id) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYID)) {
            ps.setInt(1, id);
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    if (rs.next()) {
                        setId(rs.getInt("id"));
                        setName(rs.getString("nombre"));
                        setDescription(rs.getString("descripcion"));
                        setCanciones(CancionDAO.getCancionesByList(this.getId()));
                        setComentarios(ComentarioDAO.getComentariosByLista(this.getId()));
                        setUserCreator(new UsuarioDAO(rs.getInt("id_user")));
                    }
                }
            }

        } catch (SQLException e) {
            logger.warning("Error to try get Lista by id");
            logger.warning(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean getLista(String name) {
        return false;
    }

    public static List<Lista> getAllListas() {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return null;
        List<Lista> result = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTALL)) {
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Lista l = new Lista();
                        l.setId(rs.getInt("id"));
                        l.setName(rs.getString("nombre"));
                        l.setDescription(rs.getString("descripcion"));
                        l.setCanciones(CancionDAO.getCancionesByList(rs.getInt("id")));
                        l.setComentarios(ComentarioDAO.getComentariosByLista(rs.getInt("id")));
                        l.setUserCreator(new UsuarioDAO(rs.getInt("id_user")));

                        result.add(l);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warning("Error to try get all Lista");
            logger.warning(e.getMessage());
            return null;
        }
        return result;
    }

    @Override
    public boolean save() {
        if (getId() != -1) {
            return update();
        } else {
            Connection conn = MariaDBConnection.getConnection();
            if (conn == null) return false;

            try (PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, getName());
                ps.setInt(2, getUserCreator().getId());
                ps.setString(3, getDescription());

                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
                            if (canciones != null) {
                                for (Cancion c : canciones) {
                                    CancionDAO c2 = new CancionDAO(c);
                                    //c2.lista = this;
                                    //c2.saveCancion();
                                    if (!c2.getCancionesByList(this.getId()).contains(c)){
                                        //c2.get
                                        saveSongRelation(c);
                                    }
                                }
                            }

                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                setId(-1);
                return false;

            } catch (SQLException e) {
                logger.warning("Error to try save Lista");
                logger.warning(e.getMessage());
                return false;
            }
        }
    }

    public boolean saveSongRelation(Cancion song) throws SQLException {
        if (canciones==null) getCanciones();
        canciones.add(song);
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(SAVESONGS)) {
            ps.setInt(1, this.getId());
            ps.setInt(2, song.getId());

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean deleteSongRelation(Cancion c) {
        canciones.remove(c);
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(DELETESONGS)) {
            ps.setInt(1, c.getId());

            if (ps.executeUpdate() == 1) return true;
            return false;

        } catch (SQLException e) {
            logger.warning("Error to try delete Cancion on Lista");
            logger.warning(e.getMessage());
            return false;
        }
    }

    public boolean update() {
        if (getId() == -1) return false;

        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, getName());
            ps.setString(2, getDescription());
            ps.setInt(3, getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
            setId(-1);
            return false;

        } catch (SQLException e) {
            logger.warning("Error to try update Lista");
            logger.warning(e.getMessage());
            return false;
        }
    }

    public static List<Lista> getListByUser(Usuario user) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return null;
        List<com.group1.reproductorjava.model.Entity.Lista> result = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECTBYCREADOR)) {
            ps.setInt(1, user.getId());
            if (ps.execute()) {
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        com.group1.reproductorjava.model.Entity.Lista l = new com.group1.reproductorjava.model.Entity.Lista();
                        l.setId(rs.getInt("id"));
                        l.setName(rs.getString("nombre"));
                        l.setDescription(rs.getString("descripcion"));
                        l.setCanciones(CancionDAO.getCancionesByList(rs.getInt("id")));
                        l.setComentarios(ComentarioDAO.getComentariosByLista(rs.getInt("id")));
                        l.setUserCreator(new UsuarioDAO(rs.getInt("id_user")));

                        result.add(l);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warning("Error to try get Lista by User");
            logger.warning(e.getMessage());
            return null;
        }
        return result;
    }

    @Override
    public boolean deleteLista(Lista Lista) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, getId());

            if (ps.executeUpdate() == 1) return true;
            return false;

        } catch (SQLException e) {
            logger.warning("Error to try delete Lista");
            logger.warning(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addComment(Comentario coment) {
        if (!comentarios.contains(coment))
            return comentarios.add(coment);
        return false;
    }

    @Override
    public boolean deleteComment(Comentario coment) {
        if (comentarios.contains(coment))
            return comentarios.remove(coment);
        return false;
    }


    public boolean addCancion(Cancion cancion) {
        if (!canciones.contains(cancion))
            return canciones.add(cancion);
        return false;
    }

//    @Override
//    public boolean deleteComment(Comentario coment) {
//        if (comentarios.contains(coment))
//            return comentarios.remove(coment);
//        return false;
//    }

    @Override
    public List<Cancion> getCanciones() {
        if (canciones == null) {
            setCanciones(CancionDAO.getCancionesByList(getId()));
        }
        return super.getCanciones();
    }

    @Override
    public List<Comentario> getComentarios() {
        if (comentarios == null) {
            setComentarios(ComentarioDAO.getComentariosByLista(getId()));
        }
        return super.getComentarios();
    }

//    @Override
//    public Usuario getUserCreator() {
//        if (userCreator == null) {
//            setUserCreator(UsuarioDAO.getUsuario(getId()));
//        }
//        return super.getUserCreator();
//    }
}
