package co.edu.poli.pasaportes.modelo;

public abstract class Pasaporte {

    private String id;
    private String fechaExp;
    private Titular titular;
    private Pais pais;

    // Bridge → referencia al implementador
    protected ElementoSeguridad seguridad;

    public Pasaporte(String id, String fechaExp, Titular titular, Pais pais, ElementoSeguridad seguridad) {
        this.id = id;
        this.fechaExp = fechaExp;
        this.titular = titular;
        this.pais = pais;
        this.seguridad = seguridad;
    }

    public abstract String tipoPasaporte();

    // Método delegado al implementador
    public String aplicarSeguridad() {
        return seguridad.aplicarSeguridad();
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFechaExp() { return fechaExp; }
    public void setFechaExp(String fechaExp) { this.fechaExp = fechaExp; }

    public Titular getTitular() { return titular; }
    public void setTitular(Titular titular) { this.titular = titular; }

    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }

    public ElementoSeguridad getSeguridad() { return seguridad; }
    public void setSeguridad(ElementoSeguridad seguridad) { this.seguridad = seguridad; }
}
