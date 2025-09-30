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

        PasaporteOrdinario pasaporte;

        System.out.println("\n--- Builder ---");
        
        Titular titular2 = new Titular("T2", "Maria Gomez", "Colombiana");
        Titular titular2Clonado = protoService.clonarTitular(titular2);

        PasaporteOrdinario pasaporte2;

        System.out.println("\n--- Prototype + Builder ---");

        System.out.println("\n===== FIN DE PRUEBA =====");
    }
}
