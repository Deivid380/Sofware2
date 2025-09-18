package co.edu.poli.Servicios;
import co.edu.poli.modelo.*;

public class FactoryDiplomatico implements FactoryPasaporte {

    @Override
    public Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais) {
        if (tipo.equalsIgnoreCase("Diplomatico")) {
            return new PasaporteDiplomatico(id, fechaExp, titular, pais);
        }
        return null;
    }
    
}
