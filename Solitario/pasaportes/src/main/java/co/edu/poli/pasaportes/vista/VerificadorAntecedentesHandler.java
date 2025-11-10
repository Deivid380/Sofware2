package co.edu.poli.pasaportes.vista;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import javafx.scene.control.TextArea;

public class VerificadorAntecedentesHandler extends VerificadorHandler {
    private VerificacionAntecedentes verificador = new VerificacionAntecedentes();

    public VerificadorAntecedentesHandler() {
        super("VERIFICADOR ANTECEDENTES");
    }

    @Override
    protected boolean puedeVerificar(SolicitudPasaporte solicitud) {
        // Simulamos una verificación de antecedentes que siempre pasa si no contiene 'X'
        return verificador.verificar(solicitud.getTitularId(), "internacional");
    }

    @Override
    protected void procesar(SolicitudPasaporte solicitud, TextArea log) {
        log.appendText("   ✓ Antecedentes penales OK\n");
    }
}