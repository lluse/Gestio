package com.igema.main.domini.controlador;


import com.igema.main.dades.controlador.ControladorDades;
import com.igema.main.domini.classes.Assignatura;
import com.igema.main.domini.classes.Estudiant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

//import com.igema.main.domini.classes.Usuari;

public class ControladorDomini {
    //////////////////instancia//////////////////7
    private static ControladorDomini instancia;

    public static ControladorDomini getInstancia() {
        if (instancia == null) instancia = new ControladorDomini();
        return instancia;
    }

    public static int executeQuery(String sql) {
        return ControladorDades.executeQuery(sql);
    }

    public Estudiant cercarAlumne(String dni) throws Exception {
        return ControladorDades.getInstancia().getDadesEstudiant(dni);
    }

    public ArrayList<Estudiant> buscarAlumnes(String cognoms) throws Exception {
        return ControladorDades.getInstancia().buscarAlumnes(cognoms);
    }
/*
    public Boolean loginUsuari(String nom, String pass) throws Exception {
        return ControladorDades.getInstancia().loginUsuari(nom, pass);
    }
*/
    public HashMap<String, Object> getDadesPersonalsSecretaria(Estudiant e) {
        return e.getDadesPersonalsSecretaria();
    }

    public HashMap<String, Object> getDadesPersonalsAlumne(Estudiant e) {
        return e.getDadesPersonalsAlumne();
    }
/*
    public String getTipusUsuari() {
        return Usuari.getInstancia().getUserType();
    }
*/
    public List<HashSet<Assignatura>> obtenirAssignaturesMatriculades(Estudiant e) {
        return e.obtenirAssignaturesMatriculades();
    }

    public HashSet<Assignatura> obtenirAssignaturesMatriculades(Estudiant e, String curs) {
        return e.obtenirAssignaturesMatriculadesCursAcad(curs);
    }


    public ArrayList<String> importarAssignatures() throws SQLException {
        return ControladorDades.getInstancia().importarAssignatures();
    }

    public List<Assignatura> getAllAssignatures() throws SQLException {
        return ControladorDades.getInstancia().getAllAssignatures();
    }

    public HashMap<Integer, String> cargarTaulaNotes(int id, String curs) throws SQLException {
        return ControladorDades.getInstancia().cargarTaulaNotes(id, curs);
    }

    public Estudiant getDadesEstudiant(String s) throws Exception {
        return ControladorDades.getInstancia().getDadesEstudiant(s);
    }

    public HashMap<String, String> getDadesAcces(Estudiant e) {
        return e.getdadesAccesIgema();
    }
}
