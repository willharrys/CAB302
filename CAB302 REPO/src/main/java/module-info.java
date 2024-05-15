module com.example.src {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.src to javafx.fxml;
    exports com.example.src to com.example.test, javafx.graphics;
}