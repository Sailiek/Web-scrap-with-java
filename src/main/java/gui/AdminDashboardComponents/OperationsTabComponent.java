package gui.AdminDashboardComponents;

import javafx.geometry.Pos;
import gui.AdminButtons.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import gui.components.LoginComponent;

public class OperationsTabComponent extends Tab {
    public OperationsTabComponent(Stage primaryStage) {
        // Set the tab title
        this.setText("Operations");

        // Create a VBox layout for the buttons with improved styling
        VBox buttonContainer = new VBox(20); // Increased spacing
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setStyle("-fx-padding: 20px; -fx-background-color: #f5f5f5;");

        // Create buttons using custom button classes with styling
        Button buttonSaveUser = styleButton(new SaveUser(), "#4CAF50"); // Green for create
        Button buttonGetUserByUsername = styleButton(new GetUserByUsername(), "#2196F3"); // Blue for search
        Button buttonGetUserByEmail = styleButton(new GetUserByEmail(), "#2196F3"); // Blue for search
        Button buttonGetAllUsers = styleButton(new GetAllUsers(), "#2196F3"); // Blue for search
        Button buttonGetUsersByType = styleButton(new GetUsersByType(), "#2196F3"); // Blue for search
        Button buttonUpdateUser = styleButton(new UpdateUser(), "#FFA726"); // Orange for update
        Button buttonDeleteUser = styleButton(new DeleteUser(), "#F44336"); // Red for delete

        // Create the Logout Button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-cursor: hand; -fx-background-radius: 5;");
        logoutButton.setOnAction(e -> showLogoutConfirmation(primaryStage));

        // Set consistent width for all buttons
        double buttonWidth = 250;
        buttonSaveUser.setPrefWidth(buttonWidth);
        buttonGetUserByUsername.setPrefWidth(buttonWidth);
        buttonGetUserByEmail.setPrefWidth(buttonWidth);
        buttonGetAllUsers.setPrefWidth(buttonWidth);
        buttonGetUsersByType.setPrefWidth(buttonWidth);
        buttonUpdateUser.setPrefWidth(buttonWidth);
        buttonDeleteUser.setPrefWidth(buttonWidth);
        logoutButton.setPrefWidth(buttonWidth); // Set the width for the logout button

        // Add buttons to the layout with spacing
        buttonContainer.getChildren().addAll(
                buttonSaveUser,
                buttonGetUserByUsername,
                buttonGetUserByEmail,
                buttonGetAllUsers,
                buttonGetUsersByType,
                buttonUpdateUser,
                buttonDeleteUser,
                logoutButton // Add the logout button at the end
        );

        // Add the VBox to the tab's content
        this.setContent(buttonContainer);
    }

    private Button styleButton(Button button, String color) {
        // Base style
        String baseStyle = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-cursor: hand; " +
                        "-fx-background-radius: 5;",
                color
        );
        button.setStyle(baseStyle);

        // Hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(baseStyle + "-fx-background-color: derive(" + color + ", 20%);")
        );
        button.setOnMouseExited(e ->
                button.setStyle(baseStyle)
        );

        return button;
    }

    private void showLogoutConfirmation(Stage primaryStage) {
        // Create a confirmation alert
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        // Show the alert and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // If the user clicks "OK", show the login screen
                showLoginScreen(primaryStage);
            }
            // If the user clicks "Cancel", nothing happens (so no need to do anything)
        });
    }

    private void showLoginScreen(Stage primaryStage) {
        // Create the LoginComponent and pass the necessary services
        LoginComponent loginComponent = new LoginComponent(primaryStage, null, null);

        // Set the scene to the LoginComponent
        primaryStage.setScene(new Scene(loginComponent, 300, 250)); // Adjust the size as necessary
        primaryStage.show();
    }
}
