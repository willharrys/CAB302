package com.example.src;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ProfileController extends MenuController{

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private Label messageLabel;

    private static final String DB_URL = "jdbc:sqlite:src/main/root/users.db";

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @FXML
    private void handleUpdateAction() {
        updateProfile();
        clearFields();
    }

    @FXML
    private void handleResetEntriesAction() {
        deleteEntries();
        clearFields();
    }

    @FXML
    private void handleDeleteAction() {
        deleteAccount();
        clearFields();
    }

    private void updateProfile() {
        // Constructing the SQL query dynamically based on input
        StringBuilder updateSQL = new StringBuilder("UPDATE users SET ");
        int count = 0;

        if (!usernameField.getText().isEmpty()) {
            String newUsername = usernameField.getText();
            if (isUsernameExists(newUsername)) {
                updateMessageLabel("Username already exists. Please choose a different username.", false);
                return;
            }
            updateSQL.append("username = ?");
            count++;
        }
        if (!passwordField.getText().isEmpty()) {
            if (count > 0) updateSQL.append(", ");
            updateSQL.append("password = ?");
            count++;
        }
        if (!nameField.getText().isEmpty()) {
            if (count > 0) updateSQL.append(", ");
            updateSQL.append("display_name = ?");
            count++;
        }

        if (count == 0) {
            updateMessageLabel("No information entered to update.", false);
            return;
        }

        updateSQL.append(" WHERE userID = ?");

        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(updateSQL.toString())) {

            int index = 1;
            if (!usernameField.getText().isEmpty()) {
                statement.setString(index++, usernameField.getText());
            }
            if (!passwordField.getText().isEmpty()) {
                statement.setString(index++, passwordField.getText());
            }
            if (!nameField.getText().isEmpty()) {
                statement.setString(index++, nameField.getText());
            }

            statement.setInt(index, getCurrentUserId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                updateMessageLabel("Profile updated successfully!", true);
            } else {
                updateMessageLabel("No changes made to the profile.", false);
            }
        } catch (SQLException e) {
            updateMessageLabel("Error updating profile: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    private boolean isUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void deleteEntries() {
        String deleteSQL = "DELETE FROM entries WHERE userID = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(deleteSQL)) {

            statement.setInt(1, getCurrentUserId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                updateMessageLabel("All entries deleted successfully!", true);
            } else {
                updateMessageLabel("No entries found to delete.", false);
            }
        } catch (SQLException e) {
            updateMessageLabel("Error deleting entries: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    private void deleteAccount() {
        String deleteSQL = "DELETE FROM users WHERE userID = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(deleteSQL)) {

            statement.setInt(1, getCurrentUserId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                updateMessageLabel("Account deleted successfully!", true);
            } else {
                updateMessageLabel("Error deleting account.", false);
            }
        } catch (SQLException e) {
            updateMessageLabel("Error deleting profile: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        nameField.clear();
    }
    // This makes if it is successful green if it is not successful it makes the text red
    private void updateMessageLabel(String message, boolean isSuccess) {
        messageLabel.setText(message);
        if (isSuccess) {
            messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        } else {
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
    }

    @FXML
    private void handleExportEntriesAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Entries.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            exportEntries(file);
        }
    }

    private void exportEntries(File file) {
        String query = "SELECT moodSlider, feelingsText, emotionsText FROM entries WHERE userID = ?";

        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, getCurrentUserId());
            ResultSet resultset = statement.executeQuery();
            StringBuilder sb = new StringBuilder();

            while (resultset.next()) {
                sb.append("Mood: ").append(resultset.getInt("moodSlider")).append("\n");
                sb.append("Feelings: ").append(resultset.getString("feelingsText")).append("\n");
                sb.append("Emotions: ").append(resultset.getString("emotionsText")).append("\n\n");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(sb.toString());
                updateMessageLabel("Entries exported successfully!", true);
            } catch (IOException e) {
                updateMessageLabel("Failed to write to file: " + e.getMessage(), false);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            updateMessageLabel("Error fetching entries: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

}