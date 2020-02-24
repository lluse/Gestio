package com.igema.main.dades.conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Connection con;
    private static final String DEFAULT_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DEFAULT_URL = "jdbc:sqlserver://localhost:1433;databaseName=Gestio";
    private static final String DEFAULT_USERNAME = "aleix";
    private static final String DEFAULT_PASSWORD = "Igema2020";

    public Conexion() {
        con = null;
        try {
            Class.forName(DEFAULT_DRIVER);
            con = DriverManager.getConnection(DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConexio() {
        if (con == null) {
            new Conexion();
        }
        return con;
    }
}
