package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.*;

public interface FactoryPasaporte {
    Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais);
}
