package co.edu.poli.pasaportes.servicios;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {

    // Cache para almacenar y reutilizar los objetos PasaporteTipo.
    // La clave (String) será el identificador único (ej. el país), y el valor el objeto compartido.
    private final Map<String, PasaporteTipo> cache; // -cache: Map<String, PasaporteTipo>

    public FlyweightFactory() {
        this.cache = new HashMap<>();
    }

    /**
     * Obtiene un objeto PasaporteTipo basado únicamente en el país.
     * Si no existe en la caché, lo crea usando valores predeterminados.
     * @param pais El país para el que se requiere el tipo de pasaporte.
     * @return El objeto PasaporteTipo compartido o nuevo.
     */
    public PasaporteTipo getFlyweight(String pais) { // +getFlyweight(pais: String): PasaporteTipo
        // Normaliza el país para la clave de caché
        String clave = pais.toUpperCase(); 

        if (cache.containsKey(clave)) {
            System.out.println("Reutilizando PasaporteTipo para: " + pais);
            return cache.get(clave);
        } else {
            System.out.println("Creando y almacenando nuevo PasaporteTipo para: " + pais);
            
            // Determinar atributos intrínsecos basado en el país
            String color = determinarColorPorPais(pais);
            String idioma = determinarIdiomaPorPais(pais);
            
            PasaporteTipo tipo = new PasaporteTipo(pais, color, idioma);
            cache.put(clave, tipo);
            return tipo;
        }
    }

    /**
     * Obtiene un objeto PasaporteTipo basado en país, color e idioma.
     * Esto permite una creación más específica, aunque en el Flyweight Pattern
     * el país (o una clave única de los atributos intrínsecos) suele ser suficiente.
     * @param pais El país emisor.
     * @param color El color de la cubierta.
     * @param idioma El idioma principal.
     * @return El objeto PasaporteTipo compartido o nuevo.
     */
    public PasaporteTipo getFlyweight(String pais, String color, String idioma) { // +getFlyweight(pais, color, idioma): PasaporteTipo
        // La clave de la caché debe reflejar todos los atributos intrínsecos relevantes.
        String clave = pais.toUpperCase() + "_" + color.toUpperCase() + "_" + idioma.toUpperCase();

        if (cache.containsKey(clave)) {
            System.out.println("Reutilizando PasaporteTipo (completo) para clave: " + clave);
            return cache.get(clave);
        } else {
            System.out.println("Creando y almacenando nuevo PasaporteTipo (completo) para clave: " + clave);
            PasaporteTipo tipo = new PasaporteTipo(pais, color, idioma);
            cache.put(clave, tipo);
            return tipo;
        }
    }

    /**
     * Devuelve el número de objetos PasaporteTipo almacenados en la caché.
     * @return El tamaño de la caché.
     */
    public int getCacheSize() { // +getCacheSize(): int
        return cache.size();
    }

    // --- Métodos Privados de Lógica de la Fábrica ---

    /**
     * Simula la lógica para determinar el color de cubierta según el país.
     * @param pais El país de inicio.
     * @return El color de la cubierta.
     */
    private String determinarColorPorPais(String pais) { // -determinarColorPorPais(Pais): String
        String paisNormalizado = pais.toUpperCase();
        if (paisNormalizado.contains("COLOMBIA") || paisNormalizado.contains("ESPAÑA")) {
            return "Vino Tinto";
        } else if (paisNormalizado.contains("ESTADOS UNIDOS") || paisNormalizado.contains("INDIA")) {
            return "Azul";
        } else {
            return "Verde";
        }
    }

    /**
     * Simula la lógica para determinar el idioma principal según el país.
     * @param pais El país de inicio.
     * @return El idioma principal.
     */
    private String determinarIdiomaPorPais(String pais) { // -determinarIdiomaPorPais(Pais): String
        String paisNormalizado = pais.toUpperCase();
        if (paisNormalizado.contains("COLOMBIA") || paisNormalizado.contains("ESPAÑA")) {
            return "Español";
        } else if (paisNormalizado.contains("ESTADOS UNIDOS")) {
            return "Inglés";
        } else {
            return "Nativo";
        }
    }
}