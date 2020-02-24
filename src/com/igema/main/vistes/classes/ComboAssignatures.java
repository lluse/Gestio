package com.igema.main.vistes.classes;

import com.igema.main.domini.controlador.ControladorDomini;
import javafx.collections.FXCollections;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComboAssignatures {
    Integer id;
    String nom;
    Integer semestre;

    ComboAssignatures(Integer i, String n, Integer sem) {
        id = i;
        nom = n;
        semestre = sem;
    }

    @Override
    public String toString() {
        return nom;
    }

    public int getId() { return id; }
    public String getNom() {return nom;}
    public int getSemestre() {return semestre;}

    public static List<ComboAssignatures> importarAssignatures() throws SQLException {
        List<String> assigs = ControladorDomini.getInstancia().importarAssignatures();
        List<ComboAssignatures> combo = new ArrayList<ComboAssignatures>();
        for (String s : assigs) {
            String[] arrayS = s.split("/");
            int id = Integer.parseInt(arrayS[0]);
            String nom = arrayS[1];
            int semestre = Integer.parseInt(arrayS[2]);
            ComboAssignatures ca = new ComboAssignatures(id, nom, semestre);
            combo.add(ca);
        }
        return combo;
    }

}
