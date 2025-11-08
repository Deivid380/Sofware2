package co.edu.poli.pasaportes.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import co.edu.poli.pasaportes.modelo.*;
import co.edu.poli.pasaportes.servicios.*;

import java.time.LocalDate;

public class ControladorCommand {

    @FXML
    private Button crear;

    @FXML
    private Button eliminar;

    @FXML
    private TextField id;

    @FXML
    private TextArea info;

    @FXML
    private Button mostrar;

    @FXML
    private TextField nombre;

    // Adaptador y gestor
    private PasaporteServiceAdapter adapter = new PasaporteServiceAdapter();
    private GestorComandos gestor = new GestorComandos();

    @FXML
    void Crear(ActionEvent event) {
        String idTxt = id.getText();
        String nombreTxt = nombre.getText();

        if (idTxt.isEmpty() || nombreTxt.isEmpty()) {
            info.setText("Por favor ingresa ID y nombre.");
            return;
        }

        // Crear datos base
        Pais pais = new Pais("Co", "Colombia");
        Titular titular = new Titular(idTxt, nombreTxt, "Colombiana");
        ElementoSeguridad seguridad = new Chip();
        Pasaporte p = new PasaporteOrdinario(idTxt, LocalDate.now().toString(), titular, pais, seguridad);

        // Ejecutar comando
        Comando comando = new AgregarPasaporteComando(adapter, p);
        gestor.ejecutarComando(comando);

        info.setText("Pasaporte creado: " + p.getId());
        id.clear();
        nombre.clear();
    }

    @FXML
    void Eliminar(ActionEvent event) {
        String idTxt = id.getText();

        if (idTxt.isEmpty()) {
            info.setText("Debes ingresar una ID para eliminar.");
            return;
        }

        // Buscar el pasaporte en la lista
        Pasaporte aEliminar = adapter.listarPasaportes()
                .stream()
                .filter(p -> p.getId().equals(idTxt))
                .findFirst()
                .orElse(null);

        if (aEliminar == null) {
            info.setText("No existe un pasaporte con ID: " + idTxt);
            return;
        }

        Comando comando = new EliminarPasaporteComando(adapter, aEliminar);
        gestor.ejecutarComando(comando);

        info.setText("Pasaporte eliminado: " + idTxt);
        id.clear();
    }

    @FXML
    void Mostrar(ActionEvent event) {
        StringBuilder sb = new StringBuilder("PASAPORTES GUARDADOS:\n");
        for (Pasaporte p : adapter.listarPasaportes()) {
            sb.append("- ").append(p.getId())
              .append(" | ").append(p.getTitular().getNombre())
              .append(" | ").append(p.getPais().getNombre())
              .append(" | ").append(p.getFechaExp())
              .append("\n");
        }

        info.setText(sb.toString());
    }
}
