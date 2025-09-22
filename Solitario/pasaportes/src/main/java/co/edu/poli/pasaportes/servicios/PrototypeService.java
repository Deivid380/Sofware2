package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Titular;

public class PrototypeService {

    public Titular clonarTitular(Titular original) {
        return new Titular(
            original.getId(),
            original.getNombre(),
            original.getNacionalidad()
        );
    }
}
