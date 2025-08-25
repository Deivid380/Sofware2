package co.edu.poli.Repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupabaseExample{

    private static final String URL = "jdbc:postgresql://db.csbejvbgyexmutjebdga.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Software2*";

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a Supabase PostgreSQL");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        conectar();
    }
}
