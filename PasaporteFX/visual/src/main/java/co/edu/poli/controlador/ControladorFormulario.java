package co.edu.poli.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

// Importaciones para la lógica de negocio y el modelo
import co.edu.poli.repositorio.PasaporteRepo;
import co.edu.poli.modelo.Pasaporte;
import co.edu.poli.modelo.Titular; // Asumiendo que esta clase existe
import co.edu.poli.modelo.Pais;    // Asumiendo que esta clase existe
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

    private String tipoPasaporte; // se guarda el tipo seleccionado

    private PasaporteRepo pasaporteRepo;

    // ---------------------------
    // Manejo de menú desplegable
    // ---------------------------
    @FXML
    void initialize() {
        // Inicializamos el repositorio para poder usarlo en los métodos de acción.
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

    // ---------------------------
    // Validación de campos
    // ---------------------------
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

    // ---------------------------
    // Acciones de botones
    // ---------------------------

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
                // Validación adicional: Asegurarse de que el ID es un número
                Integer.parseInt(idTextField.getText());

                // --- NOTA: Creación de objetos simplificada para el ejemplo ---
                // Tu repositorio espera un Titular y un Pais. Aquí creamos objetos dummy.
                // Lo ideal sería tener campos en el formulario para estos datos.
                Titular titular = new Titular(nameTextField.getText(), nameTextField.getText(), "COL");
                Pais pais = new Pais("COL", "Colombia");
                LocalDate fechaExp = LocalDate.now().plusYears(10); // Fecha de expiración en 10 años

                Pasaporte nuevoPasaporte = new Pasaporte(idTextField.getText(), fechaExp.toString(), titular, pais);
                
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
            // Validar que el ID sea un número antes de buscar
            Integer.parseInt(idTextField.getText());
            Pasaporte pasaporte = pasaporteRepo.read(idTextField.getText(), tipoPasaporte);

            if (pasaporte != null) {
                // Rellenamos los campos con la información encontrada
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

    @FXML
    void actualizarPasaporte(ActionEvent event) {
        if (validarCamposCompletos()) {
            try {
                // Validar que el ID sea un número antes de actualizar
                Integer.parseInt(idTextField.getText());

                Titular titularActualizado = new Titular(nameTextField.getText(), nameTextField.getText(), "COL");
                LocalDate fechaExp = LocalDate.now().plusYears(5); // Nueva fecha de expiración
                Pasaporte pasaporteActualizado = new Pasaporte(idTextField.getText(), fechaExp.toString(), titularActualizado, null);

                if (tipoPasaporte == null) {
                    mostrarAlerta("Campo Vacío", "Debe seleccionar un tipo de pasaporte para actualizar.", Alert.AlertType.WARNING);
                    return;
                }
                String resultado = pasaporteRepo.update(pasaporteActualizado, tipoPasaporte);
                mostrarAlerta("Actualización", resultado, Alert.AlertType.INFORMATION);
                limpiarCampos();

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de Formato", "El ID del pasaporte debe ser un número entero.", Alert.AlertType.ERROR);
            }
        }
    }

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
            Integer.parseInt(idTextField.getText()); // Validar que el ID es un número
            String resultado = pasaporteRepo.delete(idTextField.getText(), tipoPasaporte);
            mostrarAlerta("Eliminación", resultado, Alert.AlertType.INFORMATION);
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "El ID del pasaporte debe ser un número entero.", Alert.AlertType.ERROR);
        }
    }
}
