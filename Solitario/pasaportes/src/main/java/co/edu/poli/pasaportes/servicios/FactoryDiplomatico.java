package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.*;

public class FactoryDiplomatico implements FactoryPasaporte {

    @Override
    public Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais) {
        if ("Diplomatico".equalsIgnoreCase(tipo) || "Diplomático".equalsIgnoreCase(tipo)) {
            // Constructor del modelo SIN seguridad
            return new PasaporteDiplomatico(id, fechaExp, titular, pais, null);
        }
        return null;
    }
}
