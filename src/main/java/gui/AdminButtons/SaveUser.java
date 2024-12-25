package gui.AdminButtons;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import data.model.User;
import data.model.UserTypes;
import data.model.UserValidator;
import data.model.RegexExceptions.*;
import service.SaveUserService;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.time.LocalDate;

public class SaveUser extends Button {
    private Map<Control, Label> errorLabels = new HashMap<>();
    private Map<Control, Boolean> validationStatus = new HashMap<>();
    private Button saveButton;

    public SaveUser() {
        super("Save User");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        this.setOnAction(event -> showSaveUserPopup());
    }

    private void showSaveUserPopup() {
        Stage stage = new Stage();
        stage.setTitle("Save New User");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Create fields with validation
        TextField nomField = createTextField("Nom:", gridPane, 0);
        TextField prenomField = createTextField("Prenom:", gridPane, 1);
        TextField userEmailField = createTextField("Email:", gridPane, 2);
        TextField usernameField = createTextField("Username:", gridPane, 3);
        PasswordField userPasswordField = createPasswordField("Password:", gridPane, 4);
        TextField fieldOfWorkField = createTextField("Field of Work:", gridPane, 5);
        TextField ageField = createTextField("Age:", gridPane, 6);
        ComboBox<UserTypes> userTypeComboBox = createUserTypeComboBox("User Type:", gridPane, 7);

        // Create date fields with validation
        TextField dayField = createTextField("Day of Birth (1-31):", gridPane, 8);
        TextField monthField = createTextField("Month of Birth (1-12):", gridPane, 9);
        TextField yearField = createTextField("Year of Birth (1900-2023):", gridPane, 10);

        // Add validation listeners for date fields
        addValidationListener(dayField, text -> validateField(() -> {
            try {
                int day = Integer.parseInt(text);
                UserValidator.validateDayOfBirth(day);
            } catch (NumberFormatException e) {
                throw new DateFormatException("Day must be a number between 1 and 31");
            }
        }));

        addValidationListener(monthField, text -> validateField(() -> {
            try {
                int month = Integer.parseInt(text);
                UserValidator.validateMonthOfBirth(month);
            } catch (NumberFormatException e) {
                throw new DateFormatException("Month must be a number between 1 and 12");
            }
        }));

        addValidationListener(yearField, text -> validateField(() -> {
            try {
                int year = Integer.parseInt(text);
                UserValidator.validateYearOfBirth(year);
            } catch (NumberFormatException e) {
                throw new DateFormatException("Year must be a 4-digit number between 1900 and 2023");
            }
        }));

        // Add validation listeners for other fields
        addValidationListener(nomField, text -> validateField(() -> UserValidator.validateNom(text)));
        addValidationListener(prenomField, text -> validateField(() -> UserValidator.validatePrenom(text)));
        addValidationListener(userEmailField, text -> validateField(() -> UserValidator.validateUserEmail(text)));
        addValidationListener(usernameField, text -> validateField(() -> UserValidator.validateUsername(text)));
        addValidationListener(userPasswordField, text -> validateField(() -> UserValidator.validateUserPassword(text)));
        addValidationListener(fieldOfWorkField, text -> validateField(() -> UserValidator.validateFieldOfWork(text)));
        addValidationListener(ageField, text -> validateField(() -> {
            try {
                UserValidator.validateAge(Integer.parseInt(text));
            } catch (NumberFormatException e) {
                throw new AgeFormatException("Age must be a number between 15 and 100");
            }
        }));

        // Add validation for ComboBox
        userTypeComboBox.setOnAction(e -> {
            UserTypes selectedType = userTypeComboBox.getValue();
            boolean isValid = validateField(() -> UserValidator.validateUserType(selectedType));
            validationStatus.put(userTypeComboBox, isValid);
            updateErrorLabel(userTypeComboBox, isValid);
            updateSaveButtonState();
        });


        saveButton = new Button("Save");
        saveButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        saveButton.setDisable(true); // Initially disabled until all fields are valid

        saveButton.setOnAction(e -> {
            try {
                // Create a new User object with the form data
                User user = new User();
                user.setNom(nomField.getText().trim());
                user.setPrenom(prenomField.getText().trim());
                user.setUserEmail(userEmailField.getText().trim());
                user.setUsername(usernameField.getText().trim());
                user.setUserPassword(userPasswordField.getText().trim());
                user.setFieldOfWork(fieldOfWorkField.getText().trim());
                user.setAge(Integer.parseInt(ageField.getText().trim()));
                user.setUserType(userTypeComboBox.getValue());

                user.setDayOfBirth(Integer.parseInt(dayField.getText().trim()));
                user.setMonthOfBirth(Integer.parseInt(monthField.getText().trim()));
                user.setYearOfBirth(Integer.parseInt(yearField.getText().trim()));

                // Validate entire user object before saving
                UserValidator.validateUser(user);

                // Save the user
                SaveUserService.saveUser(
                    user.getNom(),
                    user.getPrenom(),
                    user.getUserEmail(),
                    user.getUsername(),
                    user.getUserPassword(),
                    user.getFieldOfWork(),
                    user.getAge(),
                    user.getUserType(),
                    user.getMonthOfBirth(),
                    user.getDayOfBirth(),
                    user.getYearOfBirth()
                );

                showAlert(Alert.AlertType.INFORMATION, "Success", "User saved successfully!");
                stage.close();
            } catch (RegexValidationException ex) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while saving the user: " + ex.getMessage());
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        cancelButton.setOnAction(e -> stage.close());

        // Add buttons at the bottom after all fields
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(saveButton, 0, 0);
        buttonPane.add(cancelButton, 1, 0);
        
        // Add button pane at the bottom
        gridPane.add(buttonPane, 1, 11);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    private TextField createTextField(String labelText, GridPane gridPane, int row) {
        Label label = new Label(labelText);
        TextField textField = new TextField();
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

    private PasswordField createPasswordField(String labelText, GridPane gridPane, int row) {
        Label label = new Label(labelText);
        PasswordField passwordField = new PasswordField();
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(300);
        
        gridPane.add(label, 0, row);
        gridPane.add(passwordField, 1, row);
        gridPane.add(errorLabel, 2, row);
        
        errorLabels.put(passwordField, errorLabel);
        validationStatus.put(passwordField, false);
        
        return passwordField;
    }

    private ComboBox<UserTypes> createUserTypeComboBox(String labelText, GridPane gridPane, int row) {
        Label label = new Label(labelText);
        ComboBox<UserTypes> comboBox = new ComboBox<>();
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(300);
        
        comboBox.getItems().addAll(UserTypes.values());
        
        gridPane.add(label, 0, row);
        gridPane.add(comboBox, 1, row);
        gridPane.add(errorLabel, 2, row);
        
        errorLabels.put(comboBox, errorLabel);
        validationStatus.put(comboBox, false);
        
        return comboBox;
    }

    private void addValidationListener(TextInputControl field, Function<String, Boolean> validator) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = validator.apply(newValue);
            validationStatus.put(field, isValid);
            updateErrorLabel(field, isValid);
            updateSaveButtonState();
        });
    }

    private boolean validateField(ValidationFunction validator) {
        try {
            validator.validate();
            return true;
        } catch (RegexValidationException e) {
            return false;
        }
    }

    private void updateErrorLabel(Control field, boolean isValid) {
        Label errorLabel = errorLabels.get(field);
        if (errorLabel != null) {
            if (!isValid) {
                try {
                    if (field instanceof TextInputControl && ((TextInputControl)field).getText().isEmpty()) {
                        errorLabel.setText("This field is required");
                    } else if (field instanceof ComboBox && ((ComboBox<?>)field).getValue() == null) {
                        errorLabel.setText("Please select a user type");
                    } else if (field instanceof TextField && field.getId() != null) {
                        switch (field.getId()) {
                            case "day":
                                errorLabel.setText("Day must be a number between 1 and 31");
                                break;
                            case "month":
                                errorLabel.setText("Month must be a number between 1 and 12");
                                break;
                            case "year":
                                errorLabel.setText("Year must be a 4-digit number between 1900 and 2023");
                                break;
                        }
                    } else {
                        validateFieldValue(field);
                    }
                } catch (RegexValidationException e) {
                    errorLabel.setText(e.getMessage());
                }
            } else {
                errorLabel.setText("");
            }
        }
    }

    private void validateFieldValue(Control field) throws RegexValidationException {
        if (field instanceof TextInputControl) {
            String value = ((TextInputControl)field).getText().trim();
            if (field instanceof TextField) {
                switch (field.getId()) {
                    case "nom":
                        UserValidator.validateNom(value);
                        break;
                    case "prenom":
                        UserValidator.validatePrenom(value);
                        break;
                    case "email":
                        UserValidator.validateUserEmail(value);
                        break;
                    case "username":
                        UserValidator.validateUsername(value);
                        break;
                    case "password":
                        UserValidator.validateUserPassword(value);
                        break;
                    case "fieldOfWork":
                        UserValidator.validateFieldOfWork(value);
                        break;
                    case "age":
                        try {
                            UserValidator.validateAge(Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            throw new AgeFormatException("Age must be a number between 15 and 100");
                        }
                        break;
                }
            }
        } else if (field instanceof ComboBox) {
            UserTypes selectedType = (UserTypes) ((ComboBox<?>)field).getValue();
            UserValidator.validateUserType(selectedType);
        }
    }

    private void updateSaveButtonState() {
        boolean allValid = validationStatus.values().stream().allMatch(valid -> valid);
        saveButton.setDisable(!allValid);
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
