package co.edu.poli.pasaportes.servicios;

import java.sql.*;
import java.util.ResourceBundle;

public class Singleton {

    private static Singleton instancia;
    private Connection conexion;

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

    public Connection conectar() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRA);
                System.out.println("Conexión establecida.");
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return conexion;
    }

    public void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                throw e;
            }
        }
    }

    public Connection conexionActiva() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            throw new SQLException("La conexión no está activa.");
        }
        return conexion;
    }
}