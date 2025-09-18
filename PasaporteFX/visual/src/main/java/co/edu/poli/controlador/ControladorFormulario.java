package co.edu.poli.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import co.edu.poli.repositorio.PasaporteRepo;
import co.edu.poli.modelo.Pasaporte;
import co.edu.poli.modelo.PasaporteOrdinario;
import co.edu.poli.modelo.PasaporteDiplomatico;
import co.edu.poli.modelo.Titular;
import co.edu.poli.modelo.Pais;

import java.time.LocalDate;

public class ControladorFormulario {

    @FXML
    private MenuButton passportTypeMenuButton;

    @FXML
    private Button createButton;

    @FXML
    private Button readButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private MenuItem ordinaryPassportMenuItem;

    @FXML
    private MenuItem diplomaticPassportMenuItem;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    private String tipoPasaporte; 
    private PasaporteRepo pasaporteRepo;

    @FXML
    void initialize() {
        this.pasaporteRepo = new PasaporteRepo();

        ordinaryPassportMenuItem.setOnAction(e -> {
            tipoPasaporte = "Ordinario";
            passportTypeMenuButton.setText("Pasaporte Ordinario");
        });

        diplomaticPassportMenuItem.setOnAction(e -> {
            tipoPasaporte = "Diplomático";
            passportTypeMenuButton.setText("Pasaporte Diplomático");
        });
    }

    private boolean validarCamposCompletos() {
        if (idTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || tipoPasaporte == null) {
            mostrarAlerta("Campos Incompletos", "Debe llenar ID, Nombre y seleccionar tipo de pasaporte.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        idTextField.clear();
        nameTextField.clear();
        tipoPasaporte = null;
        passportTypeMenuButton.setText("Seleccionar tipo");
    }

    @FXML
    void crearPasaporte(ActionEvent event) {
        if (validarCamposCompletos()) {
            try {
                Integer.parseInt(idTextField.getText());

                Titular titular = new Titular(nameTextField.getText(), nameTextField.getText(), "COL");
                Pais pais = new Pais("COL", "Colombia");
                LocalDate fechaExp = LocalDate.now().plusYears(10);

                Pasaporte nuevoPasaporte = null;

                if ("Ordinario".equals(tipoPasaporte)) {
                    nuevoPasaporte = new PasaporteOrdinario.Builder()
                            .getid(idTextField.getText())
                            .getfechaExp(fechaExp.toString())
                            .gettitular(titular)
                            .getpais(pais)
                            .build();
                } else if ("Diplomático".equals(tipoPasaporte)) {
                    nuevoPasaporte = new PasaporteDiplomatico(idTextField.getText(), fechaExp.toString(), titular, pais);
                }

                String resultado = pasaporteRepo.create(nuevoPasaporte, tipoPasaporte);
                mostrarAlerta("Información", resultado, Alert.AlertType.INFORMATION);
                limpiarCampos();

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de Formato", "El ID del pasaporte debe ser un número entero.", Alert.AlertType.ERROR);
            } catch (Exception e) {
                mostrarAlerta("Error de Creación", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    // ---------------------------
    // LEER
    // ---------------------------
    @FXML
    void leerPasaporte(ActionEvent event) {
        if (idTextField.getText().isEmpty()) {
            mostrarAlerta("Campo Vacío", "Debe ingresar el ID para buscar.", Alert.AlertType.WARNING);
            return;
        }

        if (tipoPasaporte == null) {
            mostrarAlerta("Campo Vacío", "Debe seleccionar un tipo de pasaporte para buscar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Integer.parseInt(idTextField.getText());
            Pasaporte pasaporte = pasaporteRepo.read(idTextField.getText(), tipoPasaporte);

            if (pasaporte != null) {
                idTextField.setText(pasaporte.getId());
                nameTextField.setText(pasaporte.getTitular() != null ? pasaporte.getTitular().getNombre() : "N/A");
                mostrarAlerta("Éxito", "Pasaporte encontrado.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("No Encontrado", "No se encontró un pasaporte con el ID: " + idTextField.getText(), Alert.AlertType.INFORMATION);
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "El ID del pasaporte debe ser un número entero.", Alert.AlertType.ERROR);
        }
    }

    // ---------------------------
    // ACTUALIZAR
    // ---------------------------
    @FXML
    void actualizarPasaporte(ActionEvent event) {
        if (validarCamposCompletos()) {
            try {
                Integer.parseInt(idTextField.getText());

                Titular titularActualizado = new Titular(nameTextField.getText(), nameTextField.getText(), "COL");
                LocalDate fechaExp = LocalDate.now().plusYears(5);

                Pasaporte pasaporteActualizado = null;

                if ("Ordinario".equals(tipoPasaporte)) {
                    pasaporteActualizado = new PasaporteOrdinario.Builder()
                            .getid(idTextField.getText())
                            .getfechaExp(fechaExp.toString())
                            .gettitular(titularActualizado)
                            .getpais(new Pais("COL", "Colombia"))
                            .build();
                } else if ("Diplomático".equals(tipoPasaporte)) {
                    pasaporteActualizado = new PasaporteDiplomatico(idTextField.getText(), fechaExp.toString(), titularActualizado, new Pais("COL", "Colombia"));
                }

                String resultado = pasaporteRepo.update(pasaporteActualizado, tipoPasaporte);
                mostrarAlerta("Actualización", resultado, Alert.AlertType.INFORMATION);
                limpiarCampos();

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de Formato", "El ID del pasaporte debe ser un número entero.", Alert.AlertType.ERROR);
            }
        }
    }

    // ---------------------------
    // ELIMINAR
    // ---------------------------
    @FXML
    void eliminarPasaporte(ActionEvent event) {
        if (idTextField.getText().isEmpty()) {
            mostrarAlerta("Campo Vacío", "Debe ingresar el ID para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        if (tipoPasaporte == null) {
            mostrarAlerta("Campo Vacío", "Debe seleccionar un tipo de pasaporte para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Integer.parseInt(idTextField.getText()); 
            String resultado = pasaporteRepo.delete(idTextField.getText(), tipoPasaporte);
            mostrarAlerta("Eliminación", resultado, Alert.AlertType.INFORMATION);
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "El ID del pasaporte debe ser un número entero.", Alert.AlertType.ERROR);
        }
    }
}
