package co.edu.poli.pasaportes.modelo;

public class GeneracionPasaporte {
    public String generar(Pasaporte pasaporte, String titularId) {
        // Lógica de generación de pasaporte
        System.out.println("Generando pasaporte para " + titularId);
        return "P-" + pasaporte.getId() + "-" + titularId; // Simulación
    }
}