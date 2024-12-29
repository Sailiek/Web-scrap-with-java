package gui.AdminButtons;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import data.model.User;
import data.model.UserTypes;
import data.model.UserValidator;
import data.model.RegexExceptions.*;
import service.GetUserService;
import service.ReceiveCurrentUsername;
import service.SaveUserService;
import service.UpdateUserService;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import data.model.RegexExceptions.RegexValidationException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;





public class UpdateUser extends Button {

    private Map<TextField, Label> errorLabels = new HashMap<>();
    private Map<TextField, Boolean> validationStatus = new HashMap<>();
    private Button updateButton;
    public String username;


    public UpdateUser() {
        super("Update User");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        this.setOnAction(event -> showUsernameInputPopup());

    }


    private void showUsernameInputPopup() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Enter Username");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        Button fetchButton = new Button("Fetch User");

        fetchButton.setOnAction(e -> {
            username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                User user = GetUserService.getUserByUsername(username);
                if (user != null) {
                    popupStage.close();
                    showUpdatePopup(user);
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

        Stage popupStage = new Stage();
        popupStage.setTitle("Update User");

        // Create input fields with validation
        //TextField usernameField = createInputField("Username:", gridPane, 0, user.getUsername());

        //TextField nomField = createInputField("Nom:", gridPane, 1, user.getNom());


        //TextField prenomField = createInputField("Prenom:", gridPane, 2, user.getPrenom());


        //TextField emailField = createInputField("Email:", gridPane, 3, user.getUserEmail());


        //TextField passwordField = createInputField("Password:", gridPane, 4, user.getUserPassword());


        //TextField fieldOfWorkField = createInputField("Field of Work:", gridPane, 5, user.getFieldOfWork());


        //TextField ageField = createInputField("Age:", gridPane, 6, String.valueOf(user.getAge()));


        //TextField userTypeField = createInputField("User Type:", gridPane, 7, user.getUserType().toString());


        // Create date fields with validation
        //TextField dayField = createInputField("Day of Birth (1-31):", gridPane, 8, String.valueOf(user.getDayOfBirth()));


        //TextField monthField = createInputField("Month of Birth (1-12):", gridPane, 9, String.valueOf(user.getMonthOfBirth()));


        //TextField yearField = createInputField("Year of Birth (1900-2023):", gridPane, 10, String.valueOf(user.getYearOfBirth()));




        Label newUsernameLabel = new Label("Username:");
        TextField newUsernameField = new TextField(user.getUsername());
        newUsernameField.setDisable(false);
        newUsernameField.setEditable(true);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(user.getUserEmail());
        emailField.setDisable(false);
        emailField.setEditable(true);

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField(user.getUserPassword());
        passwordField.setDisable(false);
        passwordField.setEditable(true);


        Label nomLabel = new Label("Nom:");
        TextField nomField = new TextField(user.getNom());
        newUsernameField.setDisable(false);
        newUsernameField.setEditable(true);

        Label prenomLabel = new Label("Prenom:");
        TextField prenomField = new TextField(user.getPrenom());
        emailField.setDisable(false);
        emailField.setEditable(true);

        Label fieldOfWorkLabel = new Label("field of work :");
        TextField fieldOfWorkField = new TextField(user.getFieldOfWork());
        passwordField.setDisable(false);
        passwordField.setEditable(true);


        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField(String.valueOf(user.getAge()));
        newUsernameField.setDisable(false);
        newUsernameField.setEditable(true);

        Label userTypeLabel = new Label("Email:");
        TextField userTypeField = new TextField(String.valueOf(user.getUserType()));
        emailField.setDisable(false);
        emailField.setEditable(true);

        Label dayLabel = new Label("Day of birth:");
        TextField dayField = new TextField(String.valueOf(user.getDayOfBirth()));
        passwordField.setDisable(false);
        passwordField.setEditable(true);


        Label monthLabel = new Label("Month of birth:");
        TextField monthField = new TextField(String.valueOf(user.getMonthOfBirth()));
        emailField.setDisable(false);
        emailField.setEditable(true);

        Label yearLabel = new Label("Year of birth:");
        TextField yearField = new TextField(String.valueOf(user.getYearOfBirth()));
        passwordField.setDisable(false);
        passwordField.setEditable(true);






        updateButton = new Button("Update");



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


        gridPane.add(nomLabel, 0, 3);
        gridPane.add(nomField, 1, 3);

        gridPane.add(prenomLabel, 0, 4);
        gridPane.add(prenomField, 1, 4);

        gridPane.add(userTypeLabel, 0, 5);
        gridPane.add(userTypeField, 1, 5);


        gridPane.add(fieldOfWorkLabel, 0, 6);
        gridPane.add(fieldOfWorkField, 1, 6);

        gridPane.add(ageLabel, 0, 7);
        gridPane.add(ageField, 1, 7);

        gridPane.add(dayLabel, 0, 8);
        gridPane.add(dayField, 1, 8);

        gridPane.add(monthLabel, 0, 9);
        gridPane.add(monthField, 1, 9);

        gridPane.add(yearLabel, 0, 10);
        gridPane.add(yearField, 1, 10);



        User updatedUser = new User();

        updateButton = new Button("update user");

        //updateButton.setDisable(true); // Initially disabled until all fields are valid
        updateButton.setOnAction(e -> {
            try {
                updatedUser.setUsername(newUsernameField.getText().trim());
                updatedUser.setNom(nomField.getText().trim());
                updatedUser.setPrenom(prenomField.getText().trim());
                updatedUser.setUserEmail(emailField.getText().trim());
                updatedUser.setUserPassword(passwordField.getText().trim());
                updatedUser.setFieldOfWork(fieldOfWorkField.getText().trim());
                updatedUser.setAge(Integer.parseInt(ageField.getText().trim()));
                updatedUser.setUserType(UserTypes.valueOf(userTypeField.getText().trim().toUpperCase()));
                updatedUser.setMonthOfBirth(Integer.parseInt(monthField.getText().trim()));
                updatedUser.setDayOfBirth(Integer.parseInt(dayField.getText().trim()));
                updatedUser.setYearOfBirth(Integer.parseInt(yearField.getText().trim()));
                // Validate the complete user object
                UserValidator.validateUser(updatedUser);

                // Call the updateUser method with the updated User object
                UpdateUserService updateUserService = new UpdateUserService();
                updateUserService.updateUser(updatedUser,username);
                logUserUpdate(updatedUser,username);
                showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
                popupStage.close();
            } catch (RegexValidationException ex) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the user: " + ex.getMessage());
            }
        });




        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> popupStage.close());

        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);

