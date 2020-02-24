package com.igema.main.dades.classes;

import com.igema.main.dades.conexionBD.Conexion;
import com.igema.main.domini.classes.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class DadesMatricula {
    private static Connection con = Conexion.getConexio();

    /**
     * instancia de la propia clase seguint el patro singleton
     */
    private static DadesMatricula instancia = new DadesMatricula();

    private DadesMatricula() {}

    public static DadesMatricula getInstancia() {
        return instancia;
    }

    public HashSet<Matricula> getMatriculesEstudiant(String dni) throws SQLException {
        HashSet<Matricula> matricules = new HashSet<>();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT * FROM Matricules WHERE NIF = ?");
        ps.setString(1, dni);
        if (con != null) {
            //st.setString(1, dni);
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Matricula m = new Matricula();
                Integer numMat = rs.getInt("nummat");
                m.setNumMat(numMat);
                m.setPeriodeAcademic(rs.getString("PeriodeAcademic"));
                m.setNumURV(rs.getInt(4));
                m.setDataMat(rs.getDate("DataMatricula"));
                m.setCurs(rs.getString(9));
                m.setHorari(rs.getString(10));
                m.setAssignaturaMatricula(DadesAssignaturaMatricula.getInstancia().getAssignaturesMatricula(numMat));
                matricules.add(m);
            }
        } else {
            System.out.println("No s'ha establert connexió amb la base de dades");
        }
        return matricules;
    }
}
