module com.example.src {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.src to javafx.fxml;
    exports com.example.src;
}