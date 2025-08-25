package co.edu.poli.Vista;

import java.util.ArrayList;
import java.util.Collections;
import co.edu.poli.Repositorio.SupabaseExample;
import java.sql.Connection;
import java.sql.Statement;
import co.edu.poli.Modelo.*;


public class Cliente {

    public static void main(String[] args) {
        // Tu main original
        Ciudad ciudad = new Ciudad("Bog", "Bogotá");
        Titular titular = new Titular("123456", "John Doe", "1990-01-01");
        Pais pais = new Pais("co", "Colombia", new ArrayList<>(Collections.singletonList(ciudad)));
        Pasaporte pasaporte = new Pasaporte("PAS123456", "2025-12-31", titular, pais);
        Visa visa = new Visa("VISA123456", pais, 1, pasaporte);

        System.out.println(pasaporte);
    }

    // 🔹 Aquí afuera va el bloque static
    static {
        System.out.println("=== Creando tabla de prueba en Supabase ===");
        crearTablaPruebaEnSupabase();
    }

    private static void crearTablaPruebaEnSupabase() {
        try (Connection conn = SupabaseExample.conectar()) {
            if (conn == null) {
                System.out.println("⚠️ No se pudo crear la tabla porque no hubo conexión.");
                return;
            }
            String sql = """
                CREATE TABLE IF NOT EXISTS public.pasaporte_prueba (
                    id SERIAL PRIMARY KEY,
                    numero VARCHAR(20) UNIQUE NOT NULL,
                    titular_id VARCHAR(30),
                    pais_codigo VARCHAR(10),
                    fecha_exp DATE,
                    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
                System.out.println("✅ Tabla 'public.pasaporte_prueba' creada/verificada en Supabase.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al crear tabla: " + e.getMessage());
        }
    }
}
