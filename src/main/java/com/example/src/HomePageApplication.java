package com.example.src;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageApplication {

    public static final String TITLE = "Journal App";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 650;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homepage-view.fxml"));
        stage.getIcons().add(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrNCnyiMs2nC5fTCEX6y0IWV6yoznFqLl-1qYMEkbodQ&s"));
        Scene scene = new Scene(fxmlLoader.load(),WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
}
