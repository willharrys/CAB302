package com.example.src;

import java.sql.*;

public class DatabaseInitializer {
    public static final String DB_URL = "jdbc:sqlite:src/main/root/users.db";
    public static final String TEST_DB_URL = "jdbc:sqlite:src/test/root/users.db";
    public static final String INSERT_USER_SQL = "INSERT INTO users (username, password, display_name) VALUES (?, ?, ?)";
    public static final String SELECT_USER_SQL = "SELECT display_name, userID FROM users WHERE username = ? AND password = ?";
    public static final String INSERT_ENTRIES_SQL = "INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID, created_at) VALUES (?, ?, ?, ?, ?)";
    public static final String ENTRIES_DISPLAY_SQL = "SELECT entryNo, moodSlider, feelingsText, emotionsText, created_at FROM entries WHERE userID = ? ORDER BY created_at DESC";
    public static Connection connection;

    private static final boolean DEBUG = true;

    static {
        try {
            debugLog("Attempting to open DB connection");
            connection = DriverManager.getConnection(isTestClass() ? TEST_DB_URL : DB_URL);
            debugLog("DB connection opened successfully");
        } catch (SQLException e) {
            debugLog("SQL Exception during DB connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Debug helper method to log messages
    static void debugLog(String message) {
        if (DEBUG) {
            System.out.println("DEBUG: " + message);
        }
    }

    public static void closeConnection() {
        try {
            debugLog("Attempting to close DB connection");
            if (connection != null && !connection.isClosed()) {
                connection.close();
                debugLog("DB connection closed successfully");
            }
        } catch (SQLException e) {
            debugLog("SQL Exception during DB connection close: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void printTableInfo() throws SQLException {
        String query = "SELECT name, sql FROM sqlite_master WHERE type='table'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String tableName = resultSet.getString("name");
                String tableStructure = resultSet.getString("sql");
                System.out.println("Table: " + tableName);
                System.out.println("Structure: " + tableStructure);
                System.out.println();
            }
        }
    }

    public static boolean isTestClass() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            System.out.println("Checking class: " + className); // [[2]]
            if (className.contains("test.")) {
                System.out.println("Test class detected: " + className); // [[3]]
                return true;
            }
        }
        System.out.println("Not a test class"); // [[4]]
        return false;
    }

    public static void createTables() throws SQLException {
        createUsersTable();
        createEntriesTable();
    }

    private static void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "userID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "display_name TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private static void createEntriesTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS entries (" +
                "entryNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "moodSlider INTEGER, " +
                "feelingsText TEXT, " +
                "emotionsText TEXT, " +
                "userID INTEGER, " +
                "created_at TEXT)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }
    public static Connection getConnection() {
        return connection;
    }

    public static void shutdown() {
        closeConnection();
    }
}
