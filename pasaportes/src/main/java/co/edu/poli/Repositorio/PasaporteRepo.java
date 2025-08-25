package co.edu.poli.Repositorio;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import co.edu.poli.Modelo.Pasaporte;

public class PasaporteRepo implements Repository<Pasaporte, String> {

    private static final String URL = "jdbc:postgresql://db.csbejvbgyexmutjebdga.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Software2*";


    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void create(Pasaporte pasaporte) {
        String sql = "INSERT INTO public.pasaporte_prueba (numero, titular_id, pais_codigo, fecha_exp) VALUES (?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pasaporte.getId());
            ps.setString(2, pasaporte.getTitular().getId());
            ps.setString(3, pasaporte.getPais().getCodigo());
            ps.setDate(4, Date.valueOf(pasaporte.getFechaExp()));
            ps.executeUpdate();
            System.out.println("✅ Pasaporte creado con éxito.");
        } catch (SQLException e) {
            System.out.println("❌ Error al crear pasaporte: " + e.getMessage());
        }
    }

    @Override
    public Pasaporte read(String idPasaporte) {
        String sql = "SELECT numero, titular_id, pais_codigo, fecha_exp FROM public.pasaporte_prueba WHERE numero = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPasaporte);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pasaporte p = new Pasaporte(
                    rs.getString("numero"),
                    rs.getString("fecha_exp"),
                    null, // aquí deberías reconstruir Titular
                    null  // aquí deberías reconstruir Pais
                );
                System.out.println("✅ Pasaporte encontrado: " + p.getId());
                return p;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al leer pasaporte: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pasaporte> readAll() {
        List<Pasaporte> lista = new ArrayList<>();
        String sql = "SELECT numero, titular_id, pais_codigo, fecha_exp FROM public.pasaporte_prueba";
        try (Connection conn = conectar(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Pasaporte p = new Pasaporte(
                    rs.getString("numero"),
                    rs.getString("fecha_exp"),
                    null,
                    null
                );
                lista.add(p);
            }
            System.out.println("✅ Se leyeron " + lista.size() + " pasaportes.");
        } catch (SQLException e) {
            System.out.println("❌ Error al leer pasaportes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Pasaporte pasaporte) {
        String sql = "UPDATE public.pasaporte_prueba SET fecha_exp = ? WHERE numero = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(pasaporte.getFechaExp()));
            ps.setString(2, pasaporte.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Pasaporte actualizado.");
            } else {
                System.out.println("⚠️ No se encontró pasaporte con ese número.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar pasaporte: " + e.getMessage());
        }
    }

    @Override
    public void delete(String idPasaporte) {
        String sql = "DELETE FROM public.pasaporte_prueba WHERE numero = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPasaporte);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Pasaporte eliminado.");
            } else {
                System.out.println("⚠️ No se encontró pasaporte para eliminar.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar pasaporte: " + e.getMessage());
        }
    }
}
