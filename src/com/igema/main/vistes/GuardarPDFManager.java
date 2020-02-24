package com.igema.main.vistes;

import com.igema.main.vistes.classes.GeneratePDFActa;
import com.itextpdf.text.DocumentException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;


public class GuardarPDFManager extends Application {
    @FXML
    public TextField path;
    @FXML
    public Button guardar;
    @FXML
    public Button cancelar;

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String sceneFile = "escenes/guardarPDF.fxml";
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(sceneFile));
        Scene scene = new Scene(root, 510, 150);
        primaryStage.setTitle("Seleccionar archiu");
        primaryStage.setScene(scene);
        this.stage = primaryStage;
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        guardar.setDisable(true);
        cancelar.setDisable(true);
    }

    @FXML
    private void browse() {
        FileChooser dlg = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save", "*.pdf");
        dlg.getExtensionFilters().add(extFilter);

        File file = dlg.showSaveDialog(stage);
        if (file != null) {
            path.setText(file.getAbsolutePath());
            guardar.setDisable(false);
            cancelar.setDisable(false);
        }
    }

    @FXML
    private void guardarPDF() throws DocumentException {
        try {
            GeneratePDFActa pdf = new GeneratePDFActa(NotesAssignaturaManager.nomAssignatura, NotesAssignaturaManager.cursAcad,
                    ActaAssignatura.estatPDF, ActaAssignatura.semestrePDF, ActaAssignatura.infoTaula);
            pdf.generar(this.path.getText());
            sortir();
        } catch (FileNotFoundException f) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No s'ha pogut guardar degut a que el document està obert.");
            alert.setContentText("Tanca el document abans de guardar-ne un de nou.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
        }
    }

    @FXML
    private void cancelarOperacio() {
        sortir();
    }

    private void sortir() {
        Stage st = (Stage) this.cancelar.getScene().getWindow();
        st.close();
    }
}
