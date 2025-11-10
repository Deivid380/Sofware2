package co.edu.poli.pasaportes.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

import co.edu.poli.pasaportes.servicios.AdapterPasaporte;
import co.edu.poli.pasaportes.servicios.ContextoSeguridad;
import co.edu.poli.pasaportes.servicios.ISeguridadPasaporte;
import co.edu.poli.pasaportes.servicios.SeguridadBiometricaService;
import co.edu.poli.pasaportes.servicios.SeguridadBlockchainService;
import co.edu.poli.pasaportes.servicios.SeguridadChipService;

public class ControladorStrategy {

    @FXML
    private TextArea areaResultado;
    @FXML
    private ToggleGroup grupoEstrategias;
    @FXML
    private RadioButton rbChip;
    @FXML
    private RadioButton rbBiometrica;
    @FXML
    private RadioButton rbBlockchain;

    private ContextoSeguridad contexto;
    private ISeguridadPasaporte chipService = new SeguridadChipService();
    private ISeguridadPasaporte biometricaService = new SeguridadBiometricaService();
    private ISeguridadPasaporte blockchainService = new SeguridadBlockchainService();

    public void initData(AdapterPasaporte adaptadorInicial) {

        this.contexto = new ContextoSeguridad(adaptadorInicial, chipService);
        areaResultado.setText("Contexto listo. Estrategia inicial: Chip.");
    }

    @FXML
    void initialize() {

        grupoEstrategias.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (contexto == null)
                return;

            if (newToggle == rbChip) {
                contexto.setEstrategia(chipService);
                areaResultado.setText("Contexto: Estrategia cambiada a Chip.");
            } else if (newToggle == rbBiometrica) {
                contexto.setEstrategia(biometricaService);
                areaResultado.setText("Contexto: Estrategia cambiada a Biométrica.");
            } else if (newToggle == rbBlockchain) {
                contexto.setEstrategia(blockchainService);
                areaResultado.setText("Contexto: Estrategia cambiada a Blockchain.");
            }
        });
    }

    @FXML
    void ejecutarEstrategia(ActionEvent event) {
        if (contexto != null) {

            String resultado = contexto.ejecutarSeguridad();
            areaResultado.setText(resultado);
        } else {
            areaResultado.setText("ERROR: El Contexto no está listo.");
        }
    }
}