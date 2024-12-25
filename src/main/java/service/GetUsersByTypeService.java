package service;

import data.dao.UserDAOImpl;  // Import the class where getUsersByType is defined
import data.model.User;       // Import the User class
import data.model.UserTypes;  // Import the UserTypes enum
import java.util.List;

public class GetUsersByTypeService {

    // This method is called from the button to get the users by type
    public static List<User> getUsersByType(UserTypes userType) {
        // Logic for getting users by type from the DAO layer
        UserDAOImpl userDAO = new UserDAOImpl(); // Create an instance of the DAO
        List<User> users = userDAO.getUsersByType(userType); // Call the method from UserDAOImpl

        // Return the list of users
        return users;
    }
}
