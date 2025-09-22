module co.edu.poli.pasaportes {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    exports co.edu.poli.pasaportes.vista;
    exports co.edu.poli.pasaportes.modelo;
    exports co.edu.poli.pasaportes.repositorio;
    exports co.edu.poli.pasaportes.servicios;
    exports co.edu.poli.pasaportes.controlador;

    opens co.edu.poli.pasaportes.controlador to javafx.fxml;
}

