package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import co.edu.poli.pasaportes.servicios.GeneracionPasaporte; // Corrected import
import co.edu.poli.pasaportes.modelo.Pasaporte; // Corrected import
import javafx.scene.control.TextArea;

public class GeneradorPasaporteHandler extends VerificadorHandler {
    private GeneracionPasaporte generador = new GeneracionPasaporte();

    public GeneradorPasaporteHandler() {
        super("GENERADOR PASAPORTE");
    }

    @Override
    protected boolean puedeVerificar(SolicitudPasaporte solicitud) {
        return true; // Último eslabón, siempre puede generar
    }

    @Override
    protected void procesar(SolicitudPasaporte solicitud, TextArea log) {
        Pasaporte pasaporte = new Pasaporte(solicitud.getTipoPasaporte());
        String resultado = generador.generar(pasaporte, solicitud.getTipoPasaporte());
        log.appendText("   ✓ Pasaporte generado: " + resultado + "\n");
    }
}