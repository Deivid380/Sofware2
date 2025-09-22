package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Pais;
import co.edu.poli.pasaportes.modelo.Pasaporte;
import co.edu.poli.pasaportes.modelo.Titular;

public interface FactoryPasaporte {
    Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais);
}
