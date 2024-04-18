package com.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;

public class MenuController {

    @FXML private Button homeMenu;
    @FXML private Button emotionMenu;
    @FXML private Button supportMenu;
    @FXML
    protected void homeMenuClick()
            throws IOException {
        Stage stage = (Stage) homeMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homepage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }
    @FXML
    protected void emotionMenuClick()
            throws IOException {
        Stage stage = (Stage) emotionMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("emotions.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }
    @FXML
    protected void supportMenuClick()
            throws IOException {
        Stage stage = (Stage) supportMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("support.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }


}
