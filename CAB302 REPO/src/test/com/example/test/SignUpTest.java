package com.example.test;

import com.example.src.LoginOrSignup;
import com.example.src.SignUp;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SignUpTest {

    private SignUp signUp;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField displayNameField;
    private Button signupButton;
    private Label messageLabel;
    private static Connection connection;


    @BeforeAll
    static void setupSpec() throws Exception {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }
    @BeforeEach
    void setUp() throws SQLException {
        // Initialize the test database connection
        connection = DriverManager.getConnection("jdbc:sqlite:src/test/root/users.db");

        // Create the users table
        String sql = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, display_name TEXT)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        }

        signUp = new SignUp();
        usernameField = signUp.usernameField;
        passwordField = signUp.passwordField;
        displayNameField = signUp.displayNameField;
        signupButton = signUp.registerButton;
        messageLabel = signUp.messageLabel;
    }

    @Test
    void testSuccessfulSignup() throws Exception {
        Platform.runLater(() -> {
            // Test successful signup
            usernameField.setText("newuser");
            passwordField.setText("newpassword");
            displayNameField.setText("New User");
            signupButton.fire();
            // Assert that the user is successfully registered in the database
            assertTrue(isUserRegistered("newuser"));
        });
    }

    @Test
    void testSignupWithExistingUsername() {
        Platform.runLater(() -> {
            // Test signup with existing username
            usernameField.setText("existinguser");
            passwordField.setText("password");
            displayNameField.setText("Existing User");
            signupButton.fire();
            // Assert that the signup fails with an appropriate error message
            assertEquals("Registration failed! Username already taken.", messageLabel.getText());
        });
    }

    @Test
    void testSignupWithNullUsername() {
        Platform.runLater(() -> {
            // Test signup with null username
            usernameField.setText(null);
            passwordField.setText("password");
            displayNameField.setText("User");
            signupButton.fire();
            // Assert that the signup fails with an appropriate error message
            assertEquals("Registration failed! Username cannot be null.", messageLabel.getText());
            System.out.println("testSignupWithNullUsername: PASSED");
        });
    }
    @Test
    void testSignupWithNullPassword() {
        Platform.runLater(() -> {
            // Test signup with null password
            usernameField.setText("user");
            passwordField.setText(null);
            displayNameField.setText("User");
            signupButton.fire();
            // Assert that the signup fails with an appropriate error message
            assertEquals("Registration failed! Password cannot be null.", messageLabel.getText());
            System.out.println("testSignupWithNullPassword: PASSED");
        });
    }
    @Test
    void testSignupWithNullDisplayName() {
        Platform.runLater(() -> {
            // Test signup with null display name
            usernameField.setText("user");
            passwordField.setText("password");
            displayNameField.setText(null);
            signupButton.fire();
            // Assert that the signup fails with an appropriate error message
            assertEquals("Registration failed! Display name cannot be null.", messageLabel.getText());
            System.out.println("testSignupWithNullDisplayName: PASSED");
        });
    }
    @Test
    void testSignupWithIncorrectUsernameFormat() {
        Platform.runLater(() -> {
            // Test signup with incorrect username format
            usernameField.setText("invalid-username");
            passwordField.setText("password");
            displayNameField.setText("User");
            signupButton.fire();
            // Assert that the signup fails with an appropriate error message
            assertEquals("Registration failed! Username format is incorrect.", messageLabel.getText());
            System.out.println("testSignupWithIncorrectUsernameFormat: PASSED");
        });
    }
    @Test
    void testSignupWithIncorrectPasswordFormat() {
        Platform.runLater(() -> {
            // Test signup with incorrect password format
            usernameField.setText("user");
            passwordField.setText("weak");
            displayNameField.setText("User");
            signupButton.fire();
            // Assert that the signup fails with an appropriate error message
            assertEquals("Registration failed! Password format is incorrect.", messageLabel.getText());
            System.out.println("testSignupWithIncorrectPasswordFormat: PASSED");
        });
    }
    @Test
    void testSignupWithCorrectInformation() {
        Platform.runLater(() -> {
            // Test signup with correct information
            usernameField.setText("newuser");
            passwordField.setText("password123");
            displayNameField.setText("New User");
            signupButton.fire();
            // Assert that the signup is successful
            assertEquals("Registration successful!", messageLabel.getText());
            System.out.println("testSignupWithCorrectInformation: PASSED");
        });
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
