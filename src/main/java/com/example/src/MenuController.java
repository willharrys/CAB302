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
import java.util.Random;

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

    // Array containing the quotes
    private String[] quotes = {
            "'The happiness of your life depends upon the quality of your thoughts.' - Marcus Aurelius",
            "'The best revenge is to be unlike him who performed the injury.' - Marcus Aurelius",
            "'The soul becomes dyed with the color of its thoughts.' - Marcus Aurelius",
            "'It is not death that a man should fear, but he should fear never beginning to live.' - Marcus Aurelius",
            "'Don’t seek for everything to happen as you wish it would, but rather wish that everything happens as it actually will—then your life will flow well.' - Epictetus",
            "'Man is not worried by real problems so much as by his imagined anxieties about real problems.' - Epictetus",
            "'He suffers more than necessary, who suffers before it is necessary.' - Seneca",
            "'Wealth consists not in having great possessions, but in having few wants.' - Epictetus",
            "'Waste no more time arguing about what a good man should be. Be one.' - Marcus Aurelius",
            "'We are more often frightened than hurt, and we suffer more from imagination than from reality.' - Seneca"
    };

    @FXML
    public void setWelcomeMessage(String username, int userID){
        welcomeLabel.setText("Welcome username: " + username + " userID: " + userID);
        setCurrentUserId(userID);
    }

    @FXML
    public void makeQuote() {
        // Generate a random index
        Random random = new Random();
        int index = random.nextInt(quotes.length);

        // Set the randomly selected quote to the label
        quoteLabel.setText(quotes[index]);
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


}
