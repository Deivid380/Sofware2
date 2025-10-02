package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Titular;

public class TitularHistorialDecorator {
    private Titular titular;
    private String historialViajes;

    public TitularHistorialDecorator(Titular titular) {
        this.titular = titular;
        this.historialViajes = "";
    }

    public void agregarViaje(String viaje) {
        historialViajes += (historialViajes.isEmpty() ? "" : " | ") + viaje;
    }

    public String getHistorialViajes() {
        return historialViajes;
    }

    public Titular getTitularBase() {
        return titular;
    }

    @Override
    public String toString() {
        return titular.getNombre() + " (" + titular.getNacionalidad() + ")" +
               " - Historial: " + historialViajes;
    }
}
