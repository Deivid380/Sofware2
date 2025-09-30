package co.edu.poli.pasaportes.servicios;

import java.sql.*;
import java.util.ResourceBundle;

public class Singleton {

    private static Singleton instancia;

    private static final String URL;
    private static final String USUARIO;
    private static final String CONTRA;

    static {
        ResourceBundle config = ResourceBundle.getBundle("config");
        URL = config.getString("URL");
        USUARIO = config.getString("USUARIO");
        CONTRA = config.getString("CONTRA");
    }

    private Singleton() {
        // Constructor privado
    }

    public static Singleton getInstance() {
        if (instancia == null) {
            synchronized (Singleton.class) {
                if (instancia == null) {
                    instancia = new Singleton();
                }
            }
        }
        return instancia;
    }

    // 🔹 SIEMPRE abre una conexión nueva
    public Connection conectar() throws SQLException {
        try {
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRA);
            System.out.println("Conexión establecida.");
            return conexion;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
    }
}
