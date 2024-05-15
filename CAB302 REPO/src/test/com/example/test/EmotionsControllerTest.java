package com.example.test;

import com.example.src.EmotionsController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmotionsControllerTest {
    private EmotionsController emotionsController;
    private TextField moodSlider;
    private TextField feelingsTextArea;
    private TextField emotionsTextArea;
    private Button submitButton;
    private Button Resubmit;
    private Button Submit2;
    private Button Resubmit2;
    private Button Submit;
    private Connection connection;

    void setUp() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/src/emotions.fxml"));
        Parent root = loader.load();
        emotionsController = loader.getController();
        moodSlider = (TextField) root.lookup("#moodSlider");
        feelingsTextArea = (TextField) root.lookup("#feelingsTextArea");
        emotionsTextArea = (TextField) root.lookup("#emotionsTextArea");
        submitButton = (Button) root.lookup("#submitButton");
        Resubmit = (Button) root.lookup("#Resubmit");
        Submit2 = (Button) root.lookup("#Submit2");
        Resubmit2 = (Button) root.lookup("#Resubmit2");
        Submit = (Button) root.lookup("#Submit");

        // Initialize the test database connection
        connection = DriverManager.getConnection("jdbc:sqlite:src/test/resources/test_users.db");
        createEmotionTable();
    }

    private void createEmotionTable() throws SQLException, SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS entries (moodSlider TEXT, feelingsText TEXT, emotionsText TEXT, userID INTEGER)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        }
    }

    @BeforeAll
    static void setupSpec() throws Exception {
        Platform.startup(() -> {
        });
    }

    @Test
    void testCreateEmotionTable() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            assertTrue(checkEmotionTable());
        });
    }

    @Test
    void testFeelingsTextArea() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.feelingsTextArea.setText("I feel happy");
            assertEquals("I feel happy", emotionsController.feelingsTextArea.getText());
        });
    }

    @Test
    void testEmotionsTextArea() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.emotionsTextArea.setText("I am happy");
            assertEquals("I am happy", emotionsController.emotionsTextArea.getText());
        });
    }

    @Test
    void testMooodSlider() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.moodSlider.setValue(1);
            assertEquals(1, emotionsController.moodSlider.getValue());
        });
    }

    @Test
    void testHandleMoodSliderException() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.submitButton.fire();
            assertEquals("Please select a mood", emotionsController.submitButton.getText());
        });
    }

    @Test
    void testHandleFeelingsTextAreaException() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.moodSlider.setValue(1);
            emotionsController.submitButton.fire();
            assertEquals("Please enter your feelings", emotionsController.submitButton.getText());
        });
    }

    @Test
    void testHandleEmotionsTextAreaException() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.moodSlider.setValue(1);
            emotionsController.feelingsTextArea.setText("I feel happy");
            emotionsController.submitButton.fire();
            assertEquals("Please enter your emotions", emotionsController.submitButton.getText());
        });
    }

    @Test
    void testHandleSubmitButtonClick() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.moodSlider.setValue(1);
            emotionsController.feelingsTextArea.setText("I feel happy");
            emotionsController.emotionsTextArea.setText("I am happy");
            emotionsController.submitButton.fire();
            assertEquals("Thanks for submitting!", emotionsController.submitButton.getText());
        });
    }

    @Test
    void testInsertEmotion() {
        Platform.runLater(() -> {
            emotionsController = new EmotionsController();
            emotionsController.moodSlider.setValue(1);
            emotionsController.feelingsTextArea.setText("I feel happy");
            emotionsController.emotionsTextArea.setText("I am happy");
            emotionsController.submitButton.fire();
            assertTrue(insertTestEmotion("1", "I feel happy", "I am happy", 1));
        });
    }

    private boolean insertTestEmotion(String moodSlider, String feelingsText, String emotionsText, int userID) {
        String sql = "INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moodSlider);
            statement.setString(2, feelingsText);
            statement.setString(3, emotionsText);
            statement.setInt(4, userID);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkEmotionTable() {
        String sql = "SELECT * FROM entries";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
