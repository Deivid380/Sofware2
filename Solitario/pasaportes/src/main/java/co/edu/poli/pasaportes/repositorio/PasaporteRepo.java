package co.edu.poli.pasaportes.repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.pasaportes.servicios.*;
import co.edu.poli.pasaportes.modelo.*;

public class PasaporteRepo implements Repository<Pasaporte, String> {

    private Connection conectar() throws SQLException {
        // Usa el Singleton para obtener la conexión activa
        return Singleton.getInstance().conexionActiva();
    }

    private String getTableName(String tipoPasaporte) {
        if ("Ordinario".equalsIgnoreCase(tipoPasaporte)) {
            return "\"pasaporte_Ordinario\"";
        } else if ("Diplomático".equalsIgnoreCase(tipoPasaporte)) {
            return "\"Pasaporte_Diplomatico\"";
        }
        throw new IllegalArgumentException("Tipo de pasaporte no válido: " + tipoPasaporte);
    }

    @Override
    public String create(Pasaporte pasaporte, String tipoPasaporte) {
        String specificTableName = getTableName(tipoPasaporte);
        String specificSql = "INSERT INTO public." + specificTableName +
                " (numero, titular_id, pais_codigo, fecha_exp) VALUES (?, ?, ?, ?)";

        String baseSql = "INSERT INTO public.\"Pasaporte_Base\" " +
                "(id, numero, titular_id, pais_codigo, fecha_exp, tipo_pass) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            // Insertar en tabla específica
            try (PreparedStatement psSpecific = conn.prepareStatement(specificSql)) {
                psSpecific.setInt(1, Integer.parseInt(pasaporte.getId()));
                psSpecific.setString(2, pasaporte.getTitular().getId());
                psSpecific.setString(3, pasaporte.getPais().getCodigo());
                psSpecific.setDate(4, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psSpecific.executeUpdate();
            }

            // Insertar en tabla base
            try (PreparedStatement psBase = conn.prepareStatement(baseSql)) {
                psBase.setObject(1, java.util.UUID.randomUUID(), Types.OTHER);
                psBase.setInt(2, Integer.parseInt(pasaporte.getId()));
                psBase.setString(3, pasaporte.getTitular().getId());
                psBase.setString(4, pasaporte.getPais().getCodigo());
                psBase.setDate(5, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psBase.setString(6, tipoPasaporte);
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

        String sql = "SELECT p.numero, p.titular_id, p.pais_codigo, p.fecha_exp, t.nombre as titular_nombre " +
                     "FROM public." + tableName + " p " +
                     "LEFT JOIN public.\"Titular\" t ON p.titular_id = t.id " +
                     "WHERE p.numero = ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(idPasaporte));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Titular titular = new Titular(rs.getString("titular_id"), rs.getString("titular_nombre"), null);
                Pais pais = new Pais(rs.getString("pais_codigo"), null);

                if ("Ordinario".equalsIgnoreCase(tipoPasaporte)) {
                    return new PasaporteOrdinario.Builder()
                            .getid(rs.getString("numero"))
                            .getfechaExp(rs.getString("fecha_exp"))
                            .gettitular(titular)
                            .getpais(pais)
                            .build();
                } else {
                    return new PasaporteDiplomatico(
                            rs.getString("numero"),
                            rs.getString("fecha_exp"),
                            titular,
                            pais
                    );
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
        String specificSql = "UPDATE public." + tableName + " SET titular_id = ?, fecha_exp = ? WHERE numero = ?";
        String baseSql = "UPDATE public.\"Pasaporte_Base\" SET titular_id = ?, fecha_exp = ? WHERE numero = ?";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            // Tabla específica
            int specificRows;
            try (PreparedStatement psSpecific = conn.prepareStatement(specificSql)) {
                psSpecific.setString(1, pasaporte.getTitular().getId());
                psSpecific.setDate(2, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psSpecific.setInt(3, Integer.parseInt(pasaporte.getId()));
                specificRows = psSpecific.executeUpdate();
            }

            // Tabla base
            int baseRows;
            try (PreparedStatement psBase = conn.prepareStatement(baseSql)) {
                psBase.setString(1, pasaporte.getTitular().getId());
                psBase.setDate(2, java.sql.Date.valueOf(pasaporte.getFechaExp()));
                psBase.setInt(3, Integer.parseInt(pasaporte.getId()));
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
        String specificSql = "DELETE FROM public." + tableName + " WHERE numero = ?";
        String baseSql = "DELETE FROM public.\"Pasaporte_Base\" WHERE numero = ?";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            int specificRows;
            try (PreparedStatement psSpecific = conn.prepareStatement(specificSql)) {
                psSpecific.setInt(1, Integer.parseInt(idPasaporte));
                specificRows = psSpecific.executeUpdate();
            }

            int baseRows;
            try (PreparedStatement psBase = conn.prepareStatement(baseSql)) {
                psBase.setInt(1, Integer.parseInt(idPasaporte));
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

    String sql = "SELECT p.numero, p.titular_id, p.pais_codigo, p.fecha_exp, p.tipo_pass, t.nombre as titular_nombre " +
                 "FROM public.\"Pasaporte_Base\" p " +
                 "LEFT JOIN public.\"Titular\" t ON p.titular_id = t.id";

    try (Connection conn = conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Titular titular = new Titular(rs.getString("titular_id"), rs.getString("titular_nombre"), null);
            Pais pais = new Pais(rs.getString("pais_codigo"), null);

            String tipo = rs.getString("tipo_pass");

            if ("Ordinario".equalsIgnoreCase(tipo)) {
                pasaportes.add(new PasaporteOrdinario.Builder()
                        .getid(rs.getString("numero"))
                        .getfechaExp(rs.getString("fecha_exp"))
                        .gettitular(titular)
                        .getpais(pais)
                        .build());
            } else if ("Diplomático".equalsIgnoreCase(tipo)) {
                pasaportes.add(new PasaporteDiplomatico(
                        rs.getString("numero"),
                        rs.getString("fecha_exp"),
                        titular,
                        pais
                ));
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return pasaportes;
}

}
