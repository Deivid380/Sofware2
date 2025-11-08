package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Pasaporte;

public class AgregarPasaporteComando implements Comando {

    private PasaporteServiceAdapter servicio;
    private Pasaporte pasaporte;

    public AgregarPasaporteComando(PasaporteServiceAdapter servicio, Pasaporte pasaporte) {
        this.servicio = servicio;
        this.pasaporte = pasaporte;
    }

    @Override
    public void ejecutar() {
        servicio.agregarPasaporte(pasaporte);
    }
}
