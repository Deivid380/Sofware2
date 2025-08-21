import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SupabaseExample {
    public static void main(String[] args) {
        String url = "postgresql://postgres:[YOUR-PASSWORD]@db.lwdqgwbkmxjizmdraeti.supabase.co:5432/postgres";
        String user = "nicolascm30";
        String password = "Software2*";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Conectado a Supabase");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                System.out.println("Usuario: " + rs.getString("nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
