package co.edu.poli.pasaportes.modelo;

public class PasaporteEmergencia extends Pasaporte {
    private String fechaEmergencia;

    public PasaporteEmergencia(String id, String fechaExp, Titular titular, Pais pais, 
                               ElementoSeguridad seguridad, String fechaEmergencia) {
        super(id, fechaExp, titular, pais, seguridad);
        this.fechaEmergencia = fechaEmergencia;
    }

    @Override
    public String tipoPasaporte() {
        return "Pasaporte de Emergencia";
    }

    public String getFechaEmergencia() {
        return fechaEmergencia;
    }

    public void setFechaEmergencia(String fechaEmergencia) {
        this.fechaEmergencia = fechaEmergencia;
    }
}
