package co.edu.poli.pasaportes.vista;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import co.edu.poli.pasaportes.modelo.VerificacionID;
import javafx.scene.control.TextArea;

public class VerificadorIDHandler extends VerificadorHandler {
    private VerificacionID verificador = new VerificacionID();

    public VerificadorIDHandler() {
        super("VERIFICADOR ID Y DOCUMENTACIÓN");
    }

    @Override
    protected boolean puedeVerificar(SolicitudPasaporte solicitud) {
        // Este manejador solo procesa si la documentación está completa.
        return solicitud.isDocumentacionCompleta();
    }

    @Override
    protected void procesar(SolicitudPasaporte solicitud, TextArea log) {
        boolean resultado = verificador.verificar(solicitud.getTitularId(), "pasaporte");
        log.appendText("   ✓ Verificación de ID simulada: " + resultado + "\n");
    }
}