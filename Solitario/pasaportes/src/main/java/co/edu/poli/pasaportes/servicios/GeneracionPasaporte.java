package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.repositorio.PasaporteRepo;
import co.edu.poli.pasaportes.modelo.Pasaporte;

public class GeneracionPasaporte {
    private final PasaporteRepo repo;

    public GeneracionPasaporte(PasaporteRepo repo) {
        this.repo = repo;
    }

    /**
     * Crea el pasaporte usando el repo. Retorna el mensaje del repo.
     */
    public String generar(Pasaporte pasaporte, String tipo) {
        return repo.create(pasaporte, tipo);
    }
}

