package co.edu.poli.pasaportes.servicios;

public class SeguridadBiometricaService implements ISeguridadPasaporte {

    @Override
    public String aplicarSeguridad() {
        // Aquí iría la lógica real
        return "ESTRATEGIA BIOMÉTRICA: Escaneando huella dactilar y rostro en punto de control.";
    }
}