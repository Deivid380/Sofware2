package co.edu.poli.pasaportes.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDate;
import co.edu.poli.pasaportes.modelo.*;
import co.edu.poli.pasaportes.servicios.*;
import co.edu.poli.pasaportes.vista.App;
import co.edu.poli.pasaportes.repositorio.PasaporteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Button command;

    @FXML
    private Button estra;

    @FXML
    private TextField txt1;

    @FXML
    private TextField txt2;

    @FXML
    private TextArea area2;

    @FXML
    private Button arbol;

    private String normalizarTipo(String tipoUI) {
        if (tipoUI == null)
            return null;

        String normalizado = Normalizer.normalize(tipoUI, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // elimina tildes
                .toLowerCase();

        if (normalizado.equals("diplomatico"))
            return "Diplomático";
        if (normalizado.equals("ordinario"))
            return "Ordinario";
        return null;
    }

    @FXML
    private void initialize() {
        combo1.getItems().setAll("Ordinario", "Diplomático");
    }

    private String resolverTipoPorId(String id) throws SQLException {
        PasaporteRepo repo = new PasaporteRepo();
        Pasaporte p = repo.read(id, "Ordinario");
        if (p != null)
            return "Ordinario";
        p = repo.read(id, "Diplomático");
        if (p != null)
            return "Diplomático";
        return null;
    }

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
                                "Fecha Exp: " + p.getFechaExp());
            } else {
                area1.setText("No existe un pasaporte con ID " + id + ".");
            }
        } catch (Exception e) {
            area1.setText("Error al buscar: " + e.getMessage());
            e.printStackTrace();
        }
    }

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

    @FXML
    void Arbol(ActionEvent event) {

        area2.setText("El botón 'Mostrar Arbol' fue presionado. Implementa aquí la lógica de árbol.");
    }

    private void cambiarVista(String vista) {
        System.out.println("Intentando abrir la vista: " + vista);
        try {
            App.cambiarVista(vista);
            System.out.println("Vista " + vista + " abierta correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la vista de " + vista);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    void con(ActionEvent event) {
        cambiarVista("formularioCommand");
    }

    @FXML
    void Estrate(ActionEvent event) {
        try {

            Titular titularDemo = new Titular("10203040", "Laura Martínez", "Colombiana");

            Pais paisDemo = new Pais("COL", "Colombia");

            ElementoSeguridad seguridadDemo = new Chip();

            String idPasaporte = "ID-DEMO-2025";
            String fechaExp = "2025-11-10";

            Pasaporte pasaportePrueba = new PasaporteOrdinario(
                    idPasaporte,
                    fechaExp,
                    titularDemo,
                    paisDemo,
                    seguridadDemo);

            AdapterPasaporte adaptadorParaDemo = new AdapterPasaporte(pasaportePrueba);

            // Código en ControladorFormulario.java:
            FXMLLoader loader = new FXMLLoader(ControladorStrategy.class.getResource("/co/edu/poli/pasaportes/vista/StrategyDemo.fxml"));
            Parent root = loader.load();

            ControladorStrategy controladorDemo = loader.getController();
            controladorDemo.initData(adaptadorParaDemo);

            Stage strategyStage = new Stage();
            strategyStage.setTitle("Patrón Strategy");
            strategyStage.setScene(new Scene(root));
            strategyStage.initModality(Modality.APPLICATION_MODAL);
            strategyStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}