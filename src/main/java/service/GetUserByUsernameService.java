package service;

import data.dao.UserDAOImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import data.model.User;

public class GetUserByUsernameService {

    public static void getUserByUsername(String username) {
        // Logic for getting user by email
        UserDAOImpl userDAO = new UserDAOImpl();

        // Call the instance method getUserByEmail to retrieve the user
        User user = userDAO.getUserByUsername(username);

        // Create an Alert to display the results in a pop-up
        if (user == null) {
            showAlert("No User Found", "No user found with the provided username.");
        } else {
            showUserPopup(user);
        }
    }

    private static void showAlert(String title, String message) {
        // Show a simple alert if no user is found
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showUserPopup(User user) {
        // Create a VBox layout to display the user's information
        VBox vbox = new VBox(10);

        // Displaying user information (adjust based on available fields)
        Label usernameLabel = new Label("Username: " + user.getUsername());
        // If your User class doesn't have getEmail(), use other available fields:
        // For example, displaying just the username:
        vbox.getChildren().add(usernameLabel);

        // Create a scene and stage for the user details popup
        Scene scene = new Scene(vbox, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("User Details");
        stage.setScene(scene);
        stage.show();
    }
}
