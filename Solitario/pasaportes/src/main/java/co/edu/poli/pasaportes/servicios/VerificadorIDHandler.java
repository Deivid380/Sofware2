package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import co.edu.poli.pasaportes.modelo.VerificacionID; // Corrected import
import javafx.scene.control.TextArea;

public class VerificadorIDHandler extends VerificadorHandler {
    private VerificacionID verificador = new VerificacionID();

    public VerificadorIDHandler() {
        super("VERIFICADOR ID");
    }

    @Override
    protected boolean puedeVerificar(SolicitudPasaporte solicitud) {
        // Assuming VerificacionID.verificar returns true if documentation is complete
        // and false otherwise. The user's example implies this.
        return solicitud.isDocumentacionCompleta();
    }

    @Override
    protected void procesar(SolicitudPasaporte solicitud, TextArea log) {
        boolean resultado = verificador.verificar(solicitud.getTitularId(), "pasaporte");
        log.appendText("   ✓ Documentación completa: " + resultado + "\n");
    }
}