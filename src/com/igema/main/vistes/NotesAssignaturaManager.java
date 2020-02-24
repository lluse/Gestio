package com.igema.main.vistes;


import com.igema.main.domini.controlador.ControladorDomini;
import com.igema.main.vistes.classes.ComboAssignatures;
import com.igema.main.vistes.classes.ModelNotes;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NotesAssignaturaManager extends Application{

    private ObservableList<ComboAssignatures> list; //llista dels cursos academics

    @FXML
    private ComboBox assignaturaComboBox;
    @FXML
    private ComboBox<String> cursComboBox;

    @FXML
    private TableView<ModelNotes> alumnes;
    @FXML
    private TableColumn<ModelNotes, String> colNom;
    @FXML
    private TableColumn<ModelNotes, String> colCognoms;
    @FXML
    private TableColumn<ModelNotes, String> colDni;
    @FXML
    private TableColumn<ModelNotes, String> colNota;
    @FXML
    private TableColumn<ModelNotes, String> colQualificacio;
    @FXML
    private TableColumn<ModelNotes, Integer> colVegada;
    @FXML
    private TableColumn<ModelNotes, CheckBox> colReconeguda;
    @FXML
    private TableColumn<ModelNotes, CheckBox> colConvalidada;

    @FXML
    private Button editar;
    @FXML
    private Button guardar;
    @FXML
    private Button cancelar;
    @FXML
    private Button acta;

    //informació que ens servirà per les actes
    protected static ObservableList<ModelNotes> llistaTaula = FXCollections.observableArrayList(); //Llista dels alumnes trobats
    protected static String nomAssignatura;
    protected static String cursAcad;
    protected static int semestre;


    @Override
    public void start(Stage primaryStage) throws Exception {
        String sceneFile = "escenes/notesAssignatura.fxml";
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(sceneFile));
        Scene scene = new Scene(root, 1025, 515);
        primaryStage.setTitle("Notes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @FXML
    private void initialize() throws SQLException {
        list = FXCollections.observableArrayList(ComboAssignatures.importarAssignatures());

        alumnes.setEditable(false);
        editar.setDisable(true);
        guardar.setDisable(true);
        cancelar.setDisable(true);
        acta.setDisable(true);

        //permite que las celdas puedan seleccionarse individualmente
        alumnes.getSelectionModel().cellSelectionEnabledProperty().set(true);

        colNom.setCellValueFactory(new PropertyValueFactory("nom"));
        colCognoms.setCellValueFactory(new PropertyValueFactory("cognom"));
        colDni.setCellValueFactory(new PropertyValueFactory("dni"));


        setupNotaColumn();

        colQualificacio.setCellValueFactory(new PropertyValueFactory("qualificacio"));
        colVegada.setCellValueFactory(new PropertyValueFactory("vegada"));
        colReconeguda.setCellValueFactory(new PropertyValueFactory("reconeguda"));
        colConvalidada.setCellValueFactory(new PropertyValueFactory("convalidada"));


        cursComboBox.getItems().addAll("2018-2019", "2019-2020", "2020-2021", "2021-2022");
        assignaturaComboBox.setItems(list);
        assignaturaComboBox.getSelectionModel().selectFirst(); //Select the first element

        //ComboBox assignatures
        assignaturaComboBox.setCellFactory(new Callback<ListView<ComboAssignatures>, ListCell<ComboAssignatures>>() {
            @Override
            public ListCell call(ListView<ComboAssignatures> param) {
                final ListCell<ComboAssignatures> cell = new ListCell<ComboAssignatures>() {
                    @Override
                    protected void updateItem(ComboAssignatures c, boolean b) {
                        super.updateItem(c, b);
                        if (c != null) {
                            setText(c.getNom()); //Nomes volem que es mostri el nom de l'assignatura, per en realitat a la
                                            //ComboBox hi ha el nom i el id.
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void setupNotaColumn() {
        colNota.setCellValueFactory(new PropertyValueFactory("nota"));
        //permitir editar sobre la columna nota
        colNota.setCellFactory(TextFieldTableCell.forTableColumn());
        try {
            colNota.setOnEditCommit(event -> {
                Object value = event.getNewValue();
                if (value instanceof String){
                    ModelNotes m = (event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    String sNota = (String) value;
                    char[] novaNota = sNota.toCharArray();
                    if (sNota == "NP") {
                        m.setOldValueNota(m.getNota());
                        m.setOldValueQuali(m.getQualificacio());
                        m.setNota("NP");
                        m.setQualificacio("NO PRESENTAT");
                        m.setDirtyBit(1);
                    }
                    else if (novaNota[novaNota.length - 1] == 'm') {
                        novaNota = Arrays.copyOfRange(novaNota, 0, novaNota.length - 1);
                        String notaDef = String.valueOf(novaNota);
                        double notaDouble = Double.parseDouble(notaDef); //Per comprovar si no salta cap excepcio
                        m.setOldValueNota(m.getNota());
                        m.setOldValueQuali(m.getQualificacio());
                        m.setNota(notaDef);
                        m.setQualificacio("MH");
                        m.setDirtyBit(1);
                    } else {
                        double nota = Double.parseDouble(sNota);
                        if (nota <= 10.0 && nota >= 0.0) {
                            m.setOldValueNota(m.getNota());
                            m.setOldValueQuali(m.getQualificacio());
                            m.setNota(String.valueOf(nota));
                            m.setQualificacio(modificaQualificacio(nota));
                            m.setDirtyBit(1);

                            m.getReconeguda().setSelected(false);
                            alumnes.refresh();
                        } else {
                            editCancel();
                            throw new NumberFormatException("La nota que s'ha introduit no es valida. (0 <= nota <= 10)");
                        }
                    }
                }
                else {
                    editCancel();
                    throw new NumberFormatException("No s'ha introduit un número");
                }
            });
        } catch (NumberFormatException n) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en l'edició de la nota");
            alert.setContentText(n.toString());
            alert.showAndWait().ifPresent(rs -> {

            });
        }
    }

    private String modificaQualificacio(Double novaNota) {
        if (novaNota >= 9.0) return "EXCEL·LENT";
        else if (novaNota >= 7.0) return "NOTABLE";
        else if (novaNota >= 5.0) return "APROVAT";
        else return "SUSPENS";
    }

    @FXML
    private void cargarTaula() throws SQLException {
        if (assignaturaComboBox.getValue() != null && cursComboBox.getValue() != null) {
            acta.setDisable(false);
            ComboAssignatures ca = (ComboAssignatures)assignaturaComboBox.getValue();
            int id = ca.getId();

            nomAssignatura = id + " " + ca.getNom() + " " + ", Grup A";
            semestre = ca.getSemestre();
            cursAcad = cursComboBox.getValue();

            HashMap<Integer, String> dadesTaula = ControladorDomini.getInstancia().cargarTaulaNotes(id, cursAcad);
            mostrarResultats(dadesTaula);

            alumnes.getItems().addAll(llistaTaula);
            alumnes.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            editar.setDisable(false);
        }
    }

    private void mostrarResultats(HashMap<Integer, String> dadesTaula) {
        llistaTaula.clear();
        alumnes.getItems().clear();
        dadesTaula.forEach((k, v) -> {
            int id = k;

            String[] valor = v.split("/");

            int vegada = Integer.parseInt(valor[0]);
            String nota = valor[1];
            String qualificacio;
            if (Double.parseDouble(nota) == 0) {
                nota = "NP";
                qualificacio = "NO PRESENTAT";
            }
            else qualificacio = valor[2];
            String nom = valor[3];
            String cognoms = valor[4];
            String dni = valor[5];

            boolean b = valor[6].contains("true");
            boolean b1 = valor[7].contains("true");

            CheckBox reconeguda;
            CheckBox convalidada;
            if (b) {
                reconeguda = new CheckBox();
                reconeguda.setSelected(true);
            } else {
                reconeguda = new CheckBox();
                reconeguda.setSelected(false);
            }

            if (b1) {
                convalidada = new CheckBox();
                convalidada.setSelected(true);
            } else {
                convalidada = new CheckBox();
                convalidada.setSelected(false);
            }

            llistaTaula.add(
                    new ModelNotes(
                            id,
                            nom,
                            cognoms,
                            dni,
                            nota,
                            qualificacio,
                            vegada,
                            reconeguda,
                            convalidada)
            );
        });
    }

    @FXML
    private void activarEdicio() {
        alumnes.setEditable(true);
        guardar.setDisable(false);
        cancelar.setDisable(false);
        editar.setDisable(true);
    }

    private void desactivarEdicio() {
        alumnes.setEditable(false);
        guardar.setDisable(true);
        cancelar.setDisable(true);
        editar.setDisable(false);
    }

    @FXML
    private void guardarCanvis() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmació");
        alert.setContentText("Estàs segur/a que vols guardar l'operació?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            List<ModelNotes> dades =  alumnes.getItems();
            boolean updated = false;
            for (ModelNotes d : dades) {
                if (d.getReconeguda().isSelected()) {
                    int id = d.getId();
                    double nota = Double.parseDouble(d.getNota());

                    String sql = "UPDATE Assignaturesmatricula SET RECONEGUDA = 'True'," +
                            " nota = " + nota + ", qualificacio = 'RECONEGUDA'" +
                            " WHERE IdAssignaturaMatricula = " + id;

                    int status = ControladorDomini.executeQuery(sql);
                    if (status > 0) {
                        d.setDirtyBit(0); d.setOldValueNota(null); d.setOldValueQuali(null);
                        updated = true;
                        alumnes.refresh();
                    }
                }

                else if (d.getConvalidada().isSelected()) {
                    int id = d.getId();

                    String sql = "UPDATE Assignaturesmatricula SET CONVALIDADA = 'True'," +
                            " nota = 5.0, qualificacio = 'APROVAT'" +
                            " WHERE IdAssignaturaMatricula = " + id;

                    int status = ControladorDomini.executeQuery(sql);
                    if (status > 0) {
                        d.setDirtyBit(0); d.setOldValueNota(null); d.setOldValueQuali(null);
                        d.setNota("CV");
                        d.setQualificacio("APROVAT");
                        updated = true;
                        alumnes.refresh();
                    }
                }

                else if (d.getDirtyBit() == 1) {
                    int idAssignaturaMatricula = d.getId();
                    Double nota;
                    if (d.getNota() == "NP") nota = 0.0;
                    else nota = Double.parseDouble(d.getNota());

                    String quali = d.getQualificacio();
                    String sql;
                    if (!d.getReconeguda().isSelected()) {
                        sql = "UPDATE Assignaturesmatricula SET RECONEGUDA = 'False', " +
                                "nota = " + nota + ", qualificacio = '" + quali + "' " +
                                "WHERE IdAssignaturaMatricula = " + idAssignaturaMatricula;
                    }
                    else {
                        sql = "UPDATE Assignaturesmatricula SET nota = " + nota + ", qualificacio = '" +
                                quali + "' WHERE IdAssignaturaMatricula = " + idAssignaturaMatricula;
                    }
                    int status = ControladorDomini.executeQuery(sql);
                    if (status > 0) {
                        d.setDirtyBit(0);
                        d.setOldValueNota(null);
                        d.setOldValueQuali(null);
                        updated = true;
                        alumnes.refresh();
                    }
                    else if (status == -1) {
                        mostrarErrorGuardarCancelar("Guardar", "Hi ha hagut un problema amb els " +
                                "valors numèrics de la nota: '" + nota + "'. Si us plau revisi que estiguin en format correcte.");
                        updated = false;
                    }
                }
            }
            if (updated) {
                mostrarAlertaGuardarCancelar("Guardar", "S'han guardat tots els canvis");
            } else {
                mostrarAlertaGuardarCancelar("Guardar", "No s'ha editat cap nota");
            }
        } else {
            cancelarCanvis();
        }
    }

    @FXML
    private void cancelarCanvis() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmació");
        alert.setContentText("Estàs segur/a que vols cancelar l'operació?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            List<ModelNotes> dades =  alumnes.getItems();
            for (ModelNotes d : dades) {
                if (d.getDirtyBit() == 1) {
                    d.setNota(String.valueOf(d.getOldValueNota()));
                    d.setQualificacio(d.getOldValueQuali());
                    d.setDirtyBit(0); d.setOldValueNota(null); d.setOldValueQuali(null);
                }
            }
            mostrarAlertaGuardarCancelar("Cancelar", "S'han cancel·lat tots els canvis");
            alumnes.refresh();
        }
    }

    @FXML
    public void generarActa() {
        try {
            String sceneFile = "escenes/actaAssignatura.fxml";
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(sceneFile));
            Scene scene = new Scene(root, 1000, 676);
            Stage stage = new Stage();
            stage.setTitle("Acta");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editStart() {
        colReconeguda.setOnEditStart(event -> {
            Object newValue = event.getNewValue();
            Object oldValue = event.getOldValue();
        });
    }

    @FXML
    public void editCommit(TextFieldTableCell<ModelNotes, Double> t) {
    }

    @FXML
    public void editCancel() {

    }

    public void mostrarAlertaGuardarCancelar(String capçalera, String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(capçalera);
        alert.setContentText(missatge);
        Optional<ButtonType> action1 = alert.showAndWait();
        if (action1.get() == ButtonType.OK) {
            desactivarEdicio();
        }
    }

    public void mostrarErrorGuardarCancelar(String capçalera, String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle(capçalera);
        alert.setContentText(missatge);
        Optional<ButtonType> action1 = alert.showAndWait();
        if (action1.get() == ButtonType.OK) {
            desactivarEdicio();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
