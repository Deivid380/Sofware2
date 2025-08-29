package co.edu.poli.Vista;

import java.util.*;
import co.edu.poli.Modelo.*;
import co.edu.poli.Repositorio.PasaporteRepo;

public class Cliente {

    public static void main(String[] args) {
        // Datos de prueba
        Ciudad ciudad = new Ciudad("Bog", "Bogotá");
        Titular titular = new Titular("900778", "John Doe", "2005-05-15");
        Pais pais = new Pais("co", "Colombia", new ArrayList<>(Collections.singletonList(ciudad)));
        Pasaporte pasaporte = new Pasaporte("PAS5555  ", "2021-03-09", titular, pais);

        // Repositorio
        PasaporteRepo repo = new PasaporteRepo();

        // 🔹 Crear
        repo.create(pasaporte);

        // 🔹 Leer uno
        repo.read("PAS123456");

        // 🔹 Listar todos
        repo.readAll();

        // 🔹 Actualizar
        pasaporte.setFechaExp("2030-01-01");
        repo.update(pasaporte);

          // 🔹 Requerimiento: Buscar por criterio ingresado
        Scanner sc = new Scanner(System.in);
        System.out.print("\nIngrese una letra o número para buscar en los pasaportes: ");
        String criterio = sc.nextLine();

        System.out.println("\n🔎 Pasaportes que contienen '" + criterio + "' en el número:");
        repo.searchByIdContains(criterio).forEach(p -> 
            System.out.println("- " + p.getId() + " expira: " + p.getFechaExp())
        );

        sc.close();

         //🔹 Eliminar
       
       //  repo.delete("PAS123456");

    }
}
