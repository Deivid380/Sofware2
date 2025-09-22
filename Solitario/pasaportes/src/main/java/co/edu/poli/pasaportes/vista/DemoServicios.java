package co.edu.poli.pasaportes.vista;

import co.edu.poli.pasaportes.modelo.*;
import co.edu.poli.pasaportes.servicios.*;

public class DemoServicios {
    public static void main(String[] args) {
        System.out.println("===== PRUEBA DE SERVICIOS =====");

        PrototypeService protoService = new PrototypeService();

        Titular titular1 = new Titular("T1", "Juan Perez", "Colombiana");
        Titular titularClonado = protoService.clonarTitular(titular1);

        System.out.println("\n--- Prototype ---");
        System.out.println("Titular original: " + titular1);
        System.out.println("Titular clonado:  " + titularClonado);

        PasaporteBuilder builder = new PasaporteBuilder();

        PasaporteOrdinario pasaporte = builder
                .setId("P001")
                .setFechaExp("2030-12-31")
                .setTitular(titularClonado) // usamos el clon
                .setPais(new Pais("CO", "Colombia"))
                .build();

        System.out.println("\n--- Builder ---");
        System.out.println("Pasaporte creado con Builder: " + pasaporte);

        Titular titular2 = new Titular("T2", "Maria Gomez", "Colombiana");
        Titular titular2Clonado = protoService.clonarTitular(titular2);

        PasaporteOrdinario pasaporte2 = builder
                .setId("P002")
                .setFechaExp("2029-05-20")
                .setTitular(titular2Clonado)
                .setPais(new Pais("US", "Estados Unidos"))
                .build();

        System.out.println("\n--- Prototype + Builder ---");
        System.out.println("Pasaporte 2 creado con clon: " + pasaporte2);

        System.out.println("\n===== FIN DE PRUEBA =====");
    }
}
