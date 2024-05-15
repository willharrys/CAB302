package com.example.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import static com.example.src.DatabaseInitializer.INSERT_ENTRIES_SQL;

public class EmotionsController extends MenuController {
    @FXML
    public Slider moodSlider;
    @FXML
    public Button submitButton;
    @FXML
    public TextArea feelingsTextArea;
    @FXML
    public TextArea emotionsTextArea;
    @FXML
    private Connection connection;
    public EmotionsController() {
        // Initialize the database connection using the connection from DatabaseInitializer
        this.connection = DatabaseInitializer.getConnection();
        try {
            DatabaseInitializer.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onSubmitButtonClick(ActionEvent event) {
        if (moodSlider.getValue() == 0) {
            submitButton.setText("Please select a mood");
            return;
        }
        if (feelingsTextArea.getText().isEmpty()) {
            submitButton.setText("Please enter your feelings");
            return;
        }
        if (emotionsTextArea.getText().isEmpty()) {
            submitButton.setText("Please enter your emotions");
            return;
        }
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        DatabaseInitializer.debugLog("Preparing to submit emotions to the database");
        try (PreparedStatement statement = DatabaseInitializer.getConnection().prepareStatement(INSERT_ENTRIES_SQL)) {
            statement.setInt(1, (int) moodSlider.getValue());
            statement.setString(2, feelingsTextArea.getText());
            statement.setString(3, emotionsTextArea.getText());
            statement.setInt(4, getCurrentUserId());
            statement.setString(5, formattedDate);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                DatabaseInitializer.debugLog("Emotions submitted successfully");
                submitButton.setText("Thanks for submitting!");
            } else {
                DatabaseInitializer.debugLog("No rows affected. Potential error during submission.");
                submitButton.setText("Error submitting");
            }
            submitButton.setDisable(true);
            feelingsTextArea.clear();
            emotionsTextArea.clear();
        } catch (SQLException e) {
            DatabaseInitializer.debugLog("SQL Exception during submitting emotions: " + e.getMessage());
            submitButton.setText("Error on submitting please try again");
            e.printStackTrace();
        }
    }

}
