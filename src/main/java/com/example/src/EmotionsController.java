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


public class EmotionsController extends MenuController {

    public Button Resubmit;
    @FXML
    public Slider moodSlider;
    public Button Submit2;
    public Button Resubmit2;
    @FXML
    public Button submitButton;
    @FXML
    private TextArea Clear;

    @FXML
    private TextArea Clear2;
    @FXML
    public TextArea feelingsTextArea;
    @FXML
    public TextArea emotionsTextArea;

    private static final String DB_URL = "jdbc:sqlite:src/main/root/users.db";
    private static final String INSERT_SQL = "INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID) VALUES (?, ?, ?, ?)";
    @FXML
    private Button Submit;


    @FXML
    protected void onSubmitButtonClick(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {

            statement.setInt(1, (int) moodSlider.getValue());
            statement.setString(2, feelingsTextArea.getText());
            statement.setString(3, emotionsTextArea.getText());
            statement.setInt(4, getCurrentUserId());
            statement.executeUpdate();
            submitButton.setText("Submit!");
            feelingsTextArea.clear();
            emotionsTextArea.clear();
        } catch (SQLException e) {
            e.printStackTrace();
            submitButton.setText("Submission Failed!");
        }
    }

/*
   @FXML
    protected void ResubmitButtonClick(){
        Submit.setText("Submit!");
    }

    @FXML
    public void onSubmitButtonClick(ActionEvent event) {
        Submit2.setText("Thanks for submitting!");
        Clear2.clear();
    }


    @FXML
    public void onResubmitButtonClick(ActionEvent event) {
        Submit2.setText("Submit!");
    }

 */
}
