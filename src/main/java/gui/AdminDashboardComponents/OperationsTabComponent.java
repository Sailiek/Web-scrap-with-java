package gui.AdminDashboardComponents;

import data.model.User;
import gui.components.MainComponent;
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
import service.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OperationsTabComponent extends Tab {

    private final AuthenticateUserService authService;
    private final Stage primaryStage;
    private static User my_user;
    public OperationsTabComponent(Stage primaryStage, User user) {


        // Set the tab title
        this.setText("Operations");

        // Create a VBox layout for the buttons with improved styling
        VBox buttonContainer = new VBox(20); // Increased spacing
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setStyle("-fx-padding: 20px; -fx-background-color: #f5f5f5;");

        this.authService = new AuthenticateUserService();
        this.primaryStage = primaryStage;
        this.my_user = user;
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
                logUserLogout(this.my_user);
                this.my_user = null;
                showLoginScreen(primaryStage);
            }
            // If the user clicks "Cancel", nothing happens (so no need to do anything)
        });
    }



    private void logUserLogout(User user) {
        String logFilePath = "logfile.txt"; // Specify the log file path
        LocalDateTime now = LocalDateTime.now(); // Get the current date and time
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Format the date and time
        String logEntry = String.format("Deconnexion : %s - User: %s, Type: %s%n", timestamp, user.getUsername(), user.getUserType());

        try (FileWriter writer = new FileWriter(logFilePath, true)) { // Open the file in append mode
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private void showLoginScreen(Stage primaryStage) {
        // Create the LoginComponent and pass the necessary services
        AuthenticateUserService authService = new AuthenticateUserService();
        LoginComponent loginComponent = new LoginComponent(primaryStage, authService, this::onClientLogin);

        // Set the scene to the LoginComponent
        primaryStage.setScene(new Scene(loginComponent, 300, 250)); // Adjust the size as necessary
        primaryStage.show();
    }

    private void onClientLogin() {
        // Handle actions after a client logs in
        // For example, navigate to the client dashboard or main screen
        System.out.println("Client logged in successfully!");
        User new_user = new User();
        String new_currentusername = ReceiveCurrentUsername.currentUsername;
        new_user = GetUserService.getUserByUsername(new_currentusername);
        JobInsertionService jobInsertionService = new JobInsertionService();
        JobRetrievalService jobRetrievalService = new JobRetrievalService();
        ScraperService scraperService = new ScraperService();
        UserManagementService userManagementService = new UserManagementService();
        MainComponent new_maincompo = new MainComponent(primaryStage, jobInsertionService, jobRetrievalService,scraperService,userManagementService,authService,new_user);
        primaryStage.setScene(new Scene(new_maincompo, 1000, 600)); // Adjust the size as necessary
        primaryStage.show();
    }

}
