package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import co.edu.poli.pasaportes.servicios.VerificacionAntecedentes; // Corrected import
import javafx.scene.control.TextArea;

public class VerificadorAntecedentesHandler extends VerificadorHandler {
    private VerificacionAntecedentes verificador = new VerificacionAntecedentes();

    public VerificadorAntecedentesHandler() {
        super("VERIFICADOR ANTECEDENTES");
    }

    @Override
    protected boolean puedeVerificar(SolicitudPasaporte solicitud) {
        // Assuming VerificacionAntecedentes.verificar returns true if antecedents are OK
        return verificador.verificar(solicitud.getTitularId(), "internacional");
    }

    @Override
    protected void procesar(SolicitudPasaporte solicitud, TextArea log) {
        log.appendText("   ✓ Antecedentes penales OK\n");
    }
}
