package com.example.src;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EntryController extends MenuController {
    @FXML
    public VBox entriesList;

    public void initialize() {
        displayEntries();
    }

    private void displayEntries() {
        String dbUrl = "jdbc:sqlite:src/main/root/users.db";
        String query = "SELECT entryNo, moodSlider, feelingsText, emotionsText, created_at FROM entries WHERE userID = ? ORDER BY created_at DESC";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, getCurrentUserId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int entryNo = resultSet.getInt("entryNo");
                int mood = resultSet.getInt("moodSlider");
                String feelings = resultSet.getString("feelingsText");
                String emotions = resultSet.getString("emotionsText");
                String createdAt = resultSet.getString("created_at");

                // Parse the date string in the "ddmmyyyy" format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                LocalDate localDate = LocalDate.parse(createdAt, formatter);

                // Format the date to "dd/MM/yyyy"
                String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Label entryLabel = new Label("Mood: " + mood + "\nFeelings: " + feelings + "\nEmotions: " + emotions + "\nDate: " + formattedDate);
                entryLabel.setWrapText(true);
                entryLabel.setMaxWidth(600);
                entryLabel.setStyle("-fx-padding: 10; -fx-border-width: 2; -fx-border-insets: 5; -fx-font-size:18;");

                Button editButton = new Button("Edit");
                editButton.setOnAction(e -> editEntry(entryNo));

                Button deleteButton = new Button("Delete");
                HBox entryBox = new HBox(10, entryLabel, editButton, deleteButton);
                deleteButton.setOnAction(e -> deleteEntry(entryNo, entryBox));

                entriesList.getChildren().add(entryBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void editEntry(int entryNo) {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Entry");

        ButtonType saveButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField moodField = new TextField();
        moodField.setPromptText("Mood");
        TextArea feelingsArea = new TextArea();
        feelingsArea.setPromptText("Feelings");
        TextArea emotionsArea = new TextArea();
        emotionsArea.setPromptText("Emotions");

        grid.add(new Label("Mood (1-10):"), 0, 0);
        grid.add(moodField, 1, 0);
        grid.add(new Label("Feelings:"), 0, 1);
        grid.add(feelingsArea, 1, 1);
        grid.add(new Label("Emotions:"), 0, 2);
        grid.add(emotionsArea, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(moodField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String moodText = moodField.getText();
                if (!moodText.isEmpty()) {
                    try {
                        int mood = Integer.parseInt(moodText);
                        if (mood < 1 || mood > 10) {
                            showInvalidMoodAlert();
                            return null;
                        }
                    } catch (NumberFormatException e) {
                        showInvalidMoodAlert();
                        return null;
                    }
                }

                Map<String, String> result = new HashMap<>();
                result.put("mood", moodText);
                result.put("feelings", feelingsArea.getText());
                result.put("emotions", emotionsArea.getText());
                return result;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();

        result.ifPresent(entryData -> {
            String updateQuery = "UPDATE entries SET ";
            List<String> updates = new ArrayList<>();

            String mood = entryData.get("mood");
            if (!mood.isEmpty()) {
                updates.add("moodSlider = " + Integer.parseInt(mood));
            }

            String feelings = entryData.get("feelings");
            if (!feelings.isEmpty()) {
                updates.add("feelingsText = '" + feelings.replace("'", "''") + "'");
            }

            String emotions = entryData.get("emotions");
            if (!emotions.isEmpty()) {
                updates.add("emotionsText = '" + emotions.replace("'", "''") + "'");
            }

            if (!updates.isEmpty()) {
                updateQuery += String.join(", ", updates) + " WHERE entryNo = ?";
                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/root/users.db");
                     PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                    statement.setInt(1, entryNo);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showInvalidMoodAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Mood Value");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid mood value between 1 and 10.");
        alert.showAndWait();
    }

    private void deleteEntry(int entryNo, HBox entryBox) {
        String dbUrl = "jdbc:sqlite:src/main/root/users.db";
        String query = "DELETE FROM entries WHERE entryNo = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, entryNo);
            statement.executeUpdate();
            entriesList.getChildren().remove(entryBox);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}