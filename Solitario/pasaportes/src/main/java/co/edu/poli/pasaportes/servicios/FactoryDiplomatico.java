package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.*;

public class FactoryDiplomatico implements FactoryPasaporte {

    @Override
    public Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais) {
        if (tipo != null && (tipo.equalsIgnoreCase("Diplomatico") || tipo.equalsIgnoreCase("Diplomático"))) {
            return new PasaporteDiplomatico(id, fechaExp, titular, pais);
        }
        throw new IllegalArgumentException("Tipo no válido para FactoryDiplomatico: " + tipo);
    }
}
