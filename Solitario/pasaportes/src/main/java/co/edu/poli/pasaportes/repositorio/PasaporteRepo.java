package co.edu.poli.pasaportes.repositorio;

import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.pasaportes.servicios.Singleton;
import co.edu.poli.pasaportes.modelo.*;

public class PasaporteRepo implements Repository<Pasaporte, String> {

    private Connection conectar() throws SQLException {
        return Singleton.getInstance().conectar();
    }

    private String normalizarTipo(String tipo) {
        if (tipo == null)
            return null;
        return Normalizer.normalize(tipo, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    private String getTableName(String tipoPasaporte) {
        String tipo = normalizarTipo(tipoPasaporte);

        if ("ordinario".equals(tipo)) {
            return "pasaporte_ordinario";
        } else if ("diplomatico".equals(tipo)) {
            return "pasaporte_diplomatico";
        }
        throw new IllegalArgumentException("Tipo de pasaporte no válido: " + tipoPasaporte);
    }

    private String seguridadPorTipo(String tipoPasaporte) {
        String tipo = normalizarTipo(tipoPasaporte);

        if ("ordinario".equals(tipo))
            return "Chip";
        if ("diplomatico".equals(tipo))
            return "Biometrica";
        return "N/A";
    }

    @Override
    public String create(Pasaporte pasaporte, String tipoPasaporte) {
        String specificTableName = getTableName(tipoPasaporte);

        String specificSql = "INSERT INTO public." + specificTableName +
                " (id, titular, pais_codigo, fecha_exp) VALUES (?, ?, ?, ?)";

        String baseSql = "INSERT INTO public.pasaporte_base " +
                "(id, titular, pais_codigo, fecha_exp, tipo_pass, seguridad_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            // tabla específica
            try (PreparedStatement psSpecific = conn.prepareStatement(specificSql)) {
                psSpecific.setString(1, pasaporte.getId());
                psSpecific.setString(2, pasaporte.getTitular().getNombre());
                psSpecific.setString(3, pasaporte.getPais().getCodigo());
                psSpecific.setDate(4, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psSpecific.executeUpdate();
            }

            // tabla base
            try (PreparedStatement psBase = conn.prepareStatement(baseSql)) {
                psBase.setString(1, pasaporte.getId());
                psBase.setString(2, pasaporte.getTitular().getNombre());
                psBase.setString(3, pasaporte.getPais().getCodigo());
                psBase.setDate(4, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psBase.setString(5, tipoPasaporte); // guarda el texto original normalizado
                psBase.setString(6, seguridadPorTipo(tipoPasaporte));
                psBase.executeUpdate();
            }

            conn.commit();
            return "Pasaporte creado con éxito en ambas tablas.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al crear pasaporte: " + e.getMessage();
        }
    }

    @Override
    public Pasaporte read(String idPasaporte, String tipoPasaporte) {
        String tableName = getTableName(tipoPasaporte);

        String sql = "SELECT p.id, p.titular, p.pais_codigo, p.fecha_exp " +
                "FROM public." + tableName + " p " +
                "WHERE p.id = ?";

        try (Connection conn = conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPasaporte);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Titular titular = new Titular(rs.getString("titular"), "Desconocido", null);
                Pais pais = new Pais(rs.getString("pais_codigo"), null);

                String tipo = normalizarTipo(tipoPasaporte);
                if ("ordinario".equals(tipo)) {
                    return new PasaporteOrdinario(
                            rs.getString("id"),
                            rs.getString("fecha_exp"),
                            titular,
                            pais,
                            null);
                } else {
                    return new PasaporteDiplomatico(
                            rs.getString("id"),
                            rs.getString("fecha_exp"),
                            titular,
                            pais,
                            null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String update(Pasaporte pasaporte, String tipoPasaporte) {
        String tableName = getTableName(tipoPasaporte);
        String specificSql = "UPDATE public." + tableName + " SET titular = ?, fecha_exp = ? WHERE id = ?";
        String baseSql = "UPDATE public.pasaporte_base SET titular = ?, fecha_exp = ?, seguridad_id = ? WHERE id = ?";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            int specificRows;
            try (PreparedStatement psSpecific = conn.prepareStatement(specificSql)) {
                psSpecific.setString(1, pasaporte.getTitular().getNombre());
                psSpecific.setDate(2, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psSpecific.setString(3, pasaporte.getId());
                specificRows = psSpecific.executeUpdate();
            }

            int baseRows;
            try (PreparedStatement psBase = conn.prepareStatement(baseSql)) {
                psBase.setString(1, pasaporte.getTitular().getNombre());
                psBase.setDate(2, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psBase.setString(3, seguridadPorTipo(tipoPasaporte));
                psBase.setString(4, pasaporte.getId());
                baseRows = psBase.executeUpdate();
            }

            if (specificRows > 0 && baseRows > 0) {
                conn.commit();
                return "Pasaporte actualizado en ambas tablas.";
            } else {
                conn.rollback();
                return "No se encontró el pasaporte para actualizar.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar el pasaporte: " + e.getMessage();
        }
    }

    @Override
    public String delete(String idPasaporte, String tipoPasaporte) {
        String tableName = getTableName(tipoPasaporte);
        String specificSql = "DELETE FROM public." + tableName + " WHERE id = ?";
        String baseSql = "DELETE FROM public.pasaporte_base WHERE id = ?";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            int specificRows;
            try (PreparedStatement psSpecific = conn.prepareStatement(specificSql)) {
                psSpecific.setString(1, idPasaporte);
                specificRows = psSpecific.executeUpdate();
            }

            int baseRows;
            try (PreparedStatement psBase = conn.prepareStatement(baseSql)) {
                psBase.setString(1, idPasaporte);
                baseRows = psBase.executeUpdate();
            }

            if (specificRows > 0 && baseRows > 0) {
                conn.commit();
                return "Pasaporte eliminado de ambas tablas.";
            } else {
                conn.rollback();
                return "No se encontró el pasaporte para eliminar.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar pasaporte: " + e.getMessage();
        }
    }

    @Override
    public List<Pasaporte> readAll() {
        List<Pasaporte> pasaportes = new ArrayList<>();

        String sql = "SELECT p.id, p.titular, p.pais_codigo, p.fecha_exp, p.tipo_pass " +
                "FROM public.pasaporte_base p";

        try (Connection conn = conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Titular titular = new Titular(rs.getString("titular"), "Desconocido", null);
                Pais pais = new Pais(rs.getString("pais_codigo"), null);

                String tipo = normalizarTipo(rs.getString("tipo_pass"));
                if ("ordinario".equals(tipo)) {
                    pasaportes.add(new PasaporteOrdinario(
                            rs.getString("id"),
                            rs.getString("fecha_exp"),
                            titular,
                            pais,
                            null));
                } else if ("diplomatico".equals(tipo)) {
                    pasaportes.add(new PasaporteDiplomatico(
                            rs.getString("id"),
                            rs.getString("fecha_exp"),
                            titular,
                            pais,
                            null));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pasaportes;
    }
}
