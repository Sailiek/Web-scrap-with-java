package gui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import service.UserManagementService;

public class UserManagementComponent extends javafx.scene.control.Tab {
    public UserManagementComponent(UserManagementService userManagementService, String currentUsername) {
        setText("User Management");

        // Create UI elements
        Label newUsernameLabel = new Label("New Username:");
        TextField newUsernameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("New Password:");
        TextField passwordField = new TextField();

        Button updateButton = new Button("Update");

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(newUsernameLabel, 0, 0);
        gridPane.add(newUsernameField, 1, 0);

        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailField, 1, 1);

        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);

        gridPane.add(updateButton, 1, 3);

        setContent(gridPane);

        // Button action
        updateButton.setOnAction(e -> {
            String newUsername = newUsernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Call the service to update user info
            boolean success = userManagementService.updateUser(currentUsername, newUsername, email, password);
            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);

            if (success) {
                alert.setTitle("Success");
                alert.setHeaderText("User Updated");
                alert.setContentText("User information has been updated successfully.");
            } else {
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("Failed to update the user information. Please try again.");
            }

            // Show the alert dialog
            alert.showAndWait();  // Add this line to display the alert popup
        });
    }
}
