package com.example.src;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SignUp {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField displayNameField;
    @FXML
    public Button registerButton;
    @FXML
    public Label messageLabel;
    @FXML
    public Button registerClick;
    public Connection connection;

    public SignUp() {
        usernameField = new TextField();
        passwordField = new PasswordField();
        displayNameField = new TextField();
        registerButton = new Button();
        messageLabel = new Label();

        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        try {
            DatabaseInitializer.createTables(); // This will create all necessary tables if they don't exist
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = databaseInitializer.getConnection();
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String displayName = displayNameField.getText();

        // Check if any field is empty
        if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            System.out.println("Registration failed! All fields are required.");
            messageLabel.setText("Registration failed! All fields are required.");
            return;
        }

        try {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseInitializer.INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, displayName);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int userID = generatedKeys.getInt(1);
                        messageLabel.setStyle("-fx-text-fill: green;");
                        messageLabel.setText("Registration successful! Welcome, " + displayName + ", your userID is " + userID);
                    } else {
                        System.out.println("Registration failed!");
                        messageLabel.setText("Registration failed!");
                    }
                } else {
                    System.out.println("Registration failed!");
                    messageLabel.setText("Registration failed!");
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                messageLabel.setText("Registration failed! Username already taken.");
            } else {
                messageLabel.setText("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    @FXML
    protected void OnReturnClick()
        throws IOException {
        Stage stage = (Stage) registerClick.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-or-signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}
