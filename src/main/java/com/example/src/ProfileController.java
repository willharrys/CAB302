package com.example.src;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
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
        String updateSQL = "UPDATE users SET username = ?, password = ?, display_name = ? WHERE userID = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(updateSQL)) {

            statement.setString(1, usernameField.getText());
            statement.setString(2, passwordField.getText());
            statement.setString(3, nameField.getText());
            statement.setInt(4, getCurrentUserId());

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

    private void deleteEntries() {
        String deleteSQL = "DELETE FROM entries WHERE userID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, getCurrentUserId());

            int affectedRows = pstmt.executeUpdate();
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
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, getCurrentUserId());

            int affectedRows = pstmt.executeUpdate();
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

    private void updateMessageLabel(String message, boolean isSuccess) {
        messageLabel.setText(message);
        if (isSuccess) {
            messageLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        } else {
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
    }
}
