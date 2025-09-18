package co.edu.poli.Servicios;

import co.edu.poli.modelo.*;

public interface FactoryPasaporte { 

    
 public Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais);
    
}
