package service;

import data.dao.UserDAOImpl;

public class DeleteUserService {

    public static void deleteUser(String username) {
        // Create an instance of UserDAOImpl
        UserDAOImpl userDAO = new UserDAOImpl();

        // Call the instance method deleteUser
        userDAO.deleteUser(username);
    }
}
