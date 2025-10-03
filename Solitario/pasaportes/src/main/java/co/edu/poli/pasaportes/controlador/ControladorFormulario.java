package co.edu.poli.pasaportes.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDate;

import co.edu.poli.pasaportes.modelo.*;
import co.edu.poli.pasaportes.servicios.*;
import co.edu.poli.pasaportes.repositorio.PasaporteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControladorFormulario {

    @FXML
    private Button actualizar;

    @FXML
    private TextArea area1;

    @FXML
    private Button buscar;

    @FXML
    private Button buscarTodo;

    @FXML
    private ComboBox<String> combo1;

    @FXML
    private Button crear;

    @FXML
    private Button eliminar;

    @FXML
    private TextField txt1; // ID del pasaporte

    @FXML
    private TextField txt2; // Nombre del titular

    // ===================================
    // 🔹 CAMPOS FALTANTES AGREGADOS
    // ===================================
    @FXML
    private TextArea area2; // Corresponde al fx:id="area2" en el FXML

    @FXML
    private Button arbol;  // Corresponde al fx:id="arbol" en el FXML
    
    // ===================================
    // 🔹 Normalizar tipo
    // ===================================
    private String normalizarTipo(String tipoUI) {
        if (tipoUI == null) return null;

        String normalizado = Normalizer.normalize(tipoUI, Normalizer.Form.NFD)
                                    .replaceAll("\\p{M}", "") // elimina tildes
                                    .toLowerCase();

        if (normalizado.equals("diplomatico")) return "Diplomático";
        if (normalizado.equals("ordinario")) return "Ordinario";
        return null;
    }

    // ===================================
    // 🔹 Inicializar ComboBox
    // ===================================
    @FXML
    private void initialize() {
        combo1.getItems().setAll("Ordinario", "Diplomático");
    }

    private String resolverTipoPorId(String id) throws SQLException {
        PasaporteRepo repo = new PasaporteRepo();
        Pasaporte p = repo.read(id, "Ordinario");
        if (p != null) return "Ordinario";
        p = repo.read(id, "Diplomático");
        if (p != null) return "Diplomático";
        return null;
    }

    // ===================================
    // 🔹 Actualizar Titular
    // ===================================
    private String actualizarNombreTitular(String titularId, String nuevoNombre) {
        try {
            Connection conn = Singleton.getInstance().conectar();
            String sql = "UPDATE public.titular SET nombre = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nuevoNombre);
                ps.setString(2, titularId);
                int rows = ps.executeUpdate();
                return rows > 0 ? "Nombre de titular actualizado." : "No se encontró el titular.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar titular: " + e.getMessage();
        }
    }

    // ===================================
    // 🔹 Crear
    // ===================================
    @FXML
    void Crear(ActionEvent event) {
        area1.clear();
        try {
            String id = txt1.getText().trim();
            String nombre = txt2.getText().trim();
            String tipoUI = combo1.getValue();

            if (id.isEmpty() || nombre.isEmpty() || tipoUI == null) {
                area1.setText("⚠️ Debes ingresar ID, Nombre y seleccionar un tipo.");
                return;
            }

            Singleton.getInstance().conectar();

            Titular titular = new Titular("TIT-" + id, nombre, "Colombiana");
            Pais pais = new Pais("CO", "Colombia");
            String fecha = LocalDate.now().toString();

            String tipoBD = normalizarTipo(tipoUI);
            if (tipoBD == null) {
                area1.setText("⚠️ Tipo de pasaporte no reconocido.");
                return;
            }

            FactoryPasaporte factory = tipoBD.equals("Ordinario")
                    ? new FactoryOrdinario()
                    : new FactoryDiplomatico();

            Pasaporte pasaporte = factory.crearPasaporte(tipoBD, id, fecha, titular, pais);

            PasaporteRepo repo = new PasaporteRepo();
            String mensaje = repo.create(pasaporte, tipoBD);

            area1.setText("Pasaporte creado\n" +
                          "Tipo: " + tipoBD +
                          "\nID: " + pasaporte.getId() +
                          "\nTitular: " + titular.getNombre() +
                          "\nResultado BD: " + mensaje);
        } catch (Exception e) {
            area1.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================================
    // 🔹 Actualizar
    // ===================================
    @FXML
    void Actualizar(ActionEvent event) {
        area1.clear();
        try {
            String id = txt1.getText().trim();
            String nuevoNombre = txt2.getText().trim();
            if (id.isEmpty() || nuevoNombre.isEmpty()) {
                area1.setText("Debes ingresar ID y el nuevo Nombre.");
                return;
            }

            Singleton.getInstance().conectar();
            PasaporteRepo repo = new PasaporteRepo();

            String tipo = resolverTipoPorId(id);
            if (tipo == null) {
                area1.setText("No existe un pasaporte con ID " + id + ".");
                return;
            }

            Pasaporte p = repo.read(id, tipo);

            String msgTitular = actualizarNombreTitular(p.getTitular().getId(), nuevoNombre);

            p.getTitular().setNombre(nuevoNombre);
            String msgPasaporte = repo.update(p, tipo);

            area1.setText("Actualizar: " + msgTitular + "\n" + msgPasaporte);

        } catch (Exception e) {
            area1.setText("Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================================
    // 🔹 Buscar
    // ===================================
    @FXML
    void Buscar(ActionEvent event) {
        area1.clear();
        try {
            String id = txt1.getText().trim();
            if (id.isEmpty()) {
                area1.setText("Debes ingresar el ID a buscar.");
                return;
            }

            Singleton.getInstance().conectar();
            PasaporteRepo repo = new PasaporteRepo();

            String tipo = resolverTipoPorId(id);
            if (tipo == null) {
                area1.setText("No existe un pasaporte con ID " + id + ".");
                return;
            }

            Pasaporte p = repo.read(id, tipo);
            if (p != null) {
                area1.setText(
                    "Pasaporte encontrado\n" +
                    "Tipo: " + tipo + "\n" +
                    "ID: " + p.getId() + "\n" +
                    "Titular: " + p.getTitular().getNombre() + "\n" +
                    "Nacionalidad: " + p.getTitular().getNacionalidad() + "\n" +
                    "Fecha Exp: " + p.getFechaExp()
                );
            } else {
                area1.setText("No existe un pasaporte con ID " + id + ".");
            }
        } catch (Exception e) {
            area1.setText("Error al buscar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================================
    // 🔹 Buscar Todo
    // ===================================
    @FXML
    void BuscarTodo(ActionEvent event) {
        area1.clear();
        try {
            Singleton.getInstance().conectar();
            PasaporteRepo repo = new PasaporteRepo();
            var lista = repo.readAll();

            if (lista == null || lista.isEmpty()) {
                area1.setText("No hay registros.");
                return;
            }

            StringBuilder sb = new StringBuilder("Lista de pasaportes\n");
            for (Pasaporte p : lista) {
                sb.append(p.getId()).append(" | ")
                  .append(p.getTitular().getNombre()).append(" | ")
                  .append(p.getTitular().getNacionalidad()).append(" | ")
                  .append(p.getFechaExp()).append("\n");
            }
            area1.setText(sb.toString());
        } catch (Exception e) {
            area1.setText("Error al listar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================================
    // 🔹 Eliminar
    // ===================================
    @FXML
    void Eliminar(ActionEvent event) {
        area1.clear();
        try {
            String id = txt1.getText().trim();
            if (id.isEmpty()) {
                area1.setText("Debes ingresar el ID a eliminar.");
                return;
            }

            Singleton.getInstance().conectar();
            PasaporteRepo repo = new PasaporteRepo();

            String tipo = resolverTipoPorId(id);
            if (tipo == null) {
                area1.setText("No existe un pasaporte con ID " + id + ".");
                return;
            }

            String msg = repo.delete(id, tipo);
            area1.setText(msg);

        } catch (Exception e) {
            area1.setText("Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================================
    // 🔹 Mostrar Arbol (Método de acción faltante para fx:id="arbol")
    // ===================================
    @FXML
    void Arbol(ActionEvent event) {
        // Aquí debes implementar la lógica que quieres para el botón "Mostrar Arbol".
        // Por ejemplo, mostrar algo en area2:
        area2.setText("El botón 'Mostrar Arbol' fue presionado. Implementa aquí la lógica de árbol.");
    }

}