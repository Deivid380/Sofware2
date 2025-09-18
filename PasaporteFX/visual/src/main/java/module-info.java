/**
 * Define el módulo de la aplicación y sus dependencias.
 */
module co.edu.poli.visual {
    // Dependencias de JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Dependencia para la conectividad con bases de datos
    requires java.sql;

    // Permite que JavaFX acceda a los controladores y a la clase principal
    opens co.edu.poli.controlador to javafx.fxml;
    opens co.edu.poli.vista to javafx.fxml, javafx.graphics;
}