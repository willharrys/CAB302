import static org.junit.jupiter.api.Assertions.*;

import com.example.src.EntryController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.sql.*;

public class EntryControllerTest {

    private EntryController entryController;
    private Connection connection;

    @BeforeAll
    static void setupSpec() throws Exception {
        // Initialize the JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws SQLException {
        entryController = new EntryController();
        entryController.entriesList = new VBox(); // Mocking the entriesList VBox

        // Insert test data into the database
        insertTestData();
    }

    @Test
    public void testDisplayEntryIfExist() {
        // Assuming there is at least one entry in the database associated with user ID 1
        entryController.setCurrentUserId(1); // Set the current user ID to 1
        entryController.initialize();

        // Verify that an entry label is displayed in the UI
        assertFalse(entryController.entriesList.getChildren().isEmpty());
    }

    @Test
    public void testDisplayEntryWithNullValues() throws SQLException {
        // Insert test data with null values for feelingsText and emotionsText into the database
        insertTestDataWithNull();

        // Assuming there is at least one entry in the database associated with user ID 1
        entryController.setCurrentUserId(1); // Set the current user ID to 1
        entryController.initialize();

        // Verify that an entry label is displayed in the UI
        assertFalse(entryController.entriesList.getChildren().isEmpty());
    }

    // Helper method to insert test data into the database
    private void insertTestData() throws SQLException {
        String dbUrl = "jdbc:sqlite:src/main/root/users.db";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement()) {

            // Create the entries table if it doesn't exist
            statement.execute("CREATE TABLE IF NOT EXISTS entries (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "moodSlider INTEGER," +
                    "feelingsText TEXT," +
                    "emotionsText TEXT," +
                    "userID INTEGER)");

            // Insert test data associated with user ID 1
            statement.execute("INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID) " +
                    "VALUES (5, 'Feeling happy', 'Excited', 1)");
        }
    }

    // Helper method to insert test data with null values for feelingsText and emotionsText into the database
    private void insertTestDataWithNull() throws SQLException {
        String dbUrl = "jdbc:sqlite:src/main/root/users.db";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement()) {

            // Create the entries table if it doesn't exist
            statement.execute("CREATE TABLE IF NOT EXISTS entries (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "moodSlider INTEGER," +
                    "feelingsText TEXT," +
                    "emotionsText TEXT," +
                    "userID INTEGER)");

            // Insert test data with null values for feelingsText and emotionsText associated with user ID 2
            statement.execute("INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID) " +
                    "VALUES (5, NULL, NULL, 2)");
        }
    }
}
