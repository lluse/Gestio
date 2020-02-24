package com.igema.main.vistes;

import com.igema.main.domini.controlador.ControladorDomini;
import com.igema.main.vistes.classes.GeneratePDFActa;
import com.igema.main.vistes.classes.ModelActes;
import com.igema.main.vistes.classes.ModelNotes;
import com.itextpdf.text.DocumentException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActaAssignatura {

    @FXML
    private Text assignatura;
    @FXML
    private Text curs;
    @FXML
    private Text semestre;
    @FXML
    private ComboBox<String> estatActa;

    @FXML
    private TableView<ModelActes> acta;
    @FXML
    private TableColumn<ModelActes, String> colEstudiant;
    @FXML
    private TableColumn<ModelActes, Double> colQualificacio;
    @FXML
    private TableColumn<ModelActes, String> colNota;
    @FXML
    private TableColumn<ModelActes, Button> colBtn;

    @FXML
    private Button sortir;



    //variables estàtiques pel PDF
    protected static String semestrePDF;
    protected static String estatPDF;
    protected static ObservableList<ModelActes> infoTaula;


    @FXML
    private void initialize() throws SQLException {
        inicialitzaVariablesSem();
        assignatura.setText(NotesAssignaturaManager.cursAcad);
        curs.setText(NotesAssignaturaManager.nomAssignatura);
        semestre.setText(semestrePDF);

        estatActa.getItems().addAll("OBERTA", "TANCADA");

        acta.setEditable(false);

        colEstudiant.setCellValueFactory(new PropertyValueFactory("nomComplet"));
        colQualificacio.setCellValueFactory(new PropertyValueFactory("qualificacio"));
        colNota.setCellValueFactory(new PropertyValueFactory("nota"));
        colBtn.setCellValueFactory(new PropertyValueFactory("expedient"));

        ObservableList<ModelNotes> llistaEstudiants = NotesAssignaturaManager.llistaTaula;
        infoTaula = generarTaula(llistaEstudiants);
        acta.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        acta.setItems(infoTaula);
    }

    private ObservableList<ModelActes> generarTaula(ObservableList<ModelNotes> llistaEstudiants) throws SQLException {
        List<ModelActes> llista = new ArrayList<>();
        for(ModelNotes m : llistaEstudiants) {
            String dni = m.getDni();
            String nom = m.getNomComplet();
            String nota = m.getNota();
            String qualificacio = m.getQualificacio();
            llista.add(
                    new ModelActes(
                            dni,
                            nom,
                            qualificacio,
                            nota,
                            new Button("Expedient")
                    )
            );
            //ModelActes ma = new ModelActes(nom, qualificacio, nota, );
            //acta.getItems().add(ma);
        }
        return FXCollections.observableArrayList(llista);
    }

    @FXML
    public void generarPDF() throws Exception {
        estatPDF = estatActa.getValue();
        /*
        GeneratePDFActa gp = new GeneratePDFActa(NotesAssignaturaManager.nomAssignatura, NotesAssignaturaManager.cursAcad,
                semestrePDF, estatPDF, infoTaula);
        gp.generar();
        */
        Stage stage = new Stage();
        GuardarPDFManager gpdf = new GuardarPDFManager();
        gpdf.start(stage);
    }

    private void inicialitzaVariablesSem() {
        int sem = NotesAssignaturaManager.semestre;
        switch (sem) {
            case 1:
                semestrePDF = "GENER";
                break;
            case 2:
                semestrePDF = "JUNY";
                break;
            default:
                semestrePDF = "Sense SEMESTRE";
                break;
        }
    }

    @FXML
    private void sortir() {
        Stage st = (Stage) this.sortir.getScene().getWindow();
        st.close();
    }

}
