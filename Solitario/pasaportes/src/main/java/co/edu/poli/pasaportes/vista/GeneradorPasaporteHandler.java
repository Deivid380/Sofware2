package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.GeneracionPasaporte;
import co.edu.poli.pasaportes.modelo.Pasaporte;
import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import javafx.scene.control.TextArea;

public class GeneradorPasaporteHandler extends VerificadorHandler {
    private GeneracionPasaporte generador = new GeneracionPasaporte();

    public GeneradorPasaporteHandler() {
        super("GENERADOR PASAPORTE");
    }

    @Override
    protected boolean puedeVerificar(SolicitudPasaporte solicitud) {
        return true; // Último eslabón, siempre puede generar si llega hasta aquí.
    }

    @Override
    protected void procesar(SolicitudPasaporte solicitud, TextArea log) {
        String resultado = generador.generar(new Pasaporte(), solicitud.getTitularId());
        log.appendText("   ✓ Pasaporte generado: " + resultado + "\n");
    }
}