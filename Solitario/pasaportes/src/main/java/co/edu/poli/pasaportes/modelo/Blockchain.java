package co.edu.poli.pasaportes.modelo;

public class Blockchain implements ElementoSeguridad {

    @Override
    public String aplicarSeguridad() {
        return "Seguridad basada en Blockchain (hash inmutable)";
    }
}
