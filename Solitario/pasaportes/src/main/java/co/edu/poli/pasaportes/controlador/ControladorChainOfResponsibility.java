package co.edu.poli.pasaportes.controlador;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import co.edu.poli.pasaportes.servicios.GeneradorPasaporteHandler;
import co.edu.poli.pasaportes.servicios.VerificadorAntecedentesHandler;
import co.edu.poli.pasaportes.servicios.VerificadorHandler;
import co.edu.poli.pasaportes.servicios.VerificadorIDHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControladorChainOfResponsibility {

    @FXML
    private Button btnProcesar;

    @FXML
    private CheckBox chkDocumentacion;

    @FXML
    private TextArea log;

    @FXML
    private TextField txtId;

    private VerificadorHandler cadena;

    @FXML
    public void initialize() {
        // CONSTRUYE LA CADENA
        cadena = new VerificadorIDHandler();
        cadena.setSiguiente(new VerificadorAntecedentesHandler())
              .setSiguiente(new GeneradorPasaporteHandler());
    }

    @FXML
    void procesarSolicitud(ActionEvent event) {
        SolicitudPasaporte solicitud = new SolicitudPasaporte(
            txtId.getText(),
            "V-01",
            2500.0,
            chkDocumentacion.isSelected()
        );
        
        log.clear();
        log.appendText("═══════════════════════════════════════\n");
        log.appendText("INICIANDO PROCESO\n");
        log.appendText("═══════════════════════════════════════\n\n");
        
        cadena.verificar(solicitud, log);
    }
}