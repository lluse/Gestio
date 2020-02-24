package com.igema.main.dades.classes;

import com.igema.main.dades.conexionBD.Conexion;
import com.igema.main.domini.classes.Estudiant;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DadesEstudiant {
    private static Connection con = Conexion.getConexio();

    /**
     * instancia de la propia clase seguint el patro singleton
     */
    private static DadesEstudiant instancia = new DadesEstudiant();

    private DadesEstudiant() {}

    public static DadesEstudiant getInstancia() {
        return instancia;
    }

    public ArrayList<Estudiant> obtenirAlumnes() throws Exception {
        ArrayList<Estudiant> alumnes = new ArrayList<Estudiant>();
        String sql = "SELECT ID_ALUMNE, DNI, NOMBRE, APELLIDOS FROM Estudiants";
        PreparedStatement ps;
        ps = con.prepareCall("SELECT ID_ALUMNE, DNI, NOMBRE, APELLIDOS FROM Estudiants");
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Estudiant e = new Estudiant();
                e.setDni(rs.getString("DNI"));
                e.setNom(rs.getString("NOMBRE"));
                e.setCognoms(rs.getString("APELLIDOS"));
                e.setIdAlumne(rs.getInt("ID_ALUMNE"));
                alumnes.add(e);
            }
        }
        return alumnes;
    }

    public ArrayList<Estudiant> cercaAlumnes(String alumne) throws Exception {
        ArrayList<Estudiant> alumnes = new ArrayList<Estudiant>();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT ID_ALUMNE, DNI, NOMBRE, APELLIDOS FROM Estudiants " +
                "WHERE APELLIDOS LIKE  ? OR NOMBRE LIKE ?");
        ps.setString(1, alumne);
        ps.setString(2, alumne);
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Estudiant e = new Estudiant();
                e.setDni(rs.getString("DNI"));
                e.setNom(rs.getString("NOMBRE"));
                e.setCognoms(rs.getString("APELLIDOS"));
                e.setIdAlumne(rs.getInt("ID_ALUMNE"));
                alumnes.add(e);
            }
        }
        return alumnes;
    }

    public List<Estudiant> getAllEstudiants() throws Exception {
        List<Estudiant> estudiants = new ArrayList<>();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT * FROM Estudiants");
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Estudiant e = new Estudiant();
                String dni = rs.getString("DNI");
                e.setDni(dni);
                e.setNom(rs.getString("NOMBRE"));
                e.setCognoms(rs.getString("APELLIDOS"));
                e.setIdAlumne(rs.getInt("ID_ALUMNE"));
                e.setDataNaixement(rs.getString("DATNAI"));
                e.setTelPersonal(rs.getString("TELEFONO"));
                e.setCorreuElectronic(rs.getString("DIR_CORREO"));
                estudiants.add(e);
            }
        } else {
            System.out.println("No s'ha pogut establir connexió amb la base de dades.");
        }
        return estudiants;
    }

    public Estudiant getDadesEstudiant(String dni) throws Exception {
        Estudiant e = new Estudiant();
        PreparedStatement ps;
        ps = con.prepareCall("SELECT * FROM Estudiants WHERE DNI = ?");
        ps.setString(1, dni);
        if (con != null) {
            //Fer la consulta
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                e.setDni(dni);
                e.setNom(rs.getString("NOMBRE"));
                e.setCognoms(rs.getString("APELLIDOS"));
                e.setIdAlumne(rs.getInt("ID_ALUMNE"));
                e.setDataNaixement(rs.getString("DATNAI"));
                e.setTelPersonal(rs.getString("TELEFONO"));
                e.setDireccio(rs.getString("DIRECCION"));
                e.setNumCFN(rs.getString("FN_CARNET"));
                e.setDataCadCFN(rs.getString("FN_DATCAD"));
                e.setDireccioFamiliar(rs.getString("DIRECCIO") + " " + rs.getString("POBLACIO"));
                e.setIBAN(rs.getString("IBAN"));
                e.setCC(rs.getString("CC"));

                e.setCorreuElectronic(rs.getString("DIR_CORREO"));
                e.setAccesUniversitat(rs.getInt("VIA_ACCES"));
                e.setAnyPAU(rs.getString("ANYO_PAAU"));
                e.setConvocatoriaPAU(rs.getString("CONVOC_PAAU").toCharArray()[0]);
                e.setNotaMitjAcces(rs.getString("NOTA_ACCES"));
                e.setMatricules(DadesMatricula.getInstancia().getMatriculesEstudiant(dni));
            }
        } else {
            System.out.println("No s'ha pogut establir connexió amb la base de dades.");
        }
        return e;
    }

    public String getNomCognoms(String dni) throws SQLException {
        String nomCognoms = "";
        PreparedStatement ps;
        ps = con.prepareCall("SELECT NOMBRE, APELLIDOS FROM Estudiants WHERE DNI = ?");
        ps.setString(1, dni);
        if (con != null) {
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                nomCognoms = rs.getString("NOMBRE") + "/" + rs.getString("APELLIDOS");
            }
        } else {
            System.out.println("No s'ha pogut establir connexió amb la base de dades.");
        }
        return nomCognoms;
    }
}
