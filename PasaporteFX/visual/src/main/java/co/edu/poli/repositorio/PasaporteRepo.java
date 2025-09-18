package co.edu.poli.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;

import co.edu.poli.modelo.Pasaporte;
import co.edu.poli.modelo.Titular;

public class PasaporteRepo { // No implementaremos la interfaz genérica por ahora

    private Connection conectar() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Devuelve el nombre de la tabla correcto según el tipo de pasaporte.
     * ¡Importante! Se usan comillas dobles para respetar mayúsculas/minúsculas.
     */
    private String getTableName(String tipoPasaporte) {
        if ("Ordinario".equalsIgnoreCase(tipoPasaporte)) {
            return "\"pasaporte_Ordinario\"";
        } else if ("Diplomático".equalsIgnoreCase(tipoPasaporte)) {
            return "\"Pasaporte Diplomatico\""; // Asumiendo que esta tabla también existe
        }
        throw new IllegalArgumentException("Tipo de pasaporte no válido: " + tipoPasaporte);
    }

    public String create(Pasaporte pasaporte, String tipoPasaporte) {
        // SQL para la tabla específica (Ordinario o Diplomático)
        String specificTableName = getTableName(tipoPasaporte);
        String specificSql = "INSERT INTO public." + specificTableName + " (numero, titular_id, pais_codigo, fecha_exp) VALUES (?, ?, ?, ?)";
        
        // SQL para la tabla general (Pasaporte_Base)
        // Se añade la columna "tipo_pass" para registrar el tipo de pasaporte.
        String baseSql = "INSERT INTO public.\"Pasaporte_Base\" (id, \"Numero\", titular_id, pais_codigo, fecha_exp, tipo_pass) VALUES (?, ?, ?, ?, ?, ?)"; // 'id' será el UUID
        
        // Usamos un único try-with-resources para gestionar la conexión y la transacción
        try (Connection conn = conectar()) {
            // 1. Iniciar la transacción
            conn.setAutoCommit(false);

            // 2. Insertar en la tabla específica
            PreparedStatement psSpecific = conn.prepareStatement(specificSql);
            psSpecific.setInt(1, Integer.parseInt(pasaporte.getId())); // 'numero' sigue siendo el ID del formulario
            psSpecific.setString(2, pasaporte.getTitular().getId());
            psSpecific.setString(3, pasaporte.getPais().getCodigo());
            psSpecific.setDate(4, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            psSpecific.executeUpdate();

            // 3. Insertar en la tabla base
            PreparedStatement psBase = conn.prepareStatement(baseSql);
            psBase.setObject(1, java.util.UUID.randomUUID(), Types.OTHER); // Elemento de seguridad: Se genera un UUID para la columna 'id'
            psBase.setInt(2, Integer.parseInt(pasaporte.getId())); // El número del formulario va a la columna 'Numero'
            psBase.setString(3, pasaporte.getTitular().getId());
            psBase.setString(4, pasaporte.getPais().getCodigo());
            psBase.setDate(5, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            psBase.setString(6, tipoPasaporte);
            psBase.executeUpdate();

            // 4. Si todo fue bien, confirmar la transacción
            conn.commit();
            return "Pasaporte creado con éxito en ambas tablas.";

        } catch (SQLException e) {
            // El rollback es manejado implícitamente por el try-with-resources al cerrar una conexión fallida sin commit.
            e.printStackTrace();
            return "Error al crear pasaporte: " + e.getMessage();
        }
    }

    public Pasaporte read(String idPasaporte, String tipoPasaporte) {
        String tableName = getTableName(tipoPasaporte);
        // Se asume que la columna 'numero' es de tipo texto (varchar/text)
        // Se añade un JOIN para obtener también el nombre del titular.
        String sql = "SELECT p.numero, p.titular_id, p.pais_codigo, p.fecha_exp, t.nombre as titular_nombre " +
                     "FROM public." + tableName + " p " +
                     "LEFT JOIN public.\"Titular\" t ON p.titular_id = t.id " + // Asume que la tabla Titular se llama "Titular"
                     "WHERE p.numero = ?";

        try (Connection conn = conectar()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idPasaporte); // Se corrige para usar String, ya que la columna 'numero' es de tipo texto
            ps.setInt(1, Integer.parseInt(idPasaporte)); // Se corrige para usar el tipo de dato correcto (entero)
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Se crea un objeto Titular con los datos recuperados del JOIN
                Titular titular = new Titular(rs.getString("titular_id"), rs.getString("titular_nombre"), null);

                Pasaporte p = new Pasaporte(
                        rs.getString("numero"),
                        rs.getString("fecha_exp"),
                        titular,
                        null // reconstruir Pais si lo necesitas
                );
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no se encontró
    }

    public String update(Pasaporte pasaporte, String tipoPasaporte) {
        String tableName = getTableName(tipoPasaporte);
        String specificSql = "UPDATE public." + tableName + " SET titular_id = ?, fecha_exp = ? WHERE numero = ?";
        String baseSql = "UPDATE public.\"Pasaporte_Base\" SET titular_id = ?, fecha_exp = ? WHERE id = ?";

        try (Connection conn = conectar()) {
            // 1. Iniciar la transacción
            conn.setAutoCommit(false);

            // 2. Actualizar la tabla específica
            PreparedStatement psSpecific = conn.prepareStatement(specificSql);
            psSpecific.setString(1, pasaporte.getTitular().getId());
            psSpecific.setDate(2, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            psSpecific.setString(3, pasaporte.getId()); // Se corrige para usar String, ya que la columna 'numero' es de tipo texto
            psSpecific.setInt(3, Integer.parseInt(pasaporte.getId())); // Se corrige para usar el tipo de dato correcto (entero)
            int specificRows = psSpecific.executeUpdate();

            // 3. Actualizar la tabla base
            PreparedStatement psBase = conn.prepareStatement(baseSql);
            psBase.setString(1, pasaporte.getTitular().getId());
            psBase.setDate(2, java.sql.Date.valueOf(pasaporte.getFechaExp()));
            psBase.setInt(3, Integer.parseInt(pasaporte.getId())); // La columna 'id' en la tabla base es integer
            int baseRows = psBase.executeUpdate();

            // 4. Confirmar o revertir la transacción
            if (specificRows > 0 && baseRows > 0) {
                conn.commit();
                return "Pasaporte actualizado en ambas tablas.";
            } else {
                conn.rollback();
                return "No se encontró el pasaporte para actualizar.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // El rollback es implícito si la conexión se cierra por un error antes del commit
            return "Error al actualizar el pasaporte: " + e.getMessage();
        }
    }

    public String delete(String idPasaporte, String tipoPasaporte) {
        String tableName = getTableName(tipoPasaporte);
        String specificSql = "DELETE FROM public." + tableName + " WHERE numero = ?";
        String baseSql = "DELETE FROM public.\"Pasaporte_Base\" WHERE id = ?";

        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);

            // 1. Eliminar de la tabla específica
            PreparedStatement psSpecific = conn.prepareStatement(specificSql);
            psSpecific.setString(1, idPasaporte); // Se corrige para usar String, ya que la columna 'numero' es de tipo texto
            psSpecific.setInt(1, Integer.parseInt(idPasaporte)); // Se corrige para usar el tipo de dato correcto (entero)
            int specificRows = psSpecific.executeUpdate();

            // 2. Eliminar de la tabla base
            PreparedStatement psBase = conn.prepareStatement(baseSql);
            psBase.setInt(1, Integer.parseInt(idPasaporte)); // La columna 'id' es de tipo integer
            int baseRows = psBase.executeUpdate();

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
}
