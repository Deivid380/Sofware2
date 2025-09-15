module co.edu.poli.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.poli.javafx to javafx.fxml;
    exports co.edu.poli.javafx;
}
