package com.igema.main.domini.classes;

import javafx.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Matricula {
    private Integer numMat; //valor unic per cada matricula
    private String periodeAcademic;
    private Integer numURV; //numero de la universitat rovira virgili
    private Date dataMat; //data de la matricula
    private String curs; //PRIMER, SEGON, TERCER i QUART
    private String horari; //Mati o tarda
    private Beca b;
    private Estudiant e;
    private HashSet<AssignaturaMatricula> am;

    //CREADORA
    public Matricula() {}

    public Matricula(Integer numMat, String periodeAcademic, Integer numURV, Date dataMat,
                     String curs, String horari, Beca b, Estudiant e, HashSet<AssignaturaMatricula> am) {
        this.setNumMat(numMat);
        this.setPeriodeAcademic(periodeAcademic);
        this.setNumURV(numURV);
        this.setDataMat(dataMat);
        this.setCurs(curs);
        this.setHorari(horari);
        this.setBeca(b);
        this.setEstudiant(e);
        this.am = new HashSet<AssignaturaMatricula>();
    }

    //GETTERS i SETTERS
    public Integer getNumMat() {
        return numMat;
    }
    public void setNumMat(Integer numMat) {
        this.numMat = numMat;
    }
    public String getPeriodeAcademic() {
        return periodeAcademic;
    }
    public void setPeriodeAcademic(String periodeAcademic) {
        this.periodeAcademic = periodeAcademic;
    }
    public Integer getNumURV() {
        return numURV;
    }
    public void setNumURV(Integer numURV) {
        this.numURV = numURV;
    }
    public Date getDataMat() {
        return dataMat;
    }
    public void setDataMat(Date dataMat) {
        this.dataMat = dataMat;
    }
    public Estudiant getEstudiant() {
        return e;
    }
    public void setEstudiant(Estudiant e) {
        this.e = e;
    }
    public String getCurs() {
        return curs;
    }
    public void setCurs(String curs) {
        this.curs = curs;
    }
    public String getHorari() {
        return horari;
    }
    public void setHorari(String horari) {
        this.horari = horari;
    }
    public Beca getBeca() {
        return b;
    }
    public void setBeca(Beca b) {
        this.b = b;
    }
    public HashSet<AssignaturaMatricula> getAssignaturaMatricula() {
        return am;
    }
    public void setAssignaturaMatricula(HashSet<AssignaturaMatricula> am) {
        this.am = am;
    }

    //CONSULTORES
    public double calculaNotaMitjanaBase10() {
        double notaMxCredit = 0.0;
        int credits = 0;
        for (AssignaturaMatricula a:am) {
            Double nota = a.getNota();
            int credit = a.getCredits();
            if (nota != null) {
                notaMxCredit += nota * credit;
                credits += credit;
            }
        }
        return notaMxCredit/(double)credits;
    }

    public double calculaNotaMijanaBase4() {
        double notaMxCredit = 0.0;
        int credits = 0;
        for (AssignaturaMatricula a:am) {
            int nota = 0;
            String qualificacio = a.getQualificacio();
            if (qualificacio != null) {
                switch (qualificacio) {
                    case "APROVAT":
                        nota = 1;
                        break;
                    case "NOTABLE":
                        nota = 2;
                        break;
                    case "EXCEL·LENT":
                        nota = 3;
                        break;
                    case "MH":
                        nota = 4;
                        break;
                }
                notaMxCredit += nota * a.getCredits();
                credits += a.getCredits();
            }
        }
        return notaMxCredit/credits;
    }

    public int[] calculaCredits() {
        int[] totalCredits = new int[5];
        for (AssignaturaMatricula a:am) {
            Double nota = a.getNota();
            if (nota != null && nota >= 5) {
                Assignatura assig = a.getAssignatura();
                char tip = assig.getTipologia();
                switch (tip) {
                    case 'T':
                        totalCredits[0] += a.getCredits();
                        break;
                    case 'B':
                        totalCredits[1] += a.getCredits();
                        break;
                    default:
                        String nom = assig.getAssignatura();
                        if (nom == "Pràctiques externes") totalCredits[3] += a.getCredits();
                        else if (nom == "Treball de Fi de Grau") totalCredits[4] += a.getCredits();
                        else totalCredits[2] += a.getCredits();
                        break;
                }
            }
        }
        return totalCredits;
    }

    public HashSet<Assignatura> obtenirAssignaturesMatriculades() {
        HashSet<Assignatura> assignatures = new HashSet<Assignatura>();
        for (AssignaturaMatricula amat:am) {
            assignatures.add(amat.getAssignatura());
        }
        return assignatures;
    }

    public HashMap<String, Pair<String, Pair<Double, String>>> consultarQualificacionsFin() {
        HashMap<String, Pair<String, Pair<Double, String>>> qualificacions
                = new HashMap<String, Pair<String, Pair<Double, String>>>();
        String curs = this.getCurs();

        for (AssignaturaMatricula amat:am) {
            Double nota = amat.getNota();
            String qualificacio = amat.getQualificacio();
            String nomAssig = amat.getAssignatura().getAssignatura();
            Pair<Double, String> p = new Pair<Double, String>(nota, qualificacio);
            Pair<String, Pair<Double, String>> fi = new Pair<String, Pair<Double, String>>(curs, p);
            qualificacions.put(curs, fi);
        }

        return qualificacions;
    }

}
