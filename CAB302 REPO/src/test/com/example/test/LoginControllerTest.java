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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController loginController;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField displayNameField;
    private Button loginButton;
    private Label messageLabel;
    private Button registerClick;
    private static Connection connection;

    @BeforeAll
    static void setupSpec() throws Exception {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() throws Exception {
        // Initialize the test database connection
        connection = DriverManager.getConnection("jdbc:sqlite:src/test/root/users.db");

        // Create the users table
        String sql = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, display_name TEXT)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        }

        loginController = new LoginController();
        usernameField = loginController.usernameField;
        passwordField = loginController.passwordField;
        displayNameField = loginController.displayNameField;
        loginButton = loginController.loginButton;
        messageLabel = loginController.messageLabel;
        registerClick = loginController.registerClick;
    }

    @Test
    void testHandleLogin() {
        Platform.runLater(() -> {
            // Insert a test user
            insertTestUser("testuser", "testpassword", "Test User", 1);

            // Test successful login
            usernameField.setText("testuser");
            passwordField.setText("testpassword");
            loginButton.fire();
            assertEquals("Welcome, Test User!", messageLabel.getText());

        });
    }
    @Test
    void testHandleLoginWithIncorrectUsername() {
        Platform.runLater(() -> {
            // Insert a test user
            insertTestUser("testuser", "testpassword", "Test User", 1);

            // Test login with incorrect username but correct password
            usernameField.setText("incorrectuser");
            passwordField.setText("testpassword");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithNoUsernameAndNoPassword() {
        Platform.runLater(() -> {
            // Test login with no username and no password
            usernameField.setText("");
            passwordField.setText("");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithUsernameButNullPassword() {
        Platform.runLater(() -> {
            // Insert a test user
            insertTestUser("testuser", "testpassword", "Test User", 1);

            // Test login with username but null password
            usernameField.setText("testuser");
            passwordField.setText(null);
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithNullUsernameButCorrectPassword() {
        Platform.runLater(() -> {
            // Insert a test user
            insertTestUser("testuser", "testpassword", "Test User", 1);

            // Test login with null username but correct password
            usernameField.setText(null);
            passwordField.setText("testpassword");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithMultipleUsers() {
        Platform.runLater(() -> {
            // Insert multiple test users
            insertTestUser("user1", "password1", "User 1", 1);
            insertTestUser("user2", "password2", "User 2", 2);
            insertTestUser("user3", "password3", "User 3", 3);

            // Test successful login for each user
            usernameField.setText("user1");
            passwordField.setText("password1");
            loginButton.fire();
            assertEquals("Welcome, User 1!", messageLabel.getText());

            usernameField.setText("user2");
            passwordField.setText("password2");
            loginButton.fire();
            assertEquals("Welcome, User 2!", messageLabel.getText());

            usernameField.setText("user3");
            passwordField.setText("password3");
            loginButton.fire();
            assertEquals("Welcome, User 3!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithIncorrectPassword() {
        Platform.runLater(() -> {
            // Insert a test user
            insertTestUser("testuser", "testpassword", "Test User", 1);

            // Test login with correct username but incorrect password
            usernameField.setText("testuser");
            passwordField.setText("wrongpassword");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithEmptyUsername() {
        Platform.runLater(() -> {
            // Test login with empty username
            usernameField.setText("");
            passwordField.setText("testpassword");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithEmptyPassword() {
        Platform.runLater(() -> {
            // Test login with empty password
            usernameField.setText("testuser");
            passwordField.setText("");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithNonexistentUser() {
        Platform.runLater(() -> {
            // Test login with a nonexistent user
            usernameField.setText("nonexistentuser");
            passwordField.setText("password");
            loginButton.fire();
            assertEquals("Invalid username or password!", messageLabel.getText());
        });
    }

    @Test
    void testHandleLoginWithLeadingAndTrailingSpaces() {
        Platform.runLater(() -> {
            // Insert a test user
            insertTestUser("testuser", "testpassword", "Test User", 1);

            // Test login with leading and trailing spaces in username and password
            usernameField.setText("  testuser  ");
            passwordField.setText("  testpassword  ");
            loginButton.fire();
            assertEquals("Welcome, Test User!", messageLabel.getText());
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
}
