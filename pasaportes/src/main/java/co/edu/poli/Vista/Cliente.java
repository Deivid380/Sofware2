package co.edu.poli.Vista;

import java.util.*;
import co.edu.poli.Modelo.*;
import co.edu.poli.Repositorio.PasaporteRepo;

public class Cliente {

    public static void main(String[] args) {
        // Datos de prueba
        Ciudad ciudad = new Ciudad("Bog", "Bogotá");
        Titular titular = new Titular("456789", "John Doe", "2005-06-01");
        Pais pais = new Pais("co", "Colombia", new ArrayList<>(Collections.singletonList(ciudad)));
        Pasaporte pasaporte = new Pasaporte("PAS346587  ", "2025-12-31", titular, pais);

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

        // 🔹 Eliminar
       //repo.delete("PAS123456");
    }
}
