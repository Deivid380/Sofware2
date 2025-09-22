package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.*;

public class FactoryOrdinario implements FactoryPasaporte {

    @Override
    public Pasaporte crearPasaporte(String tipo, String id, String fechaExp, Titular titular, Pais pais) {
        if (tipo.equalsIgnoreCase("Ordinario")) {
            return new PasaporteOrdinario.Builder()
                    .getid(id)
                    .getfechaExp(fechaExp)
                    .gettitular(titular)
                    .getpais(pais)
                    .build();
        }
        return null;
    }
}
