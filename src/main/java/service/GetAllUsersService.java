package service;

import data.dao.UserDAOImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;
import data.model.User;

public class GetAllUsersService {

    public static void getAllUsers() {
        // Logic for getting all users
        UserDAOImpl userDAO = new UserDAOImpl();

        // Call the instance method getAllUsers to retrieve users
        List<User> users = userDAO.getAllUsers();

        // Create an Alert to display the results in a pop-up
        if (users.isEmpty()) {
            showAlert("No Users", "There are no users in the database.");
        } else {
            showUserListPopup(users);
        }
    }

    private static void showAlert(String title, String message) {
        // Show a simple alert if no users are found
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showUserListPopup(List<User> users) {
        // Create a VBox layout to hold the ListView
        VBox vbox = new VBox(10);
        ListView<String> listView = new ListView<>();

        // Populate the ListView with user data (using just the username)
        for (User user : users) {
            // Displaying only the username as we don't have the getEmail() method
            listView.getItems().add(user.getUsername());
        }

        // Create a scene and stage for the user list popup
        Scene scene = new Scene(vbox, 400, 300);
        Stage stage = new Stage();
        stage.setTitle("All Users");
        vbox.getChildren().add(listView);
        stage.setScene(scene);
        stage.show();
    }
}