        buttonPane.add(updateButton, 0, 0);
        buttonPane.add(cancelButton, 1, 0);

        gridPane.add(buttonPane, 1, 11);

        Scene scene = new Scene(gridPane);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    private TextField createInputField(String labelText, GridPane gridPane, int row, String value) {

        Label label = new Label(labelText);
        TextField textField = new TextField(value);
        textField.setDisable(false);
        textField.setEditable(true);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(300);

        gridPane.add(label, 0, row);
        gridPane.add(textField, 1, row);
        gridPane.add(errorLabel, 2, row);

        errorLabels.put(textField, errorLabel);
        validationStatus.put(textField, false);

        return textField;
    }

    private void updateUpdateButtonState() {
        boolean allValid = validationStatus.values().stream().allMatch(valid -> valid);
        updateButton.setDisable(!allValid);
    }



    private void logUserUpdate(User user, String previous_username) {
        String currentusername = ReceiveCurrentUsername.currentUsername;
        User admin_user = GetUserService.getUserByUsername(currentusername);
        String logFilePath = "logfile.txt"; // Specify the log file path
        LocalDateTime now = LocalDateTime.now(); // Get the current date and time
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Format the date and time
        String logEntry = String.format("Updated by %s : %s - User: %s, To New User: %s, with email : %s, Password : %s%n",admin_user.getUserType(), timestamp, previous_username, user.getUsername(),user.getUserEmail(),user.getUserPassword());

        try (FileWriter writer = new FileWriter(logFilePath, true)) { // Open the file in append mode
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }




    private void updateErrorLabel(Control field, boolean isValid) {
        Label errorLabel = errorLabels.get(field);
        if (errorLabel != null) {
            if (!isValid) {
                try {
                    if (field instanceof TextField) {
                        String value = ((TextField) field).getText().trim();
                        if (value.isEmpty()) {
                            errorLabel.setText("This field is required");
                        } else {
                            validateFieldValue(field);
                        }
                    }
                } catch (RegexValidationException e) {
                    errorLabel.setText(e.getMessage());
                }
            } else {
                errorLabel.setText("");
            }
        }
    }

    private boolean validateField(ValidationFunction validator) {
        try {
            validator.validate();
            return true;
        } catch (RegexValidationException e) {
            return false;
        }
    }

    private void validateFieldValue(Control field) throws RegexValidationException {
        if (field instanceof TextField) {
            String value = ((TextField) field).getText().trim();
            if (field.getId() != null) {
                switch (field.getId()) {
                    case "day":
                        UserValidator.validateDayOfBirth(Integer.parseInt(value));
                        break;
                    case "month":
                        UserValidator.validateMonthOfBirth(Integer.parseInt(value));
                        break;
                    case "year":
                        UserValidator.validateYearOfBirth(Integer.parseInt(value));
                        break;
                }
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FunctionalInterface
    private interface ValidationFunction {
        void validate() throws RegexValidationException;
    }
}
