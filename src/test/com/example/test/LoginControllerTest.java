package com.example.test;

import com.example.src.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


class LoginControllerTest {

    private LoginController loginController;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField displayNameField;
    private Button loginButton;
    private Button registerButton;
    private Label messageLabel;
    private Button registerClick;
    private Connection connection;

    void setUp() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/src/login.fxml"));
        Parent root = loader.load();
        loginController = loader.getController();
        usernameField = (TextField) root.lookup("#usernameField");
        passwordField = (PasswordField) root.lookup("#passwordField");
        displayNameField = (TextField) root.lookup("#displayNameField");
        loginButton = (Button) root.lookup("#loginButton");
        registerButton = (Button) root.lookup("#registerButton");
        messageLabel = (Label) root.lookup("#messageLabel");
        registerClick = (Button) root.lookup("#registerClick");

        // Initialize the test database connection
        connection = DriverManager.getConnection("jdbc:sqlite:src/test/resources/test_users.db");
        createUsersTable();
    }

    private void createUsersTable() throws SQLException, SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, display_name TEXT, userID INTEGER)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        }
    }
    @BeforeAll
    static void setupSpec() throws Exception {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }

    @Test
    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/src/login.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testHandleLogin() {
        Platform.runLater(() -> {
            // Test successful login
            insertTestUser("testuser", "testpassword", "Test User", 1);
            usernameField.setText("testuser");
            passwordField.setText("testpassword");
            loginButton.fire();
            assertEquals("Welcome, Test User!", messageLabel.getText());

            // Test invalid login
            usernameField.setText("invaliduser");
            passwordField.setText("invalidpassword");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleRegister() {
        Platform.runLater(() -> {
            // Test successful registration
            usernameField.setText("newuser");
            passwordField.setText("newpassword");
            displayNameField.setText("New User");
            registerButton.fire();
            // Assert that the user is successfully registered in the database
            assertTrue(isUserRegistered("newuser"));

            // Test registration with existing username
            usernameField.setText("testuser");
            passwordField.setText("testpassword");
            displayNameField.setText("Test User");
            registerButton.fire();
            // Assert that the registration fails with an appropriate error message
            assertFalse(isUserRegistered("testuser"));
        });
    }

    @Test
    void testOnReturnClick() {
        Platform.runLater(() -> {
            registerClick.fire();
            // Assert that the scene is switched to the login-or-signup view
            assertEquals("login-or-signup.fxml", registerClick.getScene().getRoot().getId());
        });
    }

    private void insertTestUser(String username, String password, String displayName, int userID) {
        try {
            String sql = "INSERT INTO users (username, password, display_name, userID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, displayName);
                statement.setInt(4, userID);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isUserRegistered(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}