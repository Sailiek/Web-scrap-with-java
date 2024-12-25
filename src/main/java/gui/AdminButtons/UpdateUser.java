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
import service.UpdateUserService;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UpdateUser extends Button {
    @FunctionalInterface
    private interface ValidationFunction {
        void validate() throws RegexValidationException;
    }

    private Map<TextField, Label> errorLabels = new HashMap<>();
    private Map<TextField, Boolean> validationStatus = new HashMap<>();
    private Button updateButton;

    public UpdateUser() {
        super("Update User");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        this.setOnAction(event -> showUsernameInputPopup());
    }

    private void showUsernameInputPopup() {
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

        fetchButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
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
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Update User");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create input fields with validation
        TextField usernameField = createInputField("Username:", gridPane, 0, user.getUsername());
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = usernameField.getText().trim();
                if (!text.isEmpty()) {
                    boolean isValid = validateField(() -> UserValidator.validateUsername(text));
                    validationStatus.put(usernameField, isValid);
                    updateErrorLabel(usernameField, isValid);
                    updateUpdateButtonState();
                }
            }
        });
        TextField nomField = createInputField("Nom:", gridPane, 1, user.getNom());
        nomField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = nomField.getText().trim();
                if (!text.isEmpty()) {
                    boolean isValid = validateField(() -> UserValidator.validateNom(text));
                    validationStatus.put(nomField, isValid);
                    updateErrorLabel(nomField, isValid);
                    updateUpdateButtonState();
                }
            }
        });

        TextField prenomField = createInputField("Prenom:", gridPane, 2, user.getPrenom());
        prenomField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = prenomField.getText().trim();
                if (!text.isEmpty()) {
                    boolean isValid = validateField(() -> UserValidator.validatePrenom(text));
                    validationStatus.put(prenomField, isValid);
                    updateErrorLabel(prenomField, isValid);
                    updateUpdateButtonState();
                }
            }
        });

        TextField emailField = createInputField("Email:", gridPane, 3, user.getUserEmail());
        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = emailField.getText().trim();
                if (!text.isEmpty()) {
                    boolean isValid = validateField(() -> UserValidator.validateUserEmail(text));
                    validationStatus.put(emailField, isValid);
                    updateErrorLabel(emailField, isValid);
                    updateUpdateButtonState();
                }
            }
        });

        TextField passwordField = createInputField("Password:", gridPane, 4, user.getUserPassword());
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = passwordField.getText().trim();
                if (!text.isEmpty()) {
                    boolean isValid = validateField(() -> UserValidator.validateUserPassword(text));
                    validationStatus.put(passwordField, isValid);
                    updateErrorLabel(passwordField, isValid);
                    updateUpdateButtonState();
                }
            }
        });

        TextField fieldOfWorkField = createInputField("Field of Work:", gridPane, 5, user.getFieldOfWork());
        fieldOfWorkField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = fieldOfWorkField.getText().trim();
                if (!text.isEmpty()) {
                    boolean isValid = validateField(() -> UserValidator.validateFieldOfWork(text));
                    validationStatus.put(fieldOfWorkField, isValid);
                    updateErrorLabel(fieldOfWorkField, isValid);
                    updateUpdateButtonState();
                }
            }
        });

        TextField ageField = createInputField("Age:", gridPane, 6, String.valueOf(user.getAge()));
        ageField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // When focus is lost
                String text = ageField.getText().trim();
                if (!text.isEmpty()) {
                    boolean isValid = validateField(() -> {
                        try {
                            UserValidator.validateAge(Integer.parseInt(text));
                        } catch (NumberFormatException e) {
                            throw new AgeFormatException("Age must be a number between 15 and 100");
                        }
                    });
                    validationStatus.put(ageField, isValid);
                    updateErrorLabel(ageField, isValid);
                    updateUpdateButtonState();
                }
            }
        });

        TextField userTypeField = createInputField("User Type:", gridPane, 7, user.getUserType().toString());
        userTypeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                boolean isValid = validateField(() -> UserValidator.validateUserType(UserTypes.valueOf(newValue.trim().toUpperCase())));
                validationStatus.put(userTypeField, isValid);
                updateErrorLabel(userTypeField, isValid);
                updateUpdateButtonState();
            }
        });

        // Create date fields with validation
        TextField dayField = createInputField("Day of Birth (1-31):", gridPane, 8, String.valueOf(user.getDayOfBirth()));
        dayField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                boolean isValid = validateField(() -> {
                    try {
                        int day = Integer.parseInt(newValue.trim());
                        UserValidator.validateDayOfBirth(day);
                    } catch (NumberFormatException e) {
                        throw new DateFormatException("Day must be a number between 1 and 31");
                    }
                });
                validationStatus.put(dayField, isValid);
                updateErrorLabel(dayField, isValid);
                updateUpdateButtonState();
            }
        });

        TextField monthField = createInputField("Month of Birth (1-12):", gridPane, 9, String.valueOf(user.getMonthOfBirth()));
        monthField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                boolean isValid = validateField(() -> {
                    try {
                        int month = Integer.parseInt(newValue.trim());
                        UserValidator.validateMonthOfBirth(month);
                    } catch (NumberFormatException e) {
                        throw new DateFormatException("Month must be a number between 1 and 12");
                    }
                });
                validationStatus.put(monthField, isValid);
                updateErrorLabel(monthField, isValid);
                updateUpdateButtonState();
            }
        });

        TextField yearField = createInputField("Year of Birth (1900-2023):", gridPane, 10, String.valueOf(user.getYearOfBirth()));
        yearField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                boolean isValid = validateField(() -> {
                    try {
                        int year = Integer.parseInt(newValue.trim());
                        UserValidator.validateYearOfBirth(year);
                    } catch (NumberFormatException e) {
                        throw new DateFormatException("Year must be a 4-digit number between 1900 and 2023");
                    }
                });
                validationStatus.put(yearField, isValid);
                updateErrorLabel(yearField, isValid);
                updateUpdateButtonState();
            }
        });

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

        updateButton = new Button("Update");
        updateButton.setDisable(true); // Initially disabled until all fields are valid
        updateButton.setOnAction(e -> {
            try {
                // Validate all fields before updating
                if (!validationStatus.values().stream().allMatch(valid -> valid)) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fix all validation errors before updating.");
                    return;
                }

                // Update the user object with the new values from the text fields
                try {
                    user.setUsername(usernameField.getText().trim());
                    user.setNom(nomField.getText().trim());
                    user.setPrenom(prenomField.getText().trim());
                    user.setUserEmail(emailField.getText().trim());
                    user.setUserPassword(passwordField.getText().trim());
                    user.setFieldOfWork(fieldOfWorkField.getText().trim());
                    user.setAge(Integer.parseInt(ageField.getText().trim()));
                    user.setUserType(UserTypes.valueOf(userTypeField.getText().trim().toUpperCase()));
                    user.setMonthOfBirth(Integer.parseInt(monthField.getText().trim()));
                    user.setDayOfBirth(Integer.parseInt(dayField.getText().trim()));
                    user.setYearOfBirth(Integer.parseInt(yearField.getText().trim()));

                    // Validate the complete user object
                    UserValidator.validateUser(user);

                    // Call the updateUser method with the updated User object
                    UpdateUserService updateUserService = new UpdateUserService();
                    updateUserService.updateUser(user);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
                    popupStage.close();
                } catch (RegexValidationException ex) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
                }
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
}
