package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.*;

public class FactoryOrdinario implements FactoryPasaporte {

    @Override
    public Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais) {
        if ("Ordinario".equalsIgnoreCase(tipo)) {
           
            return new PasaporteOrdinario(id, fechaExp, titular, pais, null);
        }
        return null;
    }
}
