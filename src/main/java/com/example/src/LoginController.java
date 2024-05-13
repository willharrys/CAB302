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
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        connection = databaseInitializer.getConnection();
    }
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer();
            try (PreparedStatement statement = databaseInitializer.getConnection().prepareStatement(DatabaseInitializer.SELECT_USER_SQL)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String displayName = resultSet.getString("display_name");
                    int userID = resultSet.getInt("userID");

                    // Load the homepage view and pass the username and userID to the MenuController
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
            databaseInitializer.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Failed to load user interface.");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error connecting to the database.");
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
