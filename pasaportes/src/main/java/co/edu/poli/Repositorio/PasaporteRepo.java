package co.edu.poli.Repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.Modelo.Pasaporte;

public class PasaporteRepo implements Repository<Pasaporte, String> {

    // ✅ URL actualizada para Supabase con pooler
    private static final String URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:6543/postgres?sslmode=require";
    private static final String USER = "postgres.csbejvbgyexmutjebdga"; // usuario completo de Supabase
    private static final String PASSWORD = "Software2*"; // tu contraseña real

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
            ps.setDate(4, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            ps.executeUpdate();
            System.out.println(" Pasaporte creado con éxito.");
        } catch (SQLException e) {
            System.out.println(" Error al crear pasaporte: " + e.getMessage());
            e.printStackTrace();
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
                    null, // reconstruir Titular si lo necesitas
                    null  // reconstruir Pais si lo necesitas
                );
                System.out.println(" Pasaporte encontrado: " + p.getId());
                return p;
            }
        } catch (SQLException e) {
            System.out.println(" Error al leer pasaporte: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println(" Se leyeron " + lista.size() + " pasaportes.");
        } catch (SQLException e) {
            System.out.println(" Error al leer pasaportes: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void update(Pasaporte pasaporte) {
        String sql = "UPDATE public.pasaporte_prueba SET fecha_exp = ? WHERE numero = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            ps.setString(2, pasaporte.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println(" Pasaporte actualizado.");
            } else {
                System.out.println(" No se encontró pasaporte con ese número.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar pasaporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String idPasaporte) {
        String sql = "DELETE FROM public.pasaporte_prueba WHERE numero = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPasaporte);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println(" Pasaporte eliminado.");
            } else {
                System.out.println(" No se encontró pasaporte para eliminar.");
            }
        } catch (SQLException e) {
            System.out.println(" Error al eliminar pasaporte: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
