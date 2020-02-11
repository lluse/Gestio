package com.igema.main;

import com.igema.main.dades.conexionBD.Conexion;
import com.igema.main.dades.controlador.ControladorDades;
import com.igema.main.domini.classes.Assignatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con = Conexion.getConexio();


        Assignatura a = ControladorDades.getInstancia().getDadesAssignatura(16394001);
        imprimeixAssignatura(a);
    }

    private static void imprimeixAssignatura(Assignatura a) {
        System.out.println("Codi: " + a.getCodi() + " ,Assignatura: " + a.getAssignatura() + " ,Credits: " + a.getCredits());
    }
}
