package co.edu.poli.pasaportes.servicios;

/**
 * Estado: Normal
 * Desde Normal -> EnRevision, SolicitudVisa, FronteraCerrada
 */
public class EstadoNormal implements State {

    private final PaisStateAdapter contexto;

    public EstadoNormal(PaisStateAdapter contexto) {
        this.contexto = contexto;
    }

    @Override
    public void toEstadoNormal() {
        // ya está en estado normal, no hacer nada
    }

    @Override
    public void toEnRevision() {
        contexto.setEstado(new EstadoEnRevision(contexto));
    }

    @Override
    public void toSolicitudVisa() {
        contexto.setEstado(new EstadoSolicitudVisa(contexto));
    }

    @Override
    public void toFronteraCerrada() {
        contexto.setEstado(new EstadoFronteraCerrada(contexto));
    }

    @Override
    public String getNombre() {
        return "Estado Normal";
    }
}
