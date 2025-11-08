package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Pasaporte;

public class EliminarPasaporteComando implements Comando {

    private PasaporteServiceAdapter servicio;
    private Pasaporte pasaporte;

    public EliminarPasaporteComando(PasaporteServiceAdapter servicio, Pasaporte pasaporte) {
        this.servicio = servicio;
        this.pasaporte = pasaporte;
    }

    @Override
    public void ejecutar() {
        servicio.eliminarPasaporte(pasaporte);
    }

    @Override
    public void deshacer() {
        servicio.agregarPasaporte(pasaporte);
    }
}
