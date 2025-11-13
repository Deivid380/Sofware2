package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Pais;

/**
 * Adapter que envuelve la clase modelo Pais (que no debe tocarse)
 * y provee la funcionalidad del State Pattern.
 *
 * Aquí están los métodos públicos que puede invocar el controlador u otros servicios.
 */
public class PaisStateAdapter {

    private final Pais pais;      // referencia al objeto modelo original (no se modifica su clase)
    private State estado;         // estado actual

    public PaisStateAdapter(Pais pais) {
        this.pais = pais;
        // por defecto iniciamos en Estado Normal
        this.estado = new EstadoNormal(this);
    }

    // ---------- métodos para que el controlador / servicios invoquen transiciones ----------

    public void irAEstadoNormal() {
        estado.toEstadoNormal();
    }

    public void irAEnRevision() {
        estado.toEnRevision();
    }

    public void solicitarVisa() {
        estado.toSolicitudVisa();
    }

    public void cerrarFrontera() {
        estado.toFronteraCerrada();
    }

    // ---------- helper / acceso ----------

    /**
     * Usado por los estados para cambiar el estado actual del contexto.
     */
    public void setEstado(State nuevoEstado) {
        this.estado = nuevoEstado;
        // aquí puedes añadir logging, notificaciones al mediator, etc.
        System.out.println("[" + pais.getNombre() + "] -> nuevo estado: " + nuevoEstado.getNombre());
    }

    public String obtenerEstadoActual() {
        return estado.getNombre();
    }

    public Pais getPais() {
        return pais;
    }
}
