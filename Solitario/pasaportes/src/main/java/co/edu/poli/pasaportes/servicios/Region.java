package co.edu.poli.pasaportes.servicios;

import java.util.ArrayList;
import java.util.List;

public class Region implements EspacioGeografico {
    private String nombre;
    private List<EspacioGeografico> espacios = new ArrayList<>();

    public Region(String nombre) { this.nombre = nombre; }

    public void agregarEspacio(EspacioGeografico espacio) { espacios.add(espacio); }
    public void eliminarEspacio(EspacioGeografico espacio) { espacios.remove(espacio); }

    @Override
    public void mostrarInformacion() {
        System.out.println("Región: " + nombre);
        for (EspacioGeografico e : espacios) {
            e.mostrarInformacion();
        }
    }

    public String getNombre() { return nombre; }
}
