package com.example.src;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class StartController {

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSignUp(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sign-up.fxml"));
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
