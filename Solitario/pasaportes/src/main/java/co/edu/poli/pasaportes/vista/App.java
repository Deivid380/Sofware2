package co.edu.poli.pasaportes.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        cambiarVista("formulario");
        primaryStage.setTitle("Gestión de Pasaportes");
        primaryStage.show();
    }

    public static void cambiarVista(String fxml) {
        try {
            // ✅ CORRECCIÓN FINAL: Usamos la ruta completa del paquete 
            // porque el archivo está en /resources/co/edu/poli/pasaportes/
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/co/edu/poli/pasaportes/" + fxml + ".fxml")); 
            
            Parent root = fxmlLoader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista FXML: " + fxml);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
