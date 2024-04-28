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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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

    @BeforeEach
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

            // Test registration with existing username
            usernameField.setText("testuser");
            passwordField.setText("testpassword");
            displayNameField.setText("Test User");
            registerButton.fire();
            // Assert that the registration fails with an appropriate error message
        });
    }

    @Test
    void testOnReturnClick() {
        Platform.runLater(() -> {
            registerClick.fire();
            // Assert that the scene is switched to the login-or-signup view
        });
    }

    // Add more test methods for other scenarios and edge cases
}
