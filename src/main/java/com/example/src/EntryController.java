package com.example.src;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.sql.*;

public class EntryController extends MenuController{
    @FXML
    public VBox entriesList;

    public void initialize() {
        displayEntries();
    }

    private void displayEntries() {
        String dbUrl = "jdbc:sqlite:src/main/root/users.db";
        String query = "SELECT moodSlider, feelingsText, emotionsText FROM entries WHERE userID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, getCurrentUserId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int mood = resultSet.getInt("moodSlider");
                String feelings = resultSet.getString("feelingsText");
                String emotions = resultSet.getString("emotionsText");


                Label entryLabel = new Label("Mood: " + mood + "\nFeelings: " + feelings + "\nEmotions: " + emotions);
                entryLabel.setWrapText(true);
                entryLabel.setMaxWidth(600);
                entryLabel.setStyle("-fx-padding: 10; -fx-border-width: 2;" +
                        "-fx-border-insets: 5; -fx-font-size:18;");
                entriesList.getChildren().add(entryLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
