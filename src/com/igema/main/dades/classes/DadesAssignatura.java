package com.igema.main.dades.classes;

import com.igema.main.dades.conexionBD.Conexion;
import com.igema.main.domini.classes.Assignatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DadesAssignatura {

    private static Connection con = Conexion.getConexio();

    /**
     * instancia de la propia clase seguint el patro singleton
     */
    private static DadesAssignatura instancia = new DadesAssignatura();

    private DadesAssignatura() {}

    public static DadesAssignatura getInstancia() {
        return instancia;
    }

    public List<Assignatura> getAllAssignatures() throws SQLException {
        List<Assignatura> assignatures = new ArrayList<>();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT * FROM Assignatures");
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Assignatura a = new Assignatura();
                a.setCodi(rs.getInt("Codi"));
                a.setAssignatura(rs.getString("Assignatura"));
                a.setCurs(rs.getInt("Curs"));
                a.setCredits(rs.getInt("ECTS"));
                a.setSemestre(rs.getInt("SEMESTRE"));
                a.setTipologia(rs.getString("Tipologia"));
                a.setMateria(rs.getString("Materia"));
                assignatures.add(a);
            }
        }
        else {
            System.out.println("No s'ha establert conexio amb la BD");
        }
        return assignatures;
    }

    public Assignatura getAssignatura(Integer codi) throws SQLException {
        Assignatura a = new Assignatura();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT * FROM Assignatures WHERE Codi = ?");
        ps.setInt(1, codi);
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                a.setCodi(codi);
                a.setAssignatura(rs.getString("Assignatura"));
                a.setCurs(rs.getInt("Curs"));
                a.setCredits(rs.getInt("ECTS"));
                a.setSemestre(rs.getInt("SEMESTRE"));
                a.setTipologia(rs.getString("Tipologia"));
                a.setMateria(rs.getString("Materia"));
            }
        } else {
            System.out.println("No s'ha establert conexio amb la BD");
        }
        return a;
    }

    public ArrayList<String> importarAssignatura() throws SQLException {
        ArrayList<String> assigs = new ArrayList<>();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT Codi, Assignatura, SEMESTRE FROM Assignatures");
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String s = rs.getInt("Codi") + "/" + rs.getString("Assignatura")
                        + "/" + rs.getInt("SEMESTRE");
                assigs.add(s);
            }
        } else {
            System.out.println("No s'ha establert conexio amb la BD");
        }
        return assigs;
    }
}

