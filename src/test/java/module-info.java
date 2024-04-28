module com.example.src {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;
    requires java.desktop;

    opens com.example.src to javafx.fxml;
    exports com.example.src;
}