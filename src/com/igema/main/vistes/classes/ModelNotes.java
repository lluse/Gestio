package com.igema.main.vistes.classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class ModelNotes {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty dni;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty cognom;
    private final SimpleIntegerProperty vegada;

    private SimpleStringProperty nota;
    private SimpleStringProperty qualificacio;

    private CheckBox reconeguda;
    private CheckBox convalidada;

    private int dirtyBit;
    private String oldValueNota;
    private String oldValueQuali;

    public ModelNotes(int id, String nom, String cognom, String dni, String nota, String qualificacio, int vegada,
                      CheckBox reconeguda, CheckBox convalidada) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.cognom = new SimpleStringProperty(cognom);
        this.dni = new SimpleStringProperty(dni);
        this.nota = new SimpleStringProperty(nota);
        this.qualificacio = new SimpleStringProperty(qualificacio);
        this.vegada = new SimpleIntegerProperty(vegada);

        this.reconeguda = reconeguda;
        this.convalidada = convalidada;

        dirtyBit = 0;
        oldValueNota = null;
        oldValueQuali = null;
    }

    public int getId() { return id.get();}

    public String getNom() {
        return nom.get();
    }

    public int getVegada() {
        return vegada.get();
    }

    public SimpleIntegerProperty vegadaProperty() {
        return vegada;
    }

    public SimpleStringProperty notaProperty() {
        return nota;
    }

    public SimpleStringProperty qualificacioProperty() {
        return qualificacio;
    }

    public String getDni() {
        return dni.get();
    }

    public SimpleStringProperty dniProperty() {
        return dni;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public String getCognom() {
        return cognom.get();
    }

    public SimpleStringProperty cognomProperty() {
        return cognom;
    }

    public String getNota() {
        return nota.get();
    }

    public String getQualificacio() {
        return qualificacio.get();
    }

    public void setNota(String nota) {
        this.nota.set(nota);
    }

    public void setQualificacio(String qualificacio) {
        this.qualificacio.set(qualificacio);
    }

    public int getDirtyBit() {
        return dirtyBit;
    }

    public void setDirtyBit(int dirtyBit) {
        this.dirtyBit = dirtyBit;
    }

    public String getOldValueNota() {
        return oldValueNota;
    }

    public void setOldValueNota(String oldValue) {
        this.oldValueNota = oldValue;
    }

    public String getOldValueQuali() {
        return oldValueQuali;
    }

    public void setOldValueQuali(String oldValueQuali) {
        this.oldValueQuali = oldValueQuali;
    }

    public CheckBox getReconeguda() { return reconeguda; }

    public void setReconeguda(CheckBox reconeguda) { this.reconeguda = reconeguda; }

    public CheckBox getConvalidada() { return convalidada; }

    public void setConvalidada(CheckBox convalidada) { this.convalidada = convalidada; }

    public String getNomComplet() {
        return getNom() + " " + getCognom();
    }
}