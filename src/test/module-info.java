module com.example.test {
    requires com.example.src;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires java.sql;

    opens com.example.test to org.junit.platform.commons;
}