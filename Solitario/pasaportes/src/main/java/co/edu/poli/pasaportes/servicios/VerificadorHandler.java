package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import javafx.scene.control.TextArea;

// HANDLER ABSTRACTO (copia exacta de refactoring.guru pero con tus datos)
public abstract class VerificadorHandler {
    protected VerificadorHandler siguiente;
    protected String nombreVerificacion;

    public VerificadorHandler(String nombreVerificacion) {
        this.nombreVerificacion = nombreVerificacion;
    }

    // Método de refactoring.guru: setNext
    public VerificadorHandler setSiguiente(VerificadorHandler siguiente) {
        this.siguiente = siguiente;
        return siguiente; // Permite encadenar
    }

    // Método de refactoring.guru: handleRequest
    public final void verificar(SolicitudPasaporte solicitud, TextArea log) {
        log.appendText("🔍 [" + nombreVerificacion + "] Iniciando verificación...\n");
        
        if (puedeVerificar(solicitud)) {
            procesar(solicitud, log);
            log.appendText("✅ [" + nombreVerificacion + "] APROBADO\n\n");
        } else if (siguiente != null) {
            log.appendText("⏭️ [" + nombreVerificacion + "] Reenviando a siguiente...\n\n");
            siguiente.verificar(solicitud, log);
        } else {
            log.appendText("❌ [" + nombreVerificacion + "] RECHAZADO - Fin de cadena\n\n");
        }
    }

    protected abstract boolean puedeVerificar(SolicitudPasaporte solicitud);
    protected abstract void procesar(SolicitudPasaporte solicitud, TextArea log);
}
