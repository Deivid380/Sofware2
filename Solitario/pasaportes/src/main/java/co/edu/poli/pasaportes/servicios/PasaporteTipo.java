package co.edu.poli.pasaportes.servicios;

public class PasaporteTipo {

    // Atributos intrínsecos (compartidos)
    private String colorCubierta; // colorCubierta: String
    private String idioma; // idioma: String
    private String paisInicio; // paisInicio: String

    /**
     * Constructor para PasaporteTipo.
     * @param paisInicio El país emisor del pasaporte.
     * @param colorCubierta El color de la cubierta del pasaporte.
     * @param idioma El idioma principal del pasaporte.
     */
    public PasaporteTipo(String paisInicio, String colorCubierta, String idioma) {
        this.paisInicio = paisInicio;
        this.colorCubierta = colorCubierta;
        this.idioma = idioma;
        System.out.println("Creando nuevo objeto PasaporteTipo: " + paisInicio);
    }

    /**
     * Muestra los detalles del PasaporteTipo, junto con el estado extrínseco.
     * @param pasaporte Objeto que contiene el estado extrínseco.
     */
    // Asumiremos que el 'Object' pasado es un objeto de tipo 'Pasaporte' 
    // que contiene el estado extrínseco (no definido en el diagrama).
    public void mostrarDetalles(Object pasaporte) { // +mostrarDetalles(pasaporte: Object): void
        // Aquí se mostrarían los detalles intrínsecos (compartidos)
        System.out.println("\n--- Detalles del Tipo de Pasaporte (Intrínseco) ---");
        System.out.println("País de Origen: " + this.paisInicio);
        System.out.println("Color de Cubierta: " + this.colorCubierta);
        System.out.println("Idioma Principal: " + this.idioma);
        
        // Aquí se usaría el objeto 'pasaporte' para obtener detalles extrínsecos (ej. número de pasaporte, nombre del titular)
        // Como 'Pasaporte' no está definido, solo mostramos el tipo.
        System.out.println("Estado Extrínseco (Ej. Número de Pasaporte): " + pasaporte.toString());
    }

    // Getters para los atributos (opcional, pero útil para la fábrica)
    public String getPaisInicio() {
        return paisInicio;
    }
    
    public String getColorCubierta() {
        return colorCubierta;
    }

    public String getIdioma() {
        return idioma;
    }
}