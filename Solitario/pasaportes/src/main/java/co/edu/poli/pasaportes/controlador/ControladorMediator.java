package co.edu.poli.pasaportes.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import co.edu.poli.pasaportes.servicios.Mediator;

public class ControladorMediator {

    @FXML
    private Button cambioPas;

    @FXML
    private Button cambioPol;

    @FXML
    private TextField cambiosPol;

    @FXML
    private Button enviarCan;

    @FXML
    private Button enviarMig;

    @FXML
    private TextField id;

    @FXML
    private TextArea mensajeCan;

    @FXML
    private TextArea mensajeMig;

    private Mediator mediator = new Mediator();

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    void CambioPas(ActionEvent event) {
        String idPas = id.getText();
        if (idPas.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el ID del pasaporte.");
        } else {
            String resultado = mediator.notificarCambioPasaporte(idPas);
            mostrarAlerta("Cambio de Pasaporte", resultado);
        }
    }

    @FXML
    void CambioPol(ActionEvent event) {
        String cambio = cambiosPol.getText();
        if (cambio.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar el cambio realizado por Policía.");
        } else {
            String resultado = mediator.notificarCambioPolicia(cambio);
            mostrarAlerta("Cambio Policía", resultado);
        }
    }

    @FXML
    void EnviarCan(ActionEvent event) {
        String mensaje = mensajeCan.getText();
        if (mensaje.isEmpty()) {
            mostrarAlerta("Error", "Debe escribir un mensaje para Policía.");
        } else {
            String resultado = mediator.enviarDeCancilleriaAPolicia(mensaje);
            mostrarAlerta("Mensaje Enviado", resultado);
        }
    }

    @FXML
    void EnviarMig(ActionEvent event) {
        String mensaje = mensajeMig.getText();
        if (mensaje.isEmpty()) {
            mostrarAlerta("Error", "Debe escribir un mensaje para Cancillería.");
        } else {
            String resultado = mediator.enviarDeMigracionACancilleria(mensaje);
            mostrarAlerta("Mensaje Enviado", resultado);
        }
    }
}
