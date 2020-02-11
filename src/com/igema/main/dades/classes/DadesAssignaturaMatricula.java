package com.igema.main.dades.classes;

import com.igema.main.dades.conexionBD.Conexion;
import com.igema.main.domini.classes.AssignaturaMatricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;


public class DadesAssignaturaMatricula {
    private static Connection con = Conexion.getConexio();

    /**
     * instancia de la propia clase seguint el patro singleton
     */
    private static DadesAssignaturaMatricula instancia = new DadesAssignaturaMatricula();

    private DadesAssignaturaMatricula() {}

    public static DadesAssignaturaMatricula getInstancia() {
        return instancia;
    }

    public HashSet<AssignaturaMatricula> getAssignaturesMatricula(Integer numMat) throws SQLException {
        HashSet<AssignaturaMatricula> assigsMat = new HashSet<>();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT * FROM AssignaturesMatricula WHERE IDMatricula =  ?");
        ps.setInt(1, numMat);
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                AssignaturaMatricula am = new AssignaturaMatricula();
                Integer codiAssig = rs.getInt("IdAssignatura");
                am.setVegada(rs.getInt("VEGADA"));
                am.setReconeguda(rs.getBoolean("RECONEGUDA"));
                am.setConvalidada(rs.getBoolean("CONVALIDADA"));
                am.setNota(rs.getDouble("nota"));
                am.setQualificacio(rs.getString("qualificacio"));
                am.setBaixa(rs.getDate("baja"));
                am.setAssignatura(DadesAssignatura.getInstancia().getAssignatura(codiAssig));
                assigsMat.add(am);
            }
        } else {
            System.out.println("No s'ha establert connexio amb la base de dades");
        }
        return assigsMat;
    }

    public HashMap<Integer, String> getAssignaturesMatriculaPerAssigICurs(int codiAssig, String curs) throws SQLException {
        HashMap<Integer, String> assigsMat = new HashMap<>();
        String sql = "SELECT am.IdAssignaturaMatricula, am.VEGADA, am.nota, am.qualificacio, e.NOMBRE, e.APELLIDOS, e.DNI" +
                " FROM AssignaturesMatricula am, Matricules m, Estudiants e " +
                " WHERE am.IDMatricula = m.nummat AND m.NIF = e.DNI" +
                " AND am.IdAssignatura = " + codiAssig + " AND m.PeriodeAcademic = '" + curs + "' " +
                " ORDER BY e.NOMBRE";

        PreparedStatement ps;
        ps = con.prepareCall("SELECT am.IdAssignaturaMatricula, am.VEGADA, am.nota, am.qualificacio, e.NOMBRE," +
                "e.APELLIDOS, e.DNI FROM AssignaturesMatricula am, Matricules m, Estudiants e " +
                "WHERE am.IDMatricula = m.nummat AND m.NIF = e.DNI AND am.IdAssignatura = ? AND" +
                " m.PeriodeAcademic = ? ORDER BY e.NOMBRE");
        ps.setInt(1, codiAssig);
        ps.setString(2, curs);
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String result = rs.getInt(2) + "/" + rs.getDouble(3) + "/" +
                        rs.getString(4) + "/" + rs.getString(5) + "/" +
                        rs.getString(6) + "/" + rs.getString(7);
                assigsMat.put(id, result);
            }
        } else {
            System.out.println("No s'ha establert connexio amb la base de dades");
        }
        return assigsMat;
    }
}
