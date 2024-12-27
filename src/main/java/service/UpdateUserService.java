package service;

import data.dao.UserDAOImpl;
import data.model.User;
import data.model.UserTypes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import data.util.DatabaseConnectionManager;
import java.sql.SQLException;

public class UpdateUserService {

    // Method to update user information in the database
    public void updateUser(User user, String username) {
        // Establish a connection to the database (you may use a connection pool or ORM)
        // Create an instance of UserDAOImpl
        UserDAOImpl userDAO = new UserDAOImpl();

        userDAO.updateUser(user, username);
    }
}
