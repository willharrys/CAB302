package com.example.src;

import java.sql.*;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:src/main/root/users.db";
    public static final String INSERT_ENTRIES_SQL = "INSERT INTO entries (moodSlider, feelingsText, emotionsText, userID, entryNo) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_USER_SQL = "INSERT INTO users (username, password, display_name) VALUES (?, ?, ?)";
    public static final String SELECT_USER_SQL = "SELECT display_name, userID FROM users WHERE username = ? AND password = ?";
    public static final String SELECT_ENTRIES_SQL = "SELECT entryNo, moodSlider, feelingsText, emotionsText FROM entries WHERE userID = ?";
    public static final String UPDATE_ENTRY_SQL = "UPDATE entries SET moodSlider = ?, feelingsText = ?, emotionsText = ? WHERE entryNo = ? AND userID = ?";
    public static final String DELETE_ENTRY_SQL = "DELETE FROM entries WHERE entryNo = ? AND userID = ?";

    private static Connection connection;

    public DatabaseInitializer() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        createUsersTable();
        createEntriesTable();
    }

    private void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "userID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "display_name TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private void createEntriesTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS entries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "entryNo INTEGER, " +
                "moodSlider INTEGER, " +
                "feelingsText TEXT, " +
                "emotionsText TEXT, " +
                "userID INTEGER, " +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
