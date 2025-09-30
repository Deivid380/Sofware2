package co.edu.poli.pasaportes.modelo;

public class Biometrica implements ElementoSeguridad {
    @Override
    public String aplicarSeguridad() {
        return "Seguridad con datos biométricos";
    }
}
