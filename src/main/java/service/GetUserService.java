package service;

import data.model.User;
import java.sql.*;
import data.util.DatabaseConnectionManager;
import data.model.UserTypes;


public class GetUserService {

    public static User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setFieldOfWork(rs.getString("fieldOfWork"));
                user.setAge(rs.getInt("age"));
                user.setUserType(UserTypes.valueOf(rs.getString("userType")));
                user.setMonthOfBirth(rs.getInt("monthOfBirth"));
                user.setDayOfBirth(rs.getInt("dayOfBirth"));
                user.setYearOfBirth(rs.getInt("yearOfBirth"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
