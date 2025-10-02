package co.edu.poli.pasaportes.vista;

import co.edu.poli.pasaportes.modelo.*;
import co.edu.poli.pasaportes.servicios.*;

public class DemoServicios {
    public static void main(String[] args) {
        // Seguridad Blockchain
        ElementoSeguridad blockchain = new Blockchain();

        // Titular decorado con historial de viajes
        Titular titularBase = new Titular("T100", "Carlos Ruiz", "Colombiana");
        TitularHistorialDecorator titularConHistorial = new TitularHistorialDecorator(titularBase);
        titularConHistorial.agregarViaje("España 2022");
        titularConHistorial.agregarViaje("EE.UU 2023");

        // Pasaporte de Emergencia con seguridad Blockchain
        Pasaporte emergencia = new PasaporteEmergencia(
                "PE-001",
                "2025-10-02",
                titularConHistorial.getTitularBase(),
                new Pais("CO", "Colombia"),
                blockchain,
                "2025-10-01" // fecha de emergencia
        );

        System.out.println(emergencia.tipoPasaporte() + " con " + emergencia.aplicarSeguridad());
        System.out.println("Titular: " + titularConHistorial);
        System.out.println("Fecha de emergencia: " + ((PasaporteEmergencia) emergencia).getFechaEmergencia());
    }
}
