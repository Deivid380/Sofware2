package co.edu.poli.pasaportes.modelo;

public class PasaporteOrdinario extends Pasaporte {

    public PasaporteOrdinario(String id, String fechaExp, Titular titular, Pais pais, ElementoSeguridad seguridad) {
        super(id, fechaExp, titular, pais, seguridad);
    }

    @Override
    public String tipoPasaporte() {
        return "Pasaporte Ordinario";
    }
}
