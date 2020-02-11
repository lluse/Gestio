package com.igema.main.dades.classes;

import com.igema.main.dades.conexionBD.Conexion;
import com.igema.main.domini.classes.Beca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DadesBeca {
    private static Connection con = Conexion.getConexio();

    /**
     * instancia de la propia clase seguint el patro singleton
     */
    private static DadesBeca instancia = new DadesBeca();

    private DadesBeca() {}

    public static DadesBeca getInstancia() {
        return instancia;
    }

    public Beca getBeca(int codi) throws SQLException {
        Beca b = new Beca();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT * FROM Beques WHERE Id = ?");
        ps.setInt(1, codi);

        ResultSet rs = ps.getResultSet();
        b.setCodiBeca(codi);
        b.setBeca(rs.getString("BECA"));
        b.setQuantitat(rs.getFloat("import"));
        b.setHores(rs.getInt("hores"));

        return b;
    }
}
