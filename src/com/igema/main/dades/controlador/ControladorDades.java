package com.igema.main.dades.controlador;

import com.igema.main.dades.classes.*;
import com.igema.main.dades.conexionBD.Conexion;
import com.igema.main.domini.classes.Assignatura;
import com.igema.main.domini.classes.Estudiant;
import com.igema.main.domini.classes.Matricula;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

//@Controller
public final class ControladorDades {
    /**
     * instancia de la propia classe seguint el patro de disseny singleton
     */
    private static ControladorDades instancia;

    public ControladorDades() throws SQLException {
        if (Conexion.getConexio() == null) new Conexion();
    }

    /**
     * Retorna l'unica instancia de la clase
     */
    public static ControladorDades getInstancia() throws SQLException {
        if (instancia == null) return new ControladorDades();
        return instancia;
    }

    public static int executeQuery(String sql) {
        return Update.getInstancia().executeQuery(sql);
    }
/*
    public Boolean loginUsuari(String user, String pass) throws Exception {
        return DadesUsuari.getInstancia().loginUsuari(user, pass);
    }
*/
    public ArrayList<Estudiant> buscarAlumnes(String cognoms) throws Exception {
        return DadesEstudiant.getInstancia().cercaAlumnes(cognoms);
    }

    public List<Assignatura> getAllAssignatures() throws SQLException {
        return DadesAssignatura.getInstancia().getAllAssignatures();
    }

    public List<Estudiant> getAllEstudiants() throws Exception {
        return DadesEstudiant.getInstancia().getAllEstudiants();
    }

    public Estudiant getDadesEstudiant(String dni) throws Exception {
        return DadesEstudiant.getInstancia().getDadesEstudiant(dni);
    }

    public HashSet<Matricula> getMatriculesEstudiant(String dni) throws SQLException {
        return DadesMatricula.getInstancia().getMatriculesEstudiant(dni);
    }


    public ArrayList<String> importarAssignatures() throws SQLException {
        return DadesAssignatura.getInstancia().importarAssignatura();
    }

    public HashMap<Integer, String> cargarTaulaNotes(int id, String curs) throws SQLException {
        return DadesAssignaturaMatricula.getInstancia().getAssignaturesMatriculaPerAssigICurs(id, curs);
    }

    public Assignatura getDadesAssignatura(int i) throws SQLException {
        return DadesAssignatura.getInstancia().getAssignatura(i);
    }
}
