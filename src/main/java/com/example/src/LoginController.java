package com.example.src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class LoginController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField displayNameField;
    @FXML
    public Button loginButton;
    @FXML
    public Label messageLabel;
    @FXML
    public Button registerClick;

    private Connection connection;

    public LoginController() {
        usernameField = new TextField();
        passwordField = new PasswordField();
        displayNameField = new TextField();
        loginButton = new Button();
        messageLabel = new Label();
        registerClick = new Button();

        // Initialize the database connection
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/root/users.db");
            createUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates the users table if it doesn't exist.
     */
    private void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, display_name TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * Handles the login button click event.
     * Checks if the provided username and password match a user in the database.
     */

/*
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            String sql = "SELECT display_name FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String displayName = resultSet.getString("display_name");
                    messageLabel.setText("Welcome, " + displayName + "!");


                } else {
                    messageLabel.setText("Invalid username or password!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            String sql = "SELECT display_name, userID FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String displayName = resultSet.getString("display_name");
                    int userID = resultSet.getInt("userID");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/src/homepage-view.fxml"));
                    Parent root = loader.load();
                    MenuController menuController = loader.getController();
                    menuController.setWelcomeMessage(username, userID);

                    Stage stage = (Stage) messageLabel.getScene().getWindow();
                    stage.setScene(new Scene(root, 800, 650));
                    stage.show();

                    messageLabel.setText("Welcome, " + displayName + "!");
                } else {
                    messageLabel.setText("Invalid username or password!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Failed to load user interface.");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error connecting to the database.");
        }
    }


    /**
     * Handles the register button click event.
     * Inserts a new user into the database with the provided username, password, and display name.
     */
    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String displayName = displayNameField.getText();

        try {
            String sql = "INSERT INTO users (username, password, display_name) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, displayName);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    // Registration successful, display a success message
                    System.out.println("Registration successful!");
                } else {
                    // Registration failed, display an error message
                    System.out.println("Registration failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
