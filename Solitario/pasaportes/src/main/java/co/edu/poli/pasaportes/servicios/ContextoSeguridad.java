package co.edu.poli.pasaportes.servicios;

// Importa el Adaptador, ya que hay una Asociación (flecha)
import co.edu.poli.pasaportes.modelo.Pasaporte; // Opcional, si lo usas en el método

/**
 * CONTEXTO PURO: Única responsabilidad es orquestar el patrón Strategy.
 */
public class ContextoSeguridad {

    // 1. Atributo para la Estrategia (Asociación con ISeguridadPasaporte)
    private ISeguridadPasaporte estrategiaActual;
    
    // 2. Atributo para el Adaptador (Asociación con AdapterPasaporte)
    private AdapterPasaporte adaptadorPasaporte;

    /**
     * Constructor (como lo definimos en el UML).
     */
    public ContextoSeguridad(AdapterPasaporte adaptador, ISeguridadPasaporte estrategiaInicial) {
        this.adaptadorPasaporte = adaptador;
        this.estrategiaActual = estrategiaInicial;
    }

    /**
     * Setter para cambiar la estrategia dinámicamente (: void).
     */
    public void setEstrategia(ISeguridadPasaporte nuevaEstrategia) {
        this.estrategiaActual = nuevaEstrategia;
    }

    /**
     * Ejecuta la lógica de la estrategia actual (: String).
     */
    public String ejecutarSeguridad() {
        
        // OPCIONAL: Aquí podrías usar el Pasaporte del modelo si la estrategia lo necesita
        Pasaporte pasaporte = adaptadorPasaporte.getPasaporte(); 
        
        // DELEGACIÓN: Llama a la estrategia asignada
        String resultado = this.estrategiaActual.aplicarSeguridad();
        
        return "Pasaporte (" + pasaporte.getId() + ") verificado. Resultado: " + resultado;
    }
}