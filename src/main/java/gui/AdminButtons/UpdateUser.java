package gui.AdminButtons;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import data.model.User;
import data.model.UserTypes;
import service.GetUserService; // Service to fetch user details
import service.UpdateUserService;

public class UpdateUser extends Button {

    public UpdateUser() {
        super("Update User");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener
        this.setOnAction(event -> {
            // Show popup dialog to take user input
            showUsernameInputPopup();
        });
    }

    private void showUsernameInputPopup() {
        // Create a new stage for the username input popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Enter Username");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        Button fetchButton = new Button("Fetch User");

        // Add action for fetch button
        fetchButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                User user = new User();
                user = GetUserService.getUserByUsername(username);
                if (user != null) {
                    popupStage.close();
                    showUpdatePopup(user); // Pass the fetched user to the update form
                } else {
                    showAlert(Alert.AlertType.ERROR, "User Not Found", "No user found with the username: " + username);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            }
        });

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(fetchButton, 0, 1, 2, 1);

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    private void showUpdatePopup(User user) {
        // Create a new stage for the update form
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Update User");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add input fields populated with user data
        TextField usernameField = createInputField("Username:", gridPane, 0, user.getUsername());
        TextField nomField = createInputField("Nom:", gridPane, 1, user.getNom());
        TextField prenomField = createInputField("Prenom:", gridPane, 2, user.getPrenom());
        TextField emailField = createInputField("Email:", gridPane, 3, user.getUserEmail());
        TextField passwordField = createInputField("Password:", gridPane, 4, user.getUserPassword());
        TextField fieldOfWorkField = createInputField("Field of Work:", gridPane, 5, user.getFieldOfWork());
        TextField ageField = createInputField("Age:", gridPane, 6, String.valueOf(user.getAge()));
        TextField userTypeField = createInputField("User Type:", gridPane, 7, user.getUserType().toString());
        TextField monthField = createInputField("Month of Birth:", gridPane, 8, String.valueOf(user.getMonthOfBirth()));
        TextField dayField = createInputField("Day of Birth:", gridPane, 9, String.valueOf(user.getDayOfBirth()));
        TextField yearField = createInputField("Year of Birth:", gridPane, 10, String.valueOf(user.getYearOfBirth()));

        // Ensure text fields are editable
        usernameField.setEditable(true);
        nomField.setEditable(true);
        prenomField.setEditable(true);
        emailField.setEditable(true);
        passwordField.setEditable(true);
        fieldOfWorkField.setEditable(true);
        ageField.setEditable(true);
        userTypeField.setEditable(true);
        monthField.setEditable(true);
        dayField.setEditable(true);
        yearField.setEditable(true);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            try {
                // Update the user object with the new values from the text fields
                user.setUsername(usernameField.getText().trim());
                user.setNom(nomField.getText().trim());
                user.setPrenom(prenomField.getText().trim());
                user.setUserEmail(emailField.getText().trim());
                user.setUserPassword(passwordField.getText().trim());
                user.setFieldOfWork(fieldOfWorkField.getText().trim());
                user.setAge(Integer.parseInt(ageField.getText().trim()));

                // Parse UserType enum
                try {
                    user.setUserType(UserTypes.valueOf(userTypeField.getText().trim().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid user type. Valid types are: GUEST, CLIENT, ADMIN, SUPERADMIN.");
                    return;
                }

                user.setMonthOfBirth(Integer.parseInt(monthField.getText().trim()));
                user.setDayOfBirth(Integer.parseInt(dayField.getText().trim()));
                user.setYearOfBirth(Integer.parseInt(yearField.getText().trim()));

                // Call the updateUser method with the updated User object
                UpdateUserService updateUserService = new UpdateUserService();
                updateUserService.updateUser(user); // Pass the updated user object
                popupStage.close();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter valid numeric values for age, month, day, and year.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> popupStage.close());

        gridPane.add(updateButton, 0, 11);
        gridPane.add(cancelButton, 1, 11);

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }



    private TextField createInputField(String labelText, GridPane gridPane, int row, String value) {
        Label label = new Label(labelText);
        TextField textField = new TextField(value);
        gridPane.add(label, 0, row);
        gridPane.add(textField, 1, row);
        return textField;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
