package co.edu.poli.Repositorio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupabaseExample {
    public static void main(String[] args) {
        // Parámetros de conexión
        String url = "jdbc:postgresql://db.iwqdzmdjtmimjxbuodou.supabase.co:5432/postgres";
        String user = "postgres"; // tu usuario
        String password = "Sofware2*"; // tu contraseña de Supabase

        try {
            // Registrar el driver (a veces no es necesario en JDBC 4+)
            Class.forName("org.postgresql.Driver");

            // Establecer la conexión
            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("✅ Conexión exitosa a Supabase!");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en la conexión: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC no encontrado: " + e.getMessage());
        }
    }
}
