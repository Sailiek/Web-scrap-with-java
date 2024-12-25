package service;

import data.dao.UserDAOImpl;
import data.model.User;
import data.model.UserTypes;

public class SaveUserService {

    public static void saveUser(String nom, String prenom, String userEmail, String username,
                                String userPassword, String fieldOfWork, int age, UserTypes userType,
                                int monthOfBirth, int dayOfBirth, int yearOfBirth) {
        // Logic to save the user, including validation and passing the data to the DAO
        UserDAOImpl userDAO = new UserDAOImpl();

        // Creating a new User object and setting its properties
        User newUser = new User();
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setUserEmail(userEmail);
        newUser.setUsername(username);
        newUser.setUserPassword(userPassword);
        newUser.setFieldOfWork(fieldOfWork);
        newUser.setAge(age);
        newUser.setUserType(userType);  // This will now accept the UserTypes enum directly
        newUser.setMonthOfBirth(monthOfBirth);
        newUser.setDayOfBirth(dayOfBirth);
        newUser.setYearOfBirth(yearOfBirth);

        // Call the DAO to save the new user (implementation of save logic in DAO)
        userDAO.saveUser(newUser);
    }

}
