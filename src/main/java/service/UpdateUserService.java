package service;

import data.model.User;
import data.model.UserTypes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import data.util.DatabaseConnectionManager;
import java.sql.SQLException;

public class UpdateUserService {

    // Method to update user information in the database
    public void updateUser(User user) {
        // Establish a connection to the database (you may use a connection pool or ORM)
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get database connection (this is just an example, use your actual DB connection setup)
            connection = DatabaseConnectionManager.getConnection();

            // SQL update query
            String updateQuery = "UPDATE users SET username = ?, nom = ?, prenom = ?, email = ?, password = ?, field_of_work = ?, age = ?, user_type = ?, month_of_birth = ?, day_of_birth = ?, year_of_birth = ? WHERE username = ?";

            // Prepare statement
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getPrenom());
            preparedStatement.setString(4, user.getUserEmail());
            preparedStatement.setString(5, user.getUserPassword());
            preparedStatement.setString(6, user.getFieldOfWork());
            preparedStatement.setInt(7, user.getAge());
            preparedStatement.setString(8, user.getUserType().toString());  // Assuming UserTypes is an enum
            preparedStatement.setInt(9, user.getMonthOfBirth());
            preparedStatement.setInt(10, user.getDayOfBirth());
            preparedStatement.setInt(11, user.getYearOfBirth());
            preparedStatement.setString(12, user.getUsername());  // Where clause uses username to find the user

            // Execute update
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if update was successful
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user found with the specified username.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating user information.");
        } finally {
            // Clean up resources (close the connection and statement)
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
