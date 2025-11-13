package co.edu.poli.pasaportes.servicios;

/**
 * Estado: Frontera Cerrada
 * Desde FronteraCerrada -> EstadoNormal, SolicitudVisa
 */
public class EstadoFronteraCerrada implements State {

    private final PaisStateAdapter contexto;

    public EstadoFronteraCerrada(PaisStateAdapter contexto) {
        this.contexto = contexto;
    }

    @Override
    public void toEstadoNormal() {
        contexto.setEstado(new EstadoNormal(contexto));
    }

    @Override
    public void toEnRevision() {
        throw new IllegalStateException("No es posible pasar directamente de 'Frontera cerrada' a 'En revisión'.");
    }

    @Override
    public void toSolicitudVisa() {
        contexto.setEstado(new EstadoSolicitudVisa(contexto));
    }

    @Override
    public void toFronteraCerrada() {
        // ya está en frontera cerrada
    }

    @Override
    public String getNombre() {
        return "Estado Frontera Cerrada";
    }
}
