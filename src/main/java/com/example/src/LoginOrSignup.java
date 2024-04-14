package com.example.src;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class LoginOrSignup {
    public class StartController {


        @FXML
        private Button loginButton;

        @FXML
        private Button signUpButton;

        public static final String TITLE = "Login / Sign Up";
        public static final int WIDTH = 800;
        public static final int HEIGHT = 650;

        @FXML
        private void handleLogin(Stage stage) throws Exception {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),WIDTH, HEIGHT);
            stage.setTitle(TITLE);
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setScene(scene);
            stage.show();
        }

        @FXML
        private void handleSignUp(Stage stage) throws Exception {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign-up.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),WIDTH, HEIGHT);
            stage.setTitle(TITLE);
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setScene(scene);
            stage.show();
        }
    }
}
