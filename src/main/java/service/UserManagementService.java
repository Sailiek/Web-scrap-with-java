package service;

import data.dao.UserDAOImpl;
import data.model.User;
import data.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManagementService {
    public void updateUser(User updateduser, String currentusername) {
        UserDAOImpl userDAO = new UserDAOImpl();

        userDAO.updateUser(updateduser, currentusername);
    }
}
