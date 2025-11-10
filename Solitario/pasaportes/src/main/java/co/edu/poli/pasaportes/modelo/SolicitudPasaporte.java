package co.edu.poli.pasaportes.modelo;

// SOLO LA ENTIDAD DE TRANSPORTE
public class SolicitudPasaporte {
    private String titularId;
    private String tipoPasaporte;
    private double costo;
    private boolean documentacionCompleta;
    
    public SolicitudPasaporte(String titularId, String tipo, double costo, boolean docs) {
        this.titularId = titularId;
        this.tipoPasaporte = tipo;
        this.costo = costo;
        this.documentacionCompleta = docs;
    }
    
    public String getTitularId() { return titularId; }
    public String getTipoPasaporte() { return tipoPasaporte; }
    public double getCosto() { return costo; }
    public boolean isDocumentacionCompleta() { return documentacionCompleta; }
}