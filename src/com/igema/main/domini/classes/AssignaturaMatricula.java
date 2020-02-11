package com.igema.main.domini.classes;

import java.util.Date;

public class AssignaturaMatricula {
    private Assignatura a;
    private Integer vegada;
    private Integer credits;
    private boolean reconeguda;
    private boolean convalidada;
    private Double nota;
    private String qualificacio;
    private Date baixa;

    public AssignaturaMatricula() {}


    public Assignatura getAssignatura() {
        return a;
    }
    public void setAssignatura(Assignatura a) {
        this.a = a;
    }
    public Integer getVegada() {
        return vegada;
    }
    public void setVegada(Integer vegada) {
        this.vegada = vegada;
    }
    public Integer getCredits() {
        return a.getCredits();
    }
    public boolean isReconeguda() {
        return reconeguda;
    }
    public void setReconeguda(boolean reconeguda) {
        this.reconeguda = reconeguda;
    }
    public boolean isConvalidada() {
        return convalidada;
    }
    public void setConvalidada(boolean convalidada) {
        this.convalidada = convalidada;
    }
    public Double getNota() {
        return nota;
    }
    public void setNota(Double nota) {
        this.nota = nota;
    }
    public Date getBaixa() {
        return baixa;
    }
    public void setBaixa(Date baixa) {
        this.baixa = baixa;
    }
    public String getQualificacio() {
        return qualificacio;
    }
    public void setQualificacio(String qualificacio) {
        this.qualificacio = qualificacio;
    }

}
