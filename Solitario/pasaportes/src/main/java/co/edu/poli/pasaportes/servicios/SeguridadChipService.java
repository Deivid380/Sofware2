package co.edu.poli.pasaportes.servicios;

public class SeguridadChipService implements ISeguridadPasaporte {

    @Override
    public String aplicarSeguridad() {
        // Aquí iría la lógica real
        return "ESTRATEGIA CHIP: Verificando datos biométricos en Chip RFID/NFC (Estándar ICAO).";
    }
}