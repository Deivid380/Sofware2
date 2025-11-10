package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Pasaporte;

public class AdapterPasaporte implements ServiceInterface {

    private Pasaporte pasaporte;

    public AdapterPasaporte(Pasaporte pasaporte) {
        this.pasaporte = pasaporte;
    }

    @Override
    public void accederInformacion(String idPasaporte) {
        if (pasaporte.getId().equals(idPasaporte)) {
            System.out.println("Accediendo a la información del pasaporte de: " 
                + pasaporte.getTitular().getNombre());
            System.out.println("Tipo: " + pasaporte.tipoPasaporte());
            System.out.println("Fecha Exp: " + pasaporte.getFechaExp());
            System.out.println("Seguridad: " + pasaporte.aplicarSeguridad());
        } else {
            System.out.println("Pasaporte no encontrado.");
        }
    }

    public Pasaporte getPasaporte() {
        return this.pasaporte;
    }
}
