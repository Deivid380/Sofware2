package co.edu.poli.pasaportes.servicios;

/**
 * Estado: En revisión
 * Desde EnRevision -> SolicitudVisa
 */
public class EstadoEnRevision implements State {

    private final PaisStateAdapter contexto;

    public EstadoEnRevision(PaisStateAdapter contexto) {
        this.contexto = contexto;
    }

    @Override
    public void toEstadoNormal() {
        throw new IllegalStateException("No es posible ir directamente de 'En revisión' a 'Estado Normal'.");
    }

    @Override
    public void toEnRevision() {
        // ya está en revisión
    }

    @Override
    public void toSolicitudVisa() {
        contexto.setEstado(new EstadoSolicitudVisa(contexto));
    }

    @Override
    public void toFronteraCerrada() {
        throw new IllegalStateException("No es posible cerrar fronteras desde 'En revisión'.");
    }

    @Override
    public String getNombre() {
        return "Estado En Revisión";
    }
}
