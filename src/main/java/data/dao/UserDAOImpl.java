package data.dao;

import data.model.User;
import data.model.UserTypes;
import data.util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void saveUser(User user) {
        String query = "INSERT INTO users (nom, prenom, userEmail, username, userPassword, " +
                "fieldOfWork, age, userType, monthOfBirth, dayOfBirth, yearOfBirth) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Debug information
            System.out.println("Attempting to save user with username: " + user.getUsername());

            // Set values with validation
            stmt.setString(1, validateString(user.getNom(), "nom"));
            stmt.setString(2, validateString(user.getPrenom(), "prenom"));
            stmt.setString(3, user.getUserEmail());
            stmt.setString(4, validateString(user.getUsername(), "username"));
            stmt.setString(5, validateString(user.getUserPassword(), "password"));
            stmt.setString(6, user.getFieldOfWork());
            stmt.setInt(7, user.getAge());
            stmt.setString(8, user.getUserType().toString());
            stmt.setInt(9, user.getMonthOfBirth());
            stmt.setInt(10, user.getDayOfBirth());
            stmt.setInt(11, user.getYearOfBirth());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Successfully saved user: " + user.getUsername() + " (Rows affected: " + rowsAffected + ")");

        } catch (SQLException e) {
            String errorMessage = String.format("Error saving user '%s': %s (SQL State: %s, Error Code: %d)",
                    user.getUsername(), e.getMessage(), e.getSQLState(), e.getErrorCode());
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error retrieving user by username: " + username;
            System.err.println(errorMessage + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE userEmail = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error retrieving user by email: " + email;
            System.err.println(errorMessage + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all users: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve users", e);
        }
        return users;
    }

    @Override
    public List<User> getUsersByType(UserTypes userType) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userType = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, userType.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error retrieving users by type: " + userType;
            System.err.println(errorMessage + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
        return users;
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE users SET nom = ?, prenom = ?, userEmail = ?, userPassword = ?, " +
                "fieldOfWork = ?, age = ?, userType = ?, monthOfBirth = ?, dayOfBirth = ?, " +
                "yearOfBirth = ? WHERE username = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, validateString(user.getNom(), "nom"));
            stmt.setString(2, validateString(user.getPrenom(), "prenom"));
            stmt.setString(3, user.getUserEmail());
            stmt.setString(4, validateString(user.getUserPassword(), "password"));
            stmt.setString(5, user.getFieldOfWork());
            stmt.setInt(6, user.getAge());
            stmt.setString(7, user.getUserType().toString());
            stmt.setInt(8, user.getMonthOfBirth());
            stmt.setInt(9, user.getDayOfBirth());
            stmt.setInt(10, user.getYearOfBirth());
            stmt.setString(11, validateString(user.getUsername(), "username"));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("User not found: " + user.getUsername());
            }
            System.out.println("Successfully updated user: " + user.getUsername());

        } catch (SQLException e) {
            String errorMessage = String.format("Error updating user '%s': %s", 
                    user.getUsername(), e.getMessage());
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public void deleteUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new RuntimeException("User not found: " + username);
            }
            System.out.println("Successfully deleted user: " + username);

        } catch (SQLException e) {
            String errorMessage = "Error deleting user: " + username;
            System.err.println(errorMessage + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT userPassword FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("userPassword");
                    return storedPassword.equals(password); // In a real application, use proper password hashing
                }
            }
        } catch (SQLException e) {
            String errorMessage = "Error authenticating user: " + username;
            System.err.println(errorMessage + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
        return false;
    }

    private String validateString(String value, String fieldName) throws SQLException {
        if (value == null || value.trim().isEmpty()) {
            throw new SQLException("Field '" + fieldName + "' cannot be null or empty");
        }
        return value;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("userEmail"),
            rs.getString("username"),
            rs.getString("userPassword"),
            rs.getString("fieldOfWork"),
            rs.getInt("age"),
            UserTypes.valueOf(rs.getString("userType")),
            rs.getInt("monthOfBirth"),
            rs.getInt("dayOfBirth"),
            rs.getInt("yearOfBirth")
        );
    }
}
