package com.igema.main.domini.classes;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Estudiant {

    /////////////////////////////////DECLARACIO DE VARIABLES///////////////////////////////////////////////////

    //Informació personal de l'alumne
    private String dni;
    private String nom;
    private String cognoms;
    private Integer idAlumne;
    private String numSS; //Seguritat Social
    private String numCFN; //Carnet Familia Numerosa
    private String dataCadCFN;
    private String dataNaixement;
    private String direccio; //adreça del domicili durant el curs
    private String direccioFamiliar;
    private String telPersonal; //telefon personal del alumne
    private String telFamiliar; //telefon del pare, mare o tutors
    private String correuElectronic;
    private String IBAN;
    private String CC;
    private HashSet<Matricula> matricules;

    //Informacio de l'acces al centre
    private String accesUniversitat;
    private String anyPAU;
    private String convocatoriaPAU;
    private String notaMitjAcces;


    /////////////////////////////////CONSTRUCTORES/////////////////////////////////////////////////
    public Estudiant() {}

    /////////////////////////////////GETTERS i SETTERS/////////////////////////////////////////////////

    //GETTERS + SETTERS Dades personals
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getCognoms() {
        return cognoms;
    }
    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }
    public Integer getIdAlumne() {
        return idAlumne;
    }
    public void setIdAlumne(Integer idAlumne) {
        this.idAlumne = idAlumne;
    }
    public String getNumSS() {
        return numSS;
    }
    public void setNumSS(String numSS) {
        this.numSS = numSS;
    }
    public String getNumCFN() {
        return numCFN;
    }
    public void setNumCFN(String numCFN) {
        this.numCFN = numCFN;
    }
    public String getDataCadCFN() {
        return dataCadCFN;
    }
    public void setDataCadCFN(String dataCadCFN) throws Exception {
        this.dataCadCFN = dataCadCFN;
    }
    public String getDataNaixement() {
        return dataNaixement;
    }
    public void setDataNaixement(String dataNaixement) throws Exception {
        this.dataNaixement = dataNaixement;
    }
    public String getDireccio() {
        return direccio;
    }
    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }
    public String getDireccioFamiliar() {
        return direccioFamiliar;
    }
    public void setDireccioFamiliar(String direccioFamiliar) {
        this.direccioFamiliar = direccioFamiliar;
    }
    public String getTelPersonal() {
        return telPersonal;
    }
    public void setTelPersonal(String telPersonal) {
        this.telPersonal = telPersonal;
    }
    public String getTelFamiliar() {
        return telFamiliar;
    }
    public void setTelFamiliar(String telFamiliar) {
        this.telFamiliar = telFamiliar;
    }
    public String getCorreuElectronic() {
        return correuElectronic;
    }
    public void setCorreuElectronic(String correuElectronic) {
        this.correuElectronic = correuElectronic;
    }

    public void setMatricules(HashSet<Matricula> m) {
        this.matricules = m;
    }
    //GETTERS I SETTERS DADES BANCARIES
    public String getIBAN() {
        return IBAN;
    }
    public void setIBAN(String iBAN) {
        IBAN = iBAN;
    }
    public String getCC() {
        return CC;
    }
    public void setCC(String cC) {
        CC = cC;
    }
    public HashSet<Matricula> getMatricules() {
        return matricules;
    }

    //GETTERS + SETTERS Dades d'Acces a IGEMA

    public String getAccesUniversitat() {
        return accesUniversitat;
    }

    public void setAccesUniversitat(Integer codiAcces) {
        switch(codiAcces) {
            case 0:
                accesUniversitat = "Proves d'accés a la Universitat (PAU) o assimilats";
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                accesUniversitat = "Desconegut";
                break;
        }
    }

    public String getAnyPAU() {
        return anyPAU;
    }
    public void setAnyPAU(String anyPAU) {
        this.anyPAU = anyPAU;
    }
    public String getConvocatoriaPAU() {
        return convocatoriaPAU;
    }
    public void setConvocatoriaPAU(Character codiConvocatoria) {
        if (codiConvocatoria == null) convocatoriaPAU = "DESCONEGUDA";
        else convocatoriaPAU = (codiConvocatoria == 'J') ? "JUNY" : "SETEMBRE";
    }

    public String getNotaMitjAcces() {
        return notaMitjAcces;
    }
    public void setNotaMitjAcces(String notaMitj) {
        notaMitjAcces = notaMitj;
    }




    ////////////////////METODES///////////////////////////////
    public HashMap<String, Object> getDadesPersonalsSecretaria() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("nom", getNom());
        result.put("cognoms", getCognoms());
        result.put("dni", getDni());
        result.put("codiAlumne", getIdAlumne());
        result.put("numSS", getNumSS());
        result.put("numCFN", getNumCFN());
        result.put("dataCadCFN", getDataCadCFN());
        result.put("dataNaixement", getDataNaixement());
        result.put("direccio", getDireccio());
        result.put("direccioFamiliar", getDireccioFamiliar());
        result.put("TelPersonal", getTelPersonal());
        result.put("TelFamiliar", getTelFamiliar());
        result.put("correu", getCorreuElectronic());

        return result;
    }

    public HashMap<String, Object> getDadesPersonalsAlumne() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("nom", getNom());
        result.put("cognoms", getCognoms());
        result.put("dni", getDni());
        result.put("dataNaix", getDataNaixement());
        result.put("codiAlumne", getIdAlumne());
        result.put("correu", getCorreuElectronic());

        return result;
    }

    public HashMap<String, String> getDadesBancariesEstudiant() {
        HashMap<String, String> result = new HashMap<>();

        result.put("iban", getIBAN());
        result.put("cc", getCC());

        return result;
    }

    public HashSet<Assignatura> consultarAssignaturesMat(String cursAcad) {
        HashSet<Assignatura> assignatures = new HashSet<Assignatura>();
        for (Matricula mat:matricules) {
            if (mat.getCurs() == cursAcad) {
                assignatures = mat.obtenirAssignaturesMatriculades();
            }
        }
        return assignatures;
    }

    public HashMap<String, Pair<String, Pair<Double, String>>> consultarQualificacionsFin() {
        HashMap<String, Pair<String, Pair<Double, String>>> qualificacions
                = new HashMap<String, Pair<String, Pair<Double, String>>>();
        for (Matricula mat:matricules) {
            qualificacions.putAll(mat.consultarQualificacionsFin());
        }
        return qualificacions;
    }

    public HashMap<String, String> getdadesAccesIgema() {
        HashMap<String, String> dadesAcces = new HashMap<String,String>();
        dadesAcces.put("Acces", getAccesUniversitat());
        dadesAcces.put("Realitzat", getAnyPAU() + "-" + this.getConvocatoriaPAU());
        dadesAcces.put("Qualificacio", getNotaMitjAcces());
        return dadesAcces;
    }

    public ArrayList<HashSet<AssignaturaMatricula>> getExpedientAcademic() {
        ArrayList<HashSet<AssignaturaMatricula>> expedient = new ArrayList<HashSet<AssignaturaMatricula>>();
        HashSet<Matricula> matricules = this.getMatricules();
        for (Matricula m : matricules) {
            String curs = m.getPeriodeAcademic();
            expedient.add(m.getAssignaturaMatricula());
        }
        return expedient;
    }

    public int[][] resumCredits() {
        int[][] resum = new int[5][3];
        int[] creditsXMat = new int[5];
        Assignatura a = new Assignatura();
        resum[0][0] = a.REQUERITSBASICA;
        resum[1][0] = a.REQUERITSOBLIGATORIA;
        resum[2][0] = a.REQUERITSOPTATIVA;
        resum[3][0] = a.REQUERITSPRACTIQUES;
        resum[4][0] = a.REQUERITSTFG;
        for (Matricula mat : matricules) {
            creditsXMat = mat.calculaCredits();
        }
        resum[0][1] = creditsXMat[0]; resum[0][2] = resum[0][0] - creditsXMat[0];
        resum[1][1] = creditsXMat[1]; resum[1][2] = resum[1][0] - creditsXMat[1];
        resum[2][1] = creditsXMat[2]; resum[2][2] = resum[2][0] - creditsXMat[2];
        resum[3][1] = creditsXMat[3]; resum[3][2] = resum[3][0] - creditsXMat[3];
        resum[4][1] = creditsXMat[4]; resum[4][2] = resum[4][0] - creditsXMat[4];
        return resum;
    }

    public double notaMitjBase4() {
        double nota = 0;
        for (Matricula mat : matricules) {
            nota += mat.calculaNotaMijanaBase4();
        }
        return nota;
    }

    public double notaMitjBase10() {
        double nota = 0;
        for (Matricula mat : matricules) {
            nota += mat.calculaNotaMitjanaBase10();
        }
        return nota;
    }

    public List<HashSet<Assignatura>> obtenirAssignaturesMatriculades() {
        List<HashSet<Assignatura>> assignatures = null;
        HashSet<Matricula> matricules = this.getMatricules();
        for (Matricula mat : matricules) {
            assignatures.add(mat.obtenirAssignaturesMatriculades());
        }
        return assignatures;
    }

    public HashSet<Assignatura> obtenirAssignaturesMatriculadesCursAcad(String cursAcad) {
        HashSet<Assignatura> assignatures = null;
        HashSet<Matricula> matricules = this.getMatricules();
        for (Matricula mat : matricules) {
            if (mat.getPeriodeAcademic() == cursAcad) {
                assignatures.addAll(mat.obtenirAssignaturesMatriculades());
            }
        }
        return assignatures;
    }

    @Override
    public String toString() {
        return getCognoms() + ", " + getNom();
    }
}
