package co.edu.poli.pasaportes.controlador;

import co.edu.poli.pasaportes.modelo.Pais;
import co.edu.poli.pasaportes.servicios.PaisStateAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorState {

    @FXML
    private TextField codigo;

    @FXML
    private Button crear;

    @FXML
    private Button eNormal;

    @FXML
    private Button eRevision;

    @FXML
    private Button fronCerrada;

    @FXML
    private TextField nombre;

    @FXML
    private SplitMenuButton paises;

    @FXML
    private Button solVisa;

    // Lista local de adapters para manejar los países creados
    private final List<PaisStateAdapter> adapters = new ArrayList<>();

    // Adapter actualmente seleccionado (si hay alguno)
    private PaisStateAdapter seleccionado = null;

    // ----------------- UTIL -----------------
    private void showInfo(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }

    private void showError(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }

    // ----------------- ACCIONES FXML -----------------

    @FXML
    void Crear(ActionEvent event) {
        String cod = codigo.getText() != null ? codigo.getText().trim() : "";
        String nom = nombre.getText() != null ? nombre.getText().trim() : "";

        if (cod.isEmpty() || nom.isEmpty()) {
            showError("Datos incompletos", "Debe ingresar el código y el nombre del país.");
            return;
        }

        // Crear el Pais (modelo inmutable para nuestro caso) y su adapter
        Pais pais = new Pais(cod, nom);
        PaisStateAdapter adapter = new PaisStateAdapter(pais);

        // Guardarlo en la lista y en el SplitMenuButton
        adapters.add(adapter);

        MenuItem item = new MenuItem(nom + " (" + cod + ")");
        // Guardamos el adapter en userData para recuperarlo al seleccionar
        item.setUserData(adapter);
        item.setOnAction(e -> {
            // Cuando el usuario selecciona el país del menú
            PaisStateAdapter aSel = (PaisStateAdapter) item.getUserData();
            seleccionarPais(aSel);
        });

        paises.getItems().add(item);

        // Opcional: seleccionar automáticamente el país creado
        seleccionarPais(adapter);

        // Limpiar campos
        codigo.clear();
        nombre.clear();

        showInfo("País creado", "País '" + nom + "' con código '" + cod + "' creado y agregado al menú.");
    }

    @FXML
    void EstNormal(ActionEvent event) {
        if (!validarSeleccion()) return;

        try {
            seleccionado.irAEstadoNormal();
            showInfo("Transición exitosa",
                    "El país '" + seleccionado.getPais().getNombre() + "' ahora está en: " + seleccionado.obtenerEstadoActual());
        } catch (IllegalStateException ex) {
            showError("Transición inválida", ex.getMessage());
        } catch (Exception ex) {
            showError("Error", "Ocurrió un error al cambiar a Estado Normal.");
        }
    }

    @FXML
    void EstRevision(ActionEvent event) {
        if (!validarSeleccion()) return;

        try {
            seleccionado.irAEnRevision();
            showInfo("Transición exitosa",
                    "El país '" + seleccionado.getPais().getNombre() + "' ahora está en: " + seleccionado.obtenerEstadoActual());
        } catch (IllegalStateException ex) {
            showError("Transición inválida", ex.getMessage());
        } catch (Exception ex) {
            showError("Error", "Ocurrió un error al cambiar a Estado En Revisión.");
        }
    }

    @FXML
    void FrontCerrada(ActionEvent event) {
        if (!validarSeleccion()) return;

        try {
            seleccionado.cerrarFrontera();
            showInfo("Transición exitosa",
                    "El país '" + seleccionado.getPais().getNombre() + "' ahora está en: " + seleccionado.obtenerEstadoActual());
        } catch (IllegalStateException ex) {
            showError("Transición inválida", ex.getMessage());
        } catch (Exception ex) {
            showError("Error", "Ocurrió un error al cambiar a Frontera Cerrada.");
        }
    }

    @FXML
    void SoliVisa(ActionEvent event) {
        if (!validarSeleccion()) return;

        try {
            seleccionado.solicitarVisa();
            showInfo("Transición exitosa",
                    "El país '" + seleccionado.getPais().getNombre() + "' ahora está en: " + seleccionado.obtenerEstadoActual());
        } catch (IllegalStateException ex) {
            showError("Transición inválida", ex.getMessage());
        } catch (Exception ex) {
            showError("Error", "Ocurrió un error al cambiar a Solicitud de Visa.");
        }
    }

    // ----------------- MÉTODOS AUXILIARES -----------------

    /**
     * Selecciona el adapter y actualiza el SplitMenuButton y los campos código/nombre
     */
    private void seleccionarPais(PaisStateAdapter adapter) {
        this.seleccionado = adapter;
        // Actualiza el texto del split menu para mostrar el país seleccionado
        paises.setText(adapter.getPais().getNombre() + " (" + adapter.getPais().getCodigo() + ")");
        // Actualiza los campos para que el usuario vea los datos
        codigo.setText(adapter.getPais().getCodigo());
        nombre.setText(adapter.getPais().getNombre());
    }

    /**
     * Verifica que haya un país seleccionado; si no, muestra un error y devuelve false.
     */
    private boolean validarSeleccion() {
        if (seleccionado == null) {
            showError("No hay país seleccionado", "Seleccione primero un país desde el menú para aplicar la transición.");
            return false;
        }
        return true;
    }
}

