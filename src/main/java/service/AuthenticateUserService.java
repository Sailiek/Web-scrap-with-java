package service;

import data.dao.UserDAOImpl;
import data.model.User;

public class AuthenticateUserService {
    private final UserDAOImpl userDAO;

    public AuthenticateUserService() {
        this.userDAO = new UserDAOImpl();
    }

    public User authenticateUser(String username, String password) {
        if (userDAO.authenticateUser(username, password)) {
            return userDAO.getUserByUsername(username);
        }
        return null;
    }
}
