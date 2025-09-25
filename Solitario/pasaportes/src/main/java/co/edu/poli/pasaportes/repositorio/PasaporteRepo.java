package co.edu.poli.pasaportes.repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.pasaportes.servicios.*;
import co.edu.poli.pasaportes.modelo.*;

public class PasaporteRepo implements Repository<Pasaporte, String> {

    private Connection conectar() throws SQLException {
        return Singleton.getInstance().conexionActiva();
    }

    private String getTableName(String tipoPasaporte) {
        if ("Ordinario".equalsIgnoreCase(tipoPasaporte)) {
            return "pasaporte_ordinario";
        } else if ("Diplomático".equalsIgnoreCase(tipoPasaporte)) {
            return "pasaporte_diplomatico";
        }
        throw new IllegalArgumentException("Tipo de pasaporte no válido: " + tipoPasaporte);
    }

    // Método de creación corregido para usar 'titular'
    @Override
    public String create(Pasaporte pasaporte, String tipoPasaporte) {
        String specificTableName = getTableName(tipoPasaporte);
        
        String specificSql = "INSERT INTO public." + specificTableName +
            " (id, titular, pais_codigo, fecha_exp) VALUES (?, ?, ?, ?)";
        
        String baseSql = "INSERT INTO public.pasaporte_base " +
            "(id, seguridad_id, tipo_pass, titular, pais_codigo, fecha_exp) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psBase = conn.prepareStatement(baseSql)) {
                psBase.setString(1, pasaporte.getId());
                psBase.setString(2, java.util.UUID.randomUUID().toString());
                psBase.setString(3, tipoPasaporte);
                psBase.setString(4, pasaporte.getTitular().getNombre());
                psBase.setString(5, pasaporte.getPais().getCodigo());
                psBase.setDate(6, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psBase.executeUpdate();
            }

            try (PreparedStatement psSpecific = conn.prepareStatement(specificSql)) {
                psSpecific.setString(1, pasaporte.getId());
                psSpecific.setString(2, pasaporte.getTitular().getNombre());
                psSpecific.setString(3, pasaporte.getPais().getCodigo());
                psSpecific.setDate(4, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psSpecific.executeUpdate();
            }

            conn.commit();
            return "✅ Pasaporte creado en base y en " + specificTableName;

        } catch (SQLException e) {
            e.printStackTrace();
            return "❌ Error al crear pasaporte: " + e.getMessage();
        }
    }

    // Método de lectura corregido para usar 'titular'
    @Override
    public Pasaporte read(String idPasaporte, String tipoPasaporte) {
        String sql = "SELECT p.id, p.titular, p.pais_codigo, p.fecha_exp, p.tipo_pass " +
                     "FROM public.pasaporte_base p " +
                     "WHERE p.id = ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPasaporte);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Titular titular = new Titular(null, rs.getString("titular"), null);
                Pais pais = new Pais(rs.getString("pais_codigo"), null);
                String tipo = rs.getString("tipo_pass");

                if ("Ordinario".equalsIgnoreCase(tipo)) {
                    return new PasaporteOrdinario(rs.getString("id"), rs.getString("fecha_exp"), titular, pais);
                } else {
                    return new PasaporteDiplomatico(rs.getString("id"), rs.getString("fecha_exp"), titular, pais);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método de actualización corregido para usar 'titular'
    @Override
    public String update(Pasaporte pasaporte, String tipoPasaporte) {
        String tableName = getTableName(tipoPasaporte);
        String specificSql = "UPDATE public." + tableName + " SET titular = ?, fecha_exp = ? WHERE id = ?";
        String baseSql = "UPDATE public.pasaporte_base SET titular = ?, fecha_exp = ? WHERE id = ?";

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
                psBase.setString(3, pasaporte.getId());
                baseRows = psBase.executeUpdate();
            }

            if (specificRows > 0 && baseRows > 0) {
                conn.commit();
                return "✅ Pasaporte actualizado en ambas tablas.";
            } else {
                conn.rollback();
                return "⚠️ No se encontró el pasaporte para actualizar.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "❌ Error al actualizar pasaporte: " + e.getMessage();
        }
    }

    // Método de eliminación corregido para usar 'id'
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
                return "✅ Pasaporte eliminado de ambas tablas.";
            } else {
                conn.rollback();
                return "⚠️ No se encontró el pasaporte para eliminar.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "❌ Error al eliminar pasaporte: " + e.getMessage();
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
                Titular titular = new Titular(null, rs.getString("titular"), null);
                Pais pais = new Pais(rs.getString("pais_codigo"), null);
                String tipo = rs.getString("tipo_pass");

                if ("Ordinario".equalsIgnoreCase(tipo)) {
                    pasaportes.add(new PasaporteOrdinario(rs.getString("id"), rs.getString("fecha_exp"), titular, pais));
                } else if ("Diplomático".equalsIgnoreCase(tipo)) {
                    pasaportes.add(new PasaporteDiplomatico(rs.getString("id"), rs.getString("fecha_exp"), titular, pais));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pasaportes;
    }
}