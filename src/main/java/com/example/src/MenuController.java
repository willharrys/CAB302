package com.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;

public class MenuController {

    @FXML private Button homeMenu;
    @FXML private Button emotionMenu;
    @FXML private Button supportMenu;
    @FXML private Button entryMenu;
    @FXML private Button signOutMenu;
    @FXML private Label welcomeLabel;
    @FXML private Label quoteLabel;

    protected static int currentUserId;

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    public static int getCurrentUserId() {
        return currentUserId;

    }

    @FXML
    public void setWelcomeMessage(String username, int userID){
        welcomeLabel.setText("Welcome, " + username + "! Your user ID is " + userID + ". Enjoy your time here!");
        setQuote();
        setCurrentUserId(userID);
    }

    @FXML
    public void setQuote() {
        quoteLabel.setText(getRandomQuote());
    }

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

    @FXML
    protected void entryMenuClick()
            throws IOException {
        Stage stage = (Stage) entryMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("entries.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }

    @FXML
    protected void profileMenuClick()
            throws IOException {
        Stage stage = (Stage) entryMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }

    @FXML
    protected void signOutMenuClick()
            throws IOException {
        Stage stage = (Stage) signOutMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-or-signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }

    private final String[] MOTIVATIONAL_QUOTES = {
            "The only way to do great work is to love what you do. If you haven't found it yet, keep looking. Don't settle. As with all matters of the heart, you'll know when you find it. - Steve Jobs",
            "The only limit to our realization of tomorrow will be our doubts of today. - Franklin D. Roosevelt",
            "The best way to predict the future is to create it. - Peter Drucker",
            "The only thing standing between you and your goal is the story you keep telling yourself as to why you can't achieve it. - Jordan Belfort",
            "The only way to achieve the impossible is to believe it is possible. - Charles Kingsleigh",
            "The only way to get started is to quit talking and begin doing. - Walt Disney",
            "The only way to make sense out of change is to plunge into it, move with it, and join the dance. - Alan Watts",
            "The only way to do great work is to love what you do. - Steve Jobs",
            "The only way to discover the limits of the possible is to go beyond them into the impossible. - Arthur C. Clarke",
            "The only way to achieve the impossible is to believe it is possible. - Charles Kingsleigh",
            "The only way to get started is to quit talking and begin doing. - Walt Disney",
            "The only way to make sense out of change is to plunge into it, move with it, and join the dance. - Alan Watts",
            "The only way to do great work is to love what you do. - Steve Jobs",
            "The only way to discover the limits of the possible is to go beyond them into the impossible. - Arthur C. Clarke",
            "The only way to achieve the impossible is to believe it is possible. - Charles Kingsleigh",
            "The only way to get started is to quit talking and begin doing. - Walt Disney",
            "The only way to make sense out of change is to plunge into it, move with it, and join the dance. - Alan Watts",
            "The only way to do great work is to love what you do. - Steve Jobs",
            "The only way to discover the limits of the possible is to go beyond them into the impossible. - Arthur C. Clarke",
            "The only way to achieve the impossible is to believe it is possible. - Charles",
            "The only way to get started is to quit talking and begin doing. - Walt Disney",
    };

    private String getRandomQuote() {
        int randomIndex = (int) (Math.random() * MOTIVATIONAL_QUOTES.length);
        return MOTIVATIONAL_QUOTES[randomIndex];
    }
}
