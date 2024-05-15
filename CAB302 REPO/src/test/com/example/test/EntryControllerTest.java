package test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.src.EntryController;
import com.example.src.DatabaseInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class EntryControllerTest {

    // Private instance of EntryController for testing
    private EntryController entryController;

    @BeforeAll
    static void setupSpec() throws Exception {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});



        // If the connection is already open, close it to avoid leaks.
        if (DatabaseInitializer.connection != null && !DatabaseInitializer.connection.isClosed()) {
            DatabaseInitializer.connection.close();
        }

        // Connect to the in-memory database.
        DatabaseInitializer.connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        // With the connection established, create the necessary tables.
        DatabaseInitializer.createTables();
        DatabaseInitializer.printTableInfo();
        // Populate the tables with any necessary data for testing.
        insertTestData(DatabaseInitializer.connection);
    }

    @BeforeEach
    public void setUp() throws SQLException {
        // Initialize EntryController and its dependencies
        entryController = new EntryController();
        entryController.entriesList = new VBox(); // Mocking the entriesList VBox

        // Insert test data into the test database
        insertTestData(DatabaseInitializer.connection);
        insertTestDataWithNull(DatabaseInitializer.connection); // Use the connection from DatabaseInitializer
    }

    @Test
    public void testDisplayEntryIfExist() throws SQLException {
        // Assuming there is at least one entry in the database associated with user ID 1
        entryController.setCurrentUserId(1); // Set the current user ID to 1
        entryController.initialize();

        // Verify that an entry label is displayed in the UI
        assertFalse(entryController.entriesList.getChildren().isEmpty());
    }

    @Test
    public void testDisplayEntryWithNullValues() throws SQLException {
        // Get the test database connection
        Connection connection = DatabaseInitializer.getConnection();

        // Insert test data with null values for feelingsText and emotionsText into the database
        insertTestDataWithNull(connection);

        // Assuming there is at least one entry in the database associated with user ID 1
        entryController.setCurrentUserId(1); // Set the current user ID to 1
        entryController.initialize();

        // Verify that an entry label is displayed in the UI
        assertFalse(entryController.entriesList.getChildren().isEmpty());
    }

    // Helper method to insert test data into the database
    private static void insertTestData(Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID, created_at) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, 5);
            stmt.setString(2, "Feeling happy");
            stmt.setString(3, "Excited");
            stmt.setInt(4, 1);
            stmt.setString(5, LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
            stmt.executeUpdate();
        }
    }

    // Helper method to insert test data with null values into the database
    private void insertTestDataWithNull(Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID, created_at) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, 5);
            stmt.setNull(2, Types.VARCHAR);
            stmt.setNull(3, Types.VARCHAR);
            stmt.setInt(4, 2);
            stmt.setString(5, LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
            stmt.executeUpdate();
        }
    }
}

