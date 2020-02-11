package com.igema.main.domini.classes;

//declarem la classe com a final, per tal de que no es crein classes derivades a la classe beca
public final class Beca {
    private int codiBeca;
    private String beca;
    private float quantitat;	//import final de la beca
    private int hores;		//

    //GETTERS i SETTERS
    public int getCodiBeca() {
        return codiBeca;
    }
    public void setCodiBeca(int codiBeca) {
        this.codiBeca = codiBeca;
    }
    public String getBeca() {
        return beca;
    }
    public void setBeca(String beca) {
        this.beca = beca;
    }
    public float getQuantitat() {
        return quantitat;
    }
    public void setQuantitat(float quantitat) {
        this.quantitat = quantitat;
    }
    public int getHores() {
        return hores;
    }
    public void setHores(int hores) {
        this.hores = hores;
    }

}
