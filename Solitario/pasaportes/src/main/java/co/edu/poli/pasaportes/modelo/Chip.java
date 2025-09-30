package co.edu.poli.pasaportes.modelo;

public class Chip implements ElementoSeguridad {
    @Override
    public String aplicarSeguridad() {
        return "Seguridad con chip electrónico";
    }
}
