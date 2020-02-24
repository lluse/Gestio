package com.igema.main.vistes.classes;

import java.time.LocalDate;
import java.util.Date;

public class DataActual {

    public static String dataActual = obtenirDataActual();

    private static String obtenirDataActual() {
        LocalDate data = java.time.LocalDate.now();
        String dataActual = data.getDayOfMonth() + " de " + transformaMes(data.getMonthValue()) + " de " + data.getYear();
        return  dataActual;
    }

    private static String transformaMes(int mes) {
        String smes = "";
        switch (mes) {
            case 1:
                smes = "gener";
                break;
            case 2:
                smes = "febrer";
                break;
            case 3:
                smes = "març";
                break;
            case 4:
                smes = "abril";
                break;
            case 5:
                smes = "maig";
                break;
            case 6:
                smes = "juny";
                break;
            case 7:
                smes = "juliol";
                break;
            case 8:
                smes = "agost";
                break;
            case 9:
                smes = "septembre";
                break;
            case 10:
                smes = "octubre";
                break;
            case 11:
                smes = "novembre";
                break;
            case 12:
                smes = "desembre";
                break;
        }
        return smes;
    }

}
