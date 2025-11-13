package co.edu.poli.pasaportes.servicios;

/**
 * Interfaz de estado para el State Pattern.
 * Cada método intenta realizar una transición concreta.
 */
public interface State {
    void toEstadoNormal();
    void toEnRevision();
    void toSolicitudVisa();
    void toFronteraCerrada();

    String getNombre();
}
