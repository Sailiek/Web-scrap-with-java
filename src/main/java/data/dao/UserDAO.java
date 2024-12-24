package data.dao;

import data.model.User;
import data.model.UserTypes;
import java.util.List;

public interface UserDAO {
    void saveUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByType(UserTypes userType);
    void updateUser(User user);
    void deleteUser(String username);
    boolean authenticateUser(String username, String password);
}
