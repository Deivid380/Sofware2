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

    private Connection conectar() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public String create(Pasaporte pasaporte) {
        String sql = "INSERT INTO public.pasaporte_prueba (numero, titular_id, pais_codigo, fecha_exp) VALUES (?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pasaporte.getId());
            ps.setString(2, pasaporte.getTitular().getId());
            ps.setString(3, pasaporte.getPais().getCodigo());
            ps.setDate(4, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            ps.executeUpdate();
            return "Pasaporte creado con éxito.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al crear pasaporte: " + e.getMessage();
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
                        null // reconstruir Pais si lo necesitas
                );
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no se encontró
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
                        null);
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public String update(Pasaporte pasaporte) {
        String sql = "UPDATE public.pasaporte_prueba SET fecha_exp = ? WHERE numero = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            ps.setString(2, pasaporte.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Pasaporte actualizado.";
            } else {
                return "No se encontró pasaporte con ese número.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar pasaporte: " + e.getMessage();
        }
    }

    @Override
    public String delete(String idPasaporte) {
        String sql = "DELETE FROM public.pasaporte_prueba WHERE numero = ?";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPasaporte);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Pasaporte eliminado.";
            } else {
                return "No se encontró pasaporte para eliminar.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar pasaporte: " + e.getMessage();
        }
    }
}
