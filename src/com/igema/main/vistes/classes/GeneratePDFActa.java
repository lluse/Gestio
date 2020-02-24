package com.igema.main.vistes.classes;

import com.igema.main.vistes.NotesAssignaturaManager;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

public class GeneratePDFActa {

    private String nomAssignatura;
    private String cursAcad;
    private String estatActa;
    private String semestre;
    private ObservableList<ModelActes> list;

    private String fotoURV = "src/com/igema/main/vistes/escenes/images/Logo-URV.jpg";
    private String fotoIGEMA = "src/com/igema/main/vistes/escenes/images/logo_igema.jpg";

    private final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
    private final Font chunkFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
    private final Font encabezadoFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
    private final Font contenidoFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);

    public GeneratePDFActa(String nomAssignatura, String cursAcad, String estat, String semestre,
                           ObservableList<ModelActes> list) {
        this.nomAssignatura = nomAssignatura;
        this.cursAcad = cursAcad;
        estatActa = estat;
        this.semestre = semestre;
        this.list = list;

    }

    public void generar(String path) throws FileNotFoundException, DocumentException {
        FileOutputStream archivo = new FileOutputStream(path);
        Document doc = new Document();
        PdfWriter.getInstance(doc, archivo);
        doc.open();

        //Afegim els dos logos: IGEMA y URV
        Image image_igema, image_urv;
        try {
            image_igema = Image.getInstance(fotoIGEMA);
            image_igema.scaleAbsolute(130, 25);
            image_igema.setAbsolutePosition(60, 750);

            image_urv = Image.getInstance(fotoURV);
            image_urv.scaleAbsolute(130, 25);
            image_urv.setAbsolutePosition(215, 750);

            doc.add(image_igema);
            doc.add(image_urv);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paragraph parrafo = new Paragraph("ACTES");
        parrafo.setAlignment(0);
        parrafo.setSpacingBefore(70f);
        doc.add(parrafo);

        Paragraph nomAssig, curs, sem, estat;
        nomAssig = new Paragraph(nomAssignatura, paragraphFont);
        curs = new Paragraph(cursAcad, paragraphFont);
        sem = new Paragraph(semestre, paragraphFont);
        sem.setSpacingAfter(10.f);

        Chunk estActual = new Chunk(estatActa, chunkFont);

        estat = new Paragraph("Estat: ", paragraphFont);
        estat.add(estActual);
        //estat.setSpacingAfter(20.f);

        doc.add(curs);
        doc.add(nomAssig);
        doc.add(sem);
        doc.add(estat);

        //Generamos una tabla de 3 columnas
        PdfPTable tabla = new PdfPTable(3);
        tabla.setSpacingBefore(30f);

        //Añadimos la primera fila
        tabla.addCell(new PdfPCell(new Phrase("Estudiant", encabezadoFont)));
        tabla.addCell(new PdfPCell(new Phrase("Qualificació", encabezadoFont)));
        tabla.addCell(new PdfPCell(new Phrase("Nota", encabezadoFont)));

        for(ModelActes m : list) {
            tabla.addCell(new PdfPCell(new Phrase(m.getNomComplet(), contenidoFont)));
            tabla.addCell(new PdfPCell(new Phrase(m.getQualificacio(), contenidoFont)));
            tabla.addCell(new PdfPCell(new Phrase(m.getNota(), contenidoFont)));
        }
        tabla.setWidths(new float[]{4.5f, 2.f, 2.5f});
        doc.add(tabla);

        String data = DataActual.dataActual;

        Paragraph ubicacio = new Paragraph("Barcelona, " + data, chunkFont);
        ubicacio.setAlignment(Element.ALIGN_RIGHT);
        ubicacio.setSpacingBefore(10f);


        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph info = new Paragraph("Professor/a", chunkFont);
        info.add(glue);
        info.add("Director Acadèmic");

        doc.add(ubicacio);
        doc.add(info);

        doc.close();
    }
}
