package co.edu.poli.pasaportes.servicios;

/**
 * Estado: Solicitud de Visa
 * Desde SolicitudVisa -> EstadoNormal, FronteraCerrada
 */
public class EstadoSolicitudVisa implements State {

    private final PaisStateAdapter contexto;

    public EstadoSolicitudVisa(PaisStateAdapter contexto) {
        this.contexto = contexto;
    }

    @Override
    public void toEstadoNormal() {
        contexto.setEstado(new EstadoNormal(contexto));
    }

    @Override
    public void toEnRevision() {
        throw new IllegalStateException("No es posible pasar de 'Solicitud de Visa' a 'En revisión'.");
    }

    @Override
    public void toSolicitudVisa() {
        // ya está en solicitud de visa
    }

    @Override
    public void toFronteraCerrada() {
        contexto.setEstado(new EstadoFronteraCerrada(contexto));
    }

    @Override
    public String getNombre() {
        return "Estado Solicitud de Visa";
    }
}
