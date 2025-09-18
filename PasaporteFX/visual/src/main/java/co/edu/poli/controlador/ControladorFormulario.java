package co.edu.poli.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class ControladorFormulario {

    @FXML
    private MenuButton Btt5;

    @FXML
    private Button btt1; // Create

    @FXML
    private Button btt2; // Read

    @FXML
    private Button btt3; // Update

    @FXML
    private Button btt4; // Delete

    @FXML
    private MenuItem btt6; // Ordinario

    @FXML
    private MenuItem btt7; // Diplomático

    @FXML
    private TextField txt1; // ID

    @FXML
    private TextField txt2; // Nombre

    private String tipoPasaporte; // se guarda el tipo seleccionado

    // ---------------------------
    // Manejo de menú desplegable
    // ---------------------------
    @FXML
    void initialize() {
        btt6.setOnAction(e -> {
            tipoPasaporte = "Ordinario";
            Btt5.setText("Pasaporte Ordinario");
        });

        btt7.setOnAction(e -> {
            tipoPasaporte = "Diplomático";
            Btt5.setText("Pasaporte Diplomático");
        });
    }

    // ---------------------------
    // Validación de campos
    // ---------------------------
    private boolean validarCampos() {
        if (txt1.getText().isEmpty() || txt2.getText().isEmpty() || tipoPasaporte == null) {
            mostrarAlerta("Error", "Debe llenar ID, Nombre y seleccionar tipo de pasaporte.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ---------------------------
    // Acciones de botones
    // ---------------------------
    @FXML
    void click(ActionEvent event) {
        Object source = event.getSource();

        if (source == btt1) { // Create
            if (validarCampos()) {
                System.out.println("Crear pasaporte: " + txt1.getText() + ", " + txt2.getText() + ", " + tipoPasaporte);
                // Aquí puedes llamar a PasaporteRepo.create(...)
            }
        } else if (source == btt2) { // Read
            if (txt1.getText().isEmpty()) {
                mostrarAlerta("Error", "Debe ingresar el ID para buscar.");
            } else {
                System.out.println("Buscar pasaporte con ID: " + txt1.getText());
                // Aquí puedes llamar a PasaporteRepo.read(...)
            }
        } else if (source == btt3) { // Update
            if (validarCampos()) {
                System.out.println("Actualizar pasaporte: " + txt1.getText() + " con nuevo nombre: " + txt2.getText());
                // Aquí puedes llamar a PasaporteRepo.update(...)
            }
        } else if (source == btt4) { // Delete
            if (txt1.getText().isEmpty()) {
                mostrarAlerta("Error", "Debe ingresar el ID para eliminar.");
            } else {
                System.out.println("Eliminar pasaporte con ID: " + txt1.getText());
                // Aquí puedes llamar a PasaporteRepo.delete(...)
            }
        }
    }
}
