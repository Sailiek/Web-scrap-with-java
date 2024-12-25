package service;

import data.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManagementService {
    public boolean updateUser(String currentUsername, String newUsername, String email, String password) {
        String query = "UPDATE users SET username = ?, userEmail = ?, userPassword = ? WHERE username = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the new values
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            // Specify the user to update (current username)
            preparedStatement.setString(4, currentUsername);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            System.err.println("Error updating user info: " + e.getMessage());
            return false; // Return false if there was an error
        }
    }
}
