package co.edu.poli.pasaportes.servicios;

// Debe importar Ciudad desde su paquete original (modelo)
import co.edu.poli.pasaportes.modelo.Ciudad; 

public class CiudadAdapter implements EspacioGeografico {
    private Ciudad ciudad;

    public CiudadAdapter(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public void mostrarInformacion() {
        // La indentación se ajusta un poco para mejorar la visualización en el TextArea
        System.out.println("  Ciudad: " + ciudad.getNombre() + " (" + ciudad.getCodigo() + ")");
    }
}