package com.igema.main.vistes;


import com.igema.main.domini.controlador.ControladorDomini;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NotesAssignaturaManager extends Application{


    class ComboAssigs {
        Integer id;
        String nom;

        ComboAssigs(Integer i, String n) {
            id = i;
            nom = n;
        }

        @Override
        public String toString() {
            return nom;
        }

        public int getId() { return id; }
    }

    public static class ModelAlumne {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty nom;
        private final SimpleStringProperty cognom;
        private final SimpleIntegerProperty vegada;
        private SimpleDoubleProperty nota;
        private SimpleStringProperty qualificacio;
        private final SimpleStringProperty dni;
        private int dirtyBit;
        private Double oldValueNota;
        private String oldValueQuali;

        public ModelAlumne(SimpleIntegerProperty id, SimpleStringProperty nom, SimpleStringProperty cognom, SimpleStringProperty dni,
                           SimpleDoubleProperty nota, SimpleStringProperty qualificacio, SimpleIntegerProperty vegada) {
            this.id = id;
            this.nom = nom;
            this.cognom = cognom;
            this.dni = dni;
            this.nota = nota;
            this.qualificacio = qualificacio;
            this.vegada = vegada;
            dirtyBit = 0;
            oldValueNota = null;
            oldValueQuali = null;
        }

        public ModelAlumne(SimpleStringProperty nom, SimpleStringProperty cognom, SimpleStringProperty dni) {
            this.nom = nom;
            this.cognom = cognom;
            this.dni = dni;
            id = null;
            vegada = null;
        }

        public ModelAlumne(SimpleStringProperty nom, SimpleStringProperty cognom) {
            this.nom = nom;
            this.cognom = cognom;
            id = null;
            vegada = null;
            dni =null;
        }

        private int getId() { return id.get();}

        public String getNom() {
            return nom.get();
        }

        public int getVegada() {
            return vegada.get();
        }

        public SimpleIntegerProperty vegadaProperty() {
            return vegada;
        }

        public SimpleDoubleProperty notaProperty() {
            return nota;
        }

        public SimpleStringProperty qualificacioProperty() {
            return qualificacio;
        }

        public String getDni() {
            return dni.get();
        }

        public SimpleStringProperty dniProperty() {
            return dni;
        }

        public SimpleStringProperty nomProperty() {
            return nom;
        }

        public String getCognom() {
            return cognom.get();
        }

        public SimpleStringProperty cognomProperty() {
            return cognom;
        }

        private double getNota() {
            return nota.get();
        }

        private String getQualificacio() {
            return qualificacio.get();
        }

        private void setNota(double nota) {
            this.nota.set(nota);
        }

        private void setQualificacio(String qualificacio) {
            this.qualificacio.set(qualificacio);
        }

        private int getDirtyBit() {
            return dirtyBit;
        }

        private void setDirtyBit(int dirtyBit) {
            this.dirtyBit = dirtyBit;
        }

        private Double getOldValueNota() {
            return oldValueNota;
        }

        private void setOldValueNota(Double oldValue) {
            this.oldValueNota = oldValue;
        }

        private String getOldValueQuali() {
            return oldValueQuali;
        }

        private void setOldValueQuali(String oldValueQuali) {
            this.oldValueQuali = oldValueQuali;
        }
    }

    private ObservableList<ComboAssigs> list; //llista dels cursos academics
    private ObservableList<ModelAlumne> LlistaTaula; //Llista dels alumnes trobats

    @FXML
    private ComboBox assignaturaComboBox;
    @FXML
    private ComboBox<String> cursComboBox;

    @FXML
    private TableView alumnes;
    @FXML
    private TableColumn<ModelAlumne, String> colNom;
    @FXML
    private TableColumn<ModelAlumne, String> colCognoms;
    @FXML
    private TableColumn<ModelAlumne, String> colDni;
    @FXML
    private TableColumn<ModelAlumne, Double> colNota;
    @FXML
    private TableColumn<ModelAlumne, String> colQualificacio;
    @FXML
    private TableColumn<ModelAlumne, Integer> colVegada;

    @FXML
    private Button editar;
    @FXML
    private Button guardar;
    @FXML
    private Button cancelar;


    @Override
    public void start(Stage primaryStage) throws Exception {
        String sceneFile = "escenes/notesAssignatura.fxml";
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(sceneFile));
        Scene scene = new Scene(root, 934, 500);
        primaryStage.setTitle("Notes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @FXML
    private void initialize() throws SQLException {
        importarAssignatures();

        LlistaTaula = null;
        alumnes.setEditable(false);
        editar.setDisable(true);
        guardar.setDisable(true);
        cancelar.setDisable(true);

        //permite que las celdas puedan seleccionarse individualmente
        alumnes.getSelectionModel().cellSelectionEnabledProperty().set(true);

        colNom.setCellValueFactory(new PropertyValueFactory<ModelAlumne, String>("nom"));
        colCognoms.setCellValueFactory(new PropertyValueFactory<ModelAlumne, String>("cognom"));
        colDni.setCellValueFactory(new PropertyValueFactory<ModelAlumne, String>("dni"));


        setupNotaColumn();

        colQualificacio.setCellValueFactory(new PropertyValueFactory<ModelAlumne, String>("qualificacio"));
        colVegada.setCellValueFactory(new PropertyValueFactory<ModelAlumne, Integer>("vegada"));

        cursComboBox.getItems().addAll("2018-2019", "2019-2020", "2020-2021", "2021-2022");
        assignaturaComboBox.setItems(list);
        assignaturaComboBox.getSelectionModel().selectFirst(); //Select the first element

        //ComboBox assignatures
        assignaturaComboBox.setCellFactory(new Callback<ListView<ComboAssigs>, ListCell<ComboAssigs>>() {
            @Override
            public ListCell call(ListView<ComboAssigs> param) {
                final ListCell<ComboAssigs> cell = new ListCell<ComboAssigs>() {
                    @Override
                    protected void updateItem(ComboAssigs c, boolean b) {
                        super.updateItem(c, b);

                        if (c != null) {
                            setText(c.nom);
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
        colNota.setCellValueFactory(new PropertyValueFactory<ModelAlumne, Double>("nota"));
        //permitir editar sobre la columna nota
        colNota.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        try {
            colNota.setOnEditCommit(event -> {
                Object value = event.getNewValue();
                if (value instanceof Double) {
                    Double novaNota = (Double) value;
                    if (novaNota <= 10.0 && novaNota >= 0.0) {
                        ModelAlumne m = (event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        m.setOldValueNota(m.getNota());
                        m.setOldValueQuali(m.getQualificacio());
                        m.setNota(novaNota);
                        m.setQualificacio(modificaQualificacio(novaNota));
                        m.setDirtyBit(1);
                        alumnes.refresh();
                    } else {
                        editCancel();
                        throw new NumberFormatException("La nota que s'ha introduit no es valida. (0 <= nota <= 10");
                    }
                } else {
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
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
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
            ComboAssigs ca = (ComboAssigs)assignaturaComboBox.getValue();
            int id = ca.getId();
            String curs = cursComboBox.getValue();

            HashMap<Integer, String> dadesTaula = ControladorDomini.getInstancia().cargarTaulaNotes(id, curs);

            mostrarResultats(dadesTaula);
            editar.setDisable(false);
        }
    }

    private void mostrarResultats(HashMap<Integer, String> dadesTaula) {
        alumnes.getItems().clear();
        dadesTaula.forEach((k, v) -> {
            SimpleIntegerProperty id = new SimpleIntegerProperty(k);

            String[] valor = v.split("/");
            //am.VEGADA, am.nota, am.qualificacio, e.nom, e.cognom, e.DNI" +
            SimpleIntegerProperty vegada = new SimpleIntegerProperty(Integer.parseInt(valor[0]));
            SimpleDoubleProperty nota = new SimpleDoubleProperty(Double.parseDouble(valor[1]));
            SimpleStringProperty qualificacio = new SimpleStringProperty(valor[2]);
            SimpleStringProperty nom = new SimpleStringProperty(valor[3]);
            SimpleStringProperty cognoms = new SimpleStringProperty(valor[4]);
            SimpleStringProperty dni = new SimpleStringProperty(valor[5]);


            ModelAlumne m = new ModelAlumne(id, nom, cognoms, dni, nota, qualificacio, vegada);
            //ModelAlumne m = new ModelAlumne(nom, cognoms, dni);
            alumnes.getItems().add(m);
            //System.out.println(m.id + " " + " " + m.getNom() + " " + m.getCognom()+ " " + " " + m.getDni() + " "
            //+ " " +  m.getNota() + " " + m.getQualificacio() + " " +  m.getVegada());
        });
        alumnes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    private void importarAssignatures() throws SQLException {
        List<String> assigs = ControladorDomini.getInstancia().importarAssignatures();
        List<ComboAssigs> combo = new ArrayList<ComboAssigs>();
        for (String s : assigs) {
            String[] arrayS = s.split("/");
            int id = Integer.parseInt(arrayS[0]);
            String nom = arrayS[1];
            ComboAssigs ca = new ComboAssigs(id, nom);
            combo.add(ca);
        }
        list = FXCollections.observableArrayList(combo);
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
            List<ModelAlumne> dades =  alumnes.getItems();
            boolean updated = false;
            for (ModelAlumne d : dades) {
                if (d.getDirtyBit() == 1) {
                    int idAssignaturaMatricula = d.getId();
                    double nota = d.getNota();
                    String quali = d.getQualificacio();

                    String sql = "UPDATE Assignaturesmatricula SET nota = " + nota + ", qualificacio = '" +
                            quali + "' WHERE IdAssignaturaMatricula = " + idAssignaturaMatricula;
                    int status = ControladorDomini.executeQuery(sql);
                    if (status > 0) {
                        d.setDirtyBit(0); d.setOldValueNota(null); d.setOldValueQuali(null);
                        updated = true;
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
            List<ModelAlumne> dades =  alumnes.getItems();
            for (ModelAlumne d : dades) {
                if (d.getDirtyBit() == 1) {
                    d.setNota(d.getOldValueNota());
                    d.setQualificacio(d.getOldValueQuali());
                    d.setDirtyBit(0); d.setOldValueNota(null); d.setOldValueQuali(null);
                }
            }
            mostrarAlertaGuardarCancelar("Cancelar", "S'han cancel·lat tots els canvis");
            alumnes.refresh();
        }
    }

    @FXML
    public void editStart() {
    }

    @FXML
    public void editCommit(TextFieldTableCell<ModelAlumne, Double> t) {
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

    public static void main(String[] args) {
        launch(args);
    }
}
