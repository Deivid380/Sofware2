package co.edu.poli.Vista;

import java.util.*;
import co.edu.poli.Modelo.*;
import co.edu.poli.Repositorio.PasaporteRepo;

public class Cliente {

    public static void main(String[] args) {
        // Datos de prueba
        Ciudad ciudad = new Ciudad("Bog", "Bogotá");
        Titular titular = new Titular("123456", "John Doe", "1990-01-01");
        Pais pais = new Pais("co", "Colombia", new ArrayList<>(Collections.singletonList(ciudad)));
        Pasaporte pasaporte = new Pasaporte("PAS123456", "2025-12-31", titular, pais);

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
        repo.delete("PAS123456");
    }
}
