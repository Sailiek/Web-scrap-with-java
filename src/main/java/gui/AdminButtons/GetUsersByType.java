package gui.AdminButtons;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import service.GetUsersByTypeService;
import data.model.User;
import data.model.UserTypes; // Make sure this is the correct import for UserTypes enum
import java.util.List;

public class GetUsersByType extends Button {

    public GetUsersByType() {
        super("Get Users by Type");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener to the button
        this.setOnAction(event -> {
            // Show a dialog to select the UserType
            showUserTypeSelectionDialog();
        });
    }

    // This method will show a popup where the user can select the UserType
    private void showUserTypeSelectionDialog() {
        // Create a ComboBox for UserType selection
        ComboBox<UserTypes> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll(UserTypes.values());  // Add all UserTypes to the ComboBox

        // Create a VBox to hold the ComboBox
        VBox vbox = new VBox(10, userTypeComboBox);
        vbox.setStyle("-fx-padding: 10px;");

        // Create a Scene and Stage for the dialog
        Scene scene = new Scene(vbox, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Select User Type");
        stage.setScene(scene);

        // Add a listener for when the user selects a type from the ComboBox
        userTypeComboBox.setOnAction(e -> {
            UserTypes selectedType = userTypeComboBox.getValue();
            if (selectedType != null) {
                // Call the service method with the selected UserType and get users
                List<User> users = GetUsersByTypeService.getUsersByType(selectedType);
                displayUsersInPopup(users); // Show the users in a popup
                stage.close(); // Close the selection dialog
            } else {
                showAlert("Error", "Please select a valid user type.");
            }
        });

        // Show the popup
        stage.show();
    }

    // This method displays the users based on the selected type in a new popup
    private void displayUsersInPopup(List<User> users) {
        if (users.isEmpty()) {
            showAlert("No Users Found", "No users found for the selected type.");
        } else {
            StringBuilder userDetails = new StringBuilder();
            for (User user : users) {
                userDetails.append(user.getUsername()).append("\n"); // Modify to show relevant details
            }

            // Create and display an alert with the user list
            showAlert("Users by Type", userDetails.toString());
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
