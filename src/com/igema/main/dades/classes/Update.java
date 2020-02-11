package com.igema.main.dades.classes;

import com.igema.main.dades.conexionBD.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {
    private static Connection con = Conexion.getConexio();

    private static Update instancia = new Update();

    private Update() {}

    public static Update getInstancia() { return instancia; }

    public int executeQuery(String sql) {
        if (con != null) {
            try (Statement st = con.createStatement()) {
                return st.executeUpdate(sql);
            } catch (SQLException s) {
                s.printStackTrace();
            }
        } else {
            System.out.println("No s'ha establert connexió amb la base de dades");
        }
        return 0;
    }
}
