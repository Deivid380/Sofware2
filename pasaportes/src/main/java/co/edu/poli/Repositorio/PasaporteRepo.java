package co.edu.poli.Repositorio;

import java.util.*;
import java.sql.*;

import co.edu.poli.Modelo.Pasaporte;

public class PasaporteRepo implements Repository<Pasaporte, String> {

    @Override
    public void create(Pasaporte pasaporte) {
        // Implementación del método para crear un pasaporte en la base de datos
    }

    @Override
    public Pasaporte read(String idPasaporte) {
        // Implementación del método para leer un pasaporte de la base de datos
        return null;
    }

    @Override
    public List<Pasaporte> readAll() {
        // Implementación del método para leer todos los pasaportes de la base de datos
        return null;
    }

    @Override
    public void update(Pasaporte pasaporte) {
        // Implementación del método para actualizar un pasaporte en la base de datos
    }

    @Override
    public void delete(String idPasaporte) {
        // Implementación del método para eliminar un pasaporte de la base de datos
    }
}