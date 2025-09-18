package co.edu.Servicios;

import co.edu.poli.modelo.*;

public class FactoryOrdinario implements FactoryPasaporte {

    @Override
    public Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais) {
        if (tipo.equalsIgnoreCase("Ordinario")) {
            return new PasaporteOrdinario(id, fechaExp, titular, pais);
        }
        return null;
    }
    
}
