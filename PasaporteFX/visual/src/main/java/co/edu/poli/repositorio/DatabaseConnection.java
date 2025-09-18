package co.edu.poli.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Instancia única del Singleton
    private static DatabaseConnection instance;

    // Configuración de conexión
    private static final String BASE_URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:6543/postgres";
    private static final String USER = "postgres.csbejvbgyexmutjebdga";
    private static final String PASSWORD = "Software2*";
    private static final String FULL_URL = BASE_URL + "?user=" + USER + "&password=" + PASSWORD;

    private Connection connection; // 🔹 Debe ser java.sql.Connection

    // Constructor privado (impide instanciación externa)
    private DatabaseConnection() {
        try {
            // Se carga el driver explícitamente para asegurar compatibilidad
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(FULL_URL);
            System.out.println("Conexión inicial creada con éxito.");
        } catch (SQLException | ClassNotFoundException e) {
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
                System.out.println("La conexión estaba cerrada o era nula. Reconectando...");
                this.connection = DriverManager.getConnection(FULL_URL);
                System.out.println("Se creó una nueva conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener conexión: " + e.getMessage());
            e.printStackTrace();
            this.connection = null; // Asegurarse de que la conexión sea nula si falla
        }
        return connection;
    }
}
