package co.edu.poli.pasaportes.modelo;

public class PasaporteDiplomatico extends Pasaporte {

    public PasaporteDiplomatico(String id, String fechaExp, Titular titular, Pais pais, ElementoSeguridad seguridad) {
        super(id, fechaExp, titular, pais, seguridad);
    }

    @Override
    public String tipoPasaporte() {
        return "Pasaporte Diplomático";
    }
}
