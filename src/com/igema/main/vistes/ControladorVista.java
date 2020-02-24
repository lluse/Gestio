package com.igema.main.vistes;

import com.igema.main.domini.classes.Assignatura;
import com.igema.main.domini.classes.Estudiant;
import com.igema.main.domini.controlador.ControladorDomini;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ControladorVista {
    /////////////////instancia//////////////////////////
    private static ControladorVista instancia;

    public static ControladorVista getInstancia() {
        if (instancia == null) instancia = new ControladorVista();
        return instancia;
    }

/*
    public Boolean loginUsuari(String user, String pass) throws Exception {
        try {
            return ControladorDomini.getInstancia().loginUsuari(user, pass);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
*/
    public ArrayList<Estudiant> buscarAlumnes(String cognoms) throws Exception {
        return ControladorDomini.getInstancia().buscarAlumnes(cognoms);
    }

    public HashMap<String, Object> getDadesPersonalsEstudiant(Estudiant e) {
        HashMap<String, Object> dades = ControladorDomini.getInstancia().getDadesPersonalsSecretaria(e);
        return dades;
    }

    public List<HashSet<Assignatura>> obtenirAssignaturesMatriculades(Estudiant e) {
        return ControladorDomini.getInstancia().obtenirAssignaturesMatriculades(e);
    }

    public ArrayList<String> importarAssignatures() throws SQLException {
        return ControladorDomini.getInstancia().importarAssignatures();
    }

    public List<Assignatura> getAllAssignatures() throws SQLException {
        return ControladorDomini.getInstancia().getAllAssignatures();
    }

    public HashMap<String, Object> getDadesPersonalsAlumne(Estudiant e) {
        return ControladorDomini.getInstancia().getDadesPersonalsAlumne(e);
    }

    public HashMap<String, String> getDadesAcces(Estudiant e) {
        return ControladorDomini.getInstancia().getDadesAcces(e);
    }
}
