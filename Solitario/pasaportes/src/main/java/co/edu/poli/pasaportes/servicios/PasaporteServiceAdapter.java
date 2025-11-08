package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Pasaporte;
import java.util.ArrayList;
import java.util.List;

public class PasaporteServiceAdapter {

    private List<Pasaporte> pasaportes = new ArrayList<>();

    public String agregarPasaporte(Pasaporte p) {
        pasaportes.add(p);
        return "Pasaporte agregado: " + p.getId();
    }

    public String eliminarPasaporte(Pasaporte p) {
        pasaportes.remove(p);
        return "Pasaporte eliminado: " + p.getId();
    }

    public List<Pasaporte> listarPasaportes() {
        return pasaportes;
    }
}
