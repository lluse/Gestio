package com.igema.main.vistes.classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ModelActes {
    private final String dni;
    private final SimpleStringProperty nomComplet;
    private final SimpleStringProperty nota;
    private final SimpleStringProperty qualificacio;
    private final Button expedient;

    public ModelActes (String dni, String nom, String nota, String qualificacio, Button button) {
        this.dni = dni;
        this.nomComplet = new SimpleStringProperty(nom);
        this.nota = new SimpleStringProperty(nota);
        this.qualificacio = new SimpleStringProperty(qualificacio);
        expedient = button;
        expedient.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(nomComplet + " " + nota + " " + qualificacio);
            }
        });
    }

    @Override
    public String toString() {
        return nomComplet.get() + " " + qualificacio.get() + " " + nota.get();
    }

    public String getNomComplet() {
        return nomComplet.get();
    }

    public String getNota() {
        return nota.get();
    }

    public String getQualificacio() {
        return qualificacio.get();
    }

}
