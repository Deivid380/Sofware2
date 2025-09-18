package co.edu.poli.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Instancia única del Singleton
    private static DatabaseConnection instance;

    // Configuración de conexión
    private static final String URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:6543/postgres?sslmode=require";
    private static final String USER = "postgres.csbejvbgyexmutjebdga";
    private static final String PASSWORD = "Software2*";

    private Connection connection; // 🔹 Debe ser java.sql.Connection

    // Constructor privado (impide instanciación externa)
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión inicial creada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método público para obtener la instancia única
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) { // thread-safe
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Devuelve la conexión activa o crea una nueva si está cerrada
    public Connection getConnection() { // 🔹 Debe devolver Connection
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Se creó una nueva conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}
