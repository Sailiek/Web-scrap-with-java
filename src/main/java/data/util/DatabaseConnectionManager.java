package data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/webscrapdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnectionManager() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                connection.setAutoCommit(true);
                System.out.println("Database connected successfully!");
            } catch (SQLException e) {
                throw new RuntimeException("Failed to connect to the database", e);
            }
        }
        return connection;
    }

}
