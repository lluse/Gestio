package com.igema.main.domini.classes;

public class Assignatura {
    private Integer codi;
    private String assignatura;
    private Integer curs;
    private Integer credits;
    private String semestre;
    private Character tipologia;
    private String materia;

    public final int REQUERITSBASICA = 60;
    public final int REQUERITSOBLIGATORIA = 120;
    public final int REQUERITSOPTATIVA = 30;
    public final int REQUERITSPRACTIQUES = 24;
    public final int REQUERITSTFG = 6;

    //CONSTRUCTORA
    public Assignatura() {
    }


    //GETTERS i SETTERS
    public Integer getCodi() {
        return codi;
    }

    public void setCodi(Integer codi) {
        this.codi = codi;
    }

    public String getAssignatura() {
        return assignatura;
    }

    public void setAssignatura(String assignatura) {
        this.assignatura = assignatura;
    }

    public Integer getCurs() {
        return curs;
    }

    public void setCurs(Integer curs) {
        this.curs = curs;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer codiSemestre) {
        this.semestre = (codiSemestre == 1) ? "1Q" : "2Q";
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }


    public Character getTipologia() {
        return tipologia;
    }


    public void setTipologia(String tipologia) {
        switch (tipologia) {
            case "Bàsica":
                this.tipologia = 'T';
                break;
            case "Obligatòria":
                this.tipologia = 'B';
                break;
            case "Optativa":
                this.tipologia = 'O';
                break;
            default:
                break;
        }
    }

}
