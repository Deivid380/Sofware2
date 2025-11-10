package co.edu.poli.pasaportes.vista;

import co.edu.poli.pasaportes.modelo.SolicitudPasaporte;
import co.edu.poli.pasaportes.servicios.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChainOfResponsibilityFX extends Application {
    
    private VerificadorHandler cadena;

    public ChainOfResponsibilityFX() {
        // CONSTRUYE LA CADENA (igual que refactoring.guru)
        cadena = new VerificadorIDHandler();
        cadena.setSiguiente(new VerificadorAntecedentesHandler())
              .setSiguiente(new GeneradorPasaporteHandler());
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titulo = new Label("SISTEMA DE PASAPORTES - CHAIN OF RESPONSIBILITY");
        titulo.setFont(new Font("Arial Bold", 16));
        titulo.setTextFill(Color.DARKBLUE);

        // Formulario simple
        TextField txtId = new TextField("12345");
        CheckBox chkDocumentacion = new CheckBox("Documentación completa");
        chkDocumentacion.setSelected(true);
        Button btnProcesar = new Button("PROCESAR SOLICITUD");
        btnProcesar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        TextArea log = new TextArea();
        log.setPrefHeight(300);
        log.setFont(new Font("Courier New", 11));
        log.setEditable(false);

        btnProcesar.setOnAction(e -> {
            SolicitudPasaporte solicitud = new SolicitudPasaporte(
                txtId.getText(),
                "V-01",
                2500.0,
                chkDocumentacion.isSelected()
            );
            
            log.clear();
            log.appendText("═══════════════════════════════════════\n");
            log.appendText("INICIANDO PROCESO\n");
            log.appendText("═══════════════════════════════════════\n\n");
            
            cadena.verificar(solicitud, log);
        });

        root.getChildren().addAll(titulo, txtId, chkDocumentacion, btnProcesar, log);
        
        Scene scene = new Scene(root, 650, 500);
        primaryStage.setTitle("Chain of Responsibility - Pasaportes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
