package com.group1.reproductorjava.model.Connection;

import com.group1.reproductorjava.utils.LoggerClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MariaDBConnection {
    private static Connection conn = null;
    //read config from xml file
    //alpha version
    private final static String uri="jdbc:mariadb://localhost:3306/reproductor_java";
    private final static String user = "root";
    private final static String password = "";

    static LoggerClass logger = new LoggerClass(MariaDBConnection.class.getName());

    private MariaDBConnection(){}

    public static Connection getConnection(){
        if(conn==null){
            try {
                conn = DriverManager.getConnection(uri, user, password);
            } catch (SQLException e) {
                conn = null;
                logger.warning("Error to try connect with db");
                logger.warning(e.getMessage());
            }
        }
        return conn;
    }

    public static void close(){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warning("Error to try close connection with db");
                logger.warning(e.getMessage());
            } finally {
                conn = null;
            }
        }
    }
}
