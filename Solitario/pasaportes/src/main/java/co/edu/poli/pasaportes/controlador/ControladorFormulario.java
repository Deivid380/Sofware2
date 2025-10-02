package co.edu.poli.pasaportes.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
// Mantener los imports del modelo para Pasaporte, Titular, etc.
import co.edu.poli.pasaportes.modelo.*; 
import co.edu.poli.pasaportes.servicios.*;
import co.edu.poli.pasaportes.repositorio.PasaporteRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// 🟢 IMPORTACIONES CLAVE para los Patrones (YA CORREGIDAS)
// Ciudad es la clase base inmutable y está en 'modelo'
import co.edu.poli.pasaportes.modelo.Ciudad; 
// Las clases de los patrones Composite y Adapter vienen del paquete servicios
import co.edu.poli.pasaportes.servicios.EspacioGeografico;
import co.edu.poli.pasaportes.servicios.Region;
import co.edu.poli.pasaportes.servicios.CiudadAdapter;


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
    private TextField txt1;
    @FXML
    private TextField txt2;
    
    // Elementos para la nueva funcionalidad del árbol geográfico
    @FXML
    private Button btnMostrarArbol;
    @FXML
    private TextArea textAreaArbol;

    private EspacioGeografico arbolGeografico;

    @FXML
    private void initialize() {
        combo1.getItems().setAll("Ordinario", "Diplomático");
        // Se construye el objeto quemado del árbol al iniciar
        construirArbolGeografico();
    }
    
    // ----------------------------------------------------
    // Lógica para los pasaportes (CRUD)
    // ----------------------------------------------------

    private String normalizarTipo(String tipoUI) {
        if (tipoUI == null) return null;
        if (tipoUI.equalsIgnoreCase("Ordinario")) return "Ordinario";
        if (tipoUI.toLowerCase().startsWith("diplom")) return "Diplomático";
        return null;
    }

    private String resolverTipoPorId(String id) throws SQLException {
        PasaporteRepo repo = new PasaporteRepo();
        Pasaporte p = repo.read(id, "Ordinario");
        if (p != null) return "Ordinario";
        p = repo.read(id, "Diplomático");
        if (p != null) return "Diplomático";
        return null;
    }

    private String actualizarNombreTitular(String titularId, String nuevoNombre) {
        try {
            Connection conn = Singleton.getInstance().conexionActiva();
            String sql = "UPDATE public.\"Titular\" SET nombre = ? WHERE id = ?";
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

            FactoryPasaporte factory = tipoUI.equalsIgnoreCase("Ordinario")
                    ? new FactoryOrdinario()
                    : new FactoryDiplomatico();

            Pasaporte pasaporte = factory.crearPasaporte(tipoUI, id, fecha, titular, pais);

            if (pasaporte == null) {
                area1.setText("⚠️ No se pudo crear el pasaporte. Tipo inválido: " + tipoUI);
                return;
            }

            String tipoBD = normalizarTipo(tipoUI);
            if (tipoBD == null) {
                area1.setText("⚠️ Tipo no reconocido: " + tipoUI);
                return;
            }

            PasaporteRepo repo = new PasaporteRepo();
            String mensaje = repo.create(pasaporte, tipoBD);

            area1.setText("✅ Pasaporte creado\n" +
                          "Tipo: " + tipoUI +
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

            String tipoBD = resolverTipoPorId(id);
            if (tipoBD == null) {
                area1.setText("No existe un pasaporte con ID " + id + ".");
                return;
            }

            Pasaporte p = repo.read(id, tipoBD);
            String msgTitular = actualizarNombreTitular(p.getTitular().getId(), nuevoNombre);

            p.getTitular().setNombre(nuevoNombre);
            String msgPasaporte = repo.update(p, tipoBD);

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

            String tipoBD = resolverTipoPorId(id);
            if (tipoBD == null) {
                area1.setText("No existe un pasaporte con ID " + id + ".");
                return;
            }

            Pasaporte p = repo.read(id, tipoBD);
            if (p != null) {
                area1.setText(
                    "Pasaporte encontrado\n" +
                    "Tipo: " + tipoBD + "\n" +
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

            String tipoBD = resolverTipoPorId(id);
            if (tipoBD == null) {
                area1.setText("No existe un pasaporte con ID " + id + ".");
                return;
            }

            String msg = repo.delete(id, tipoBD);
            area1.setText(msg);

        } catch (Exception e) {
            area1.setText("Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------
    // Lógica para los patrones Adapter y Composite
    // ----------------------------------------------------

    /**
     * Construye el objeto quemado del árbol Composite con Adapter para las ciudades.
     */
    private void construirArbolGeografico() {
        // Región Andina (Composite)
        Region regionAndina = new Region("Andina");
        Region subRegionCundinamarca = new Region("Cundinamarca"); // Sub-región/Departamento

        // ADAPTER: Se adaptan las clases Ciudad (del modelo) a la interfaz EspacioGeografico (del servicio)
        subRegionCundinamarca.agregarEspacio(new CiudadAdapter(new Ciudad("BOG", "Bogotá")));
        subRegionCundinamarca.agregarEspacio(new CiudadAdapter(new Ciudad("FNZ", "Funza")));

        Region subRegionAntioquia = new Region("Antioquia");
        subRegionAntioquia.agregarEspacio(new CiudadAdapter(new Ciudad("MED", "Medellín")));

        // COMPOSITE: Se añaden Composites (subRegion) y Hojas (CiudadAdapter)
        regionAndina.agregarEspacio(subRegionCundinamarca);
        regionAndina.agregarEspacio(subRegionAntioquia);
        regionAndina.agregarEspacio(new CiudadAdapter(new Ciudad("TUN", "Tunja")));

        // Otras regiones (Composite)
        Region regionOrinoquia = new Region("Orinoquía");
        regionOrinoquia.agregarEspacio(new CiudadAdapter(new Ciudad("YOP", "Yopal")));
        
        Region regionPacifica = new Region("Pacífica");
        regionPacifica.agregarEspacio(new CiudadAdapter(new Ciudad("CAL", "Cali")));

        // Crear el árbol completo (Raíz)
        arbolGeografico = new Region("Colombia");
        ((Region) arbolGeografico).agregarEspacio(regionAndina);
        ((Region) arbolGeografico).agregarEspacio(regionOrinoquia);
        ((Region) arbolGeografico).agregarEspacio(regionPacifica);
    }
    
    /**
     * Maneja el evento del botón, recorre el árbol geográfico y muestra el resultado en el TextArea.
     */
    @FXML
    public void onMostrarArbol(ActionEvent event) {
        // Técnica para capturar la salida de System.out.println en una cadena de texto
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        // COMPOSITE: Llamada recursiva para mostrar la jerarquía
        arbolGeografico.mostrarInformacion();

        // Restaurar la salida de consola
        System.out.flush();
        System.setOut(old);

        // Mostrar la salida capturada en el TextArea de JavaFX
        textAreaArbol.setText(baos.toString());
    }
}