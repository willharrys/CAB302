package com.example.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {

    //Defining the window title and size
    public static final String TITLE = "Journal Reflection: Java Edition";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 650;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-or-signup.fxml"));
        stage.getIcons().add(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrNCnyiMs2nC5fTCEX6y0IWV6yoznFqLl-1qYMEkbodQ&s"));
        Scene scene = new Scene(fxmlLoader.load(),WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}