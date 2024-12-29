package gui.components;

import data.model.RegexExceptions.RegexValidationException;
import data.model.User;
import data.model.UserTypes;
import data.model.UserValidator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import service.GetUserService;
import service.ReceiveCurrentUsername;
import service.UserManagementService;
import gui.components.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserManagementComponent extends javafx.scene.control.Tab {

    public String currentusername;




    public UserManagementComponent(UserManagementService userManagementService) {
        setText("User Management");
        currentusername = ReceiveCurrentUsername.currentUsername;
        User user = GetUserService.getUserByUsername(currentusername);


        // Create UI elements
        Label newUsernameLabel = new Label("New Username:");
        TextField newUsernameField = new TextField(user.getUsername());
        newUsernameField.setDisable(false);
        newUsernameField.setEditable(true);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(user.getUserEmail());
        emailField.setDisable(false);
        emailField.setEditable(true);

        Label passwordLabel = new Label("New Password:");
        TextField passwordField = new TextField(user.getUserPassword());
        passwordField.setDisable(false);
        passwordField.setEditable(true);

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
        User updateduser = new User();

        // Button action
        updateButton.setOnAction(e -> {

            try{
                updateduser.setUsername(newUsernameField.getText());
                String email = emailField.getText();
                updateduser.setUserEmail(email);
                String password = passwordField.getText();
                updateduser.setUserPassword(password);
                updateduser.setNom(user.getNom());
                updateduser.setPrenom(user.getPrenom());
                updateduser.setUserType(user.getUserType());
                updateduser.setFieldOfWork(user.getFieldOfWork());
                updateduser.setAge(user.getAge());
                updateduser.setDayOfBirth(user.getDayOfBirth());
                updateduser.setMonthOfBirth(user.getMonthOfBirth());
                updateduser.setYearOfBirth(user.getYearOfBirth());

                UserValidator.validateUser(updateduser);

                // Call the service to update user info
                userManagementService.updateUser(updateduser, currentusername);
                logUserUpdate(updateduser, currentusername);
                showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
            } catch (RegexValidationException ex) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
            }
        });

        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);

        buttonPane.add(updateButton, 0, 0);

        gridPane.add(buttonPane, 1, 11);

        Scene scene = new Scene(gridPane);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void logUserUpdate(User user, String previous_username) {
        String logFilePath = "logfile.txt"; // Specify the log file path
        LocalDateTime now = LocalDateTime.now(); // Get the current date and time
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Format the date and time
        String logEntry = String.format("Update own profile : %s - User: %s, To New User: %s, with email : %s, Password : %s%n", timestamp, previous_username, user.getUsername(),user.getUserEmail(),user.getUserPassword());
        ReceiveCurrentUsername.currentUsername = user.getUsername();

        try (FileWriter writer = new FileWriter(logFilePath, true)) { // Open the file in append mode
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }


    @FunctionalInterface
    private interface ValidationFunction {
        void validate() throws RegexValidationException;
    }
}
