package gui.AdminButtons;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.DatePicker;
import service.SaveUserService;
import data.model.UserTypes; // Import the UserTypes enum

public class SaveUser extends Button {

    public SaveUser() {
        super("Save User");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener
        this.setOnAction(event -> {
            // Call the service method when clicked
            showSaveUserPopup();
        });
    }

    private void showSaveUserPopup() {
        // Create the popup for user input
        Stage stage = new Stage();
        stage.setTitle("Save New User");

        // Create the grid pane for organizing the fields
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Create the text fields for user input
        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField userEmailField = new TextField();
        TextField usernameField = new TextField();
        PasswordField userPasswordField = new PasswordField();
        TextField fieldOfWorkField = new TextField();
        TextField ageField = new TextField();
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        DatePicker birthDatePicker = new DatePicker();

        // Populate the ComboBox for userType (You can replace these with real values from an enum)
        userTypeComboBox.getItems().addAll("GUEST", "CLIENT", "ADMIN","SUPERADMIN");

        // Add the fields to the grid pane
        gridPane.addRow(0, new javafx.scene.control.Label("Nom:"), nomField);
        gridPane.addRow(1, new javafx.scene.control.Label("Prenom:"), prenomField);
        gridPane.addRow(2, new javafx.scene.control.Label("Email:"), userEmailField);
        gridPane.addRow(3, new javafx.scene.control.Label("Username:"), usernameField);
        gridPane.addRow(4, new javafx.scene.control.Label("Password:"), userPasswordField);
        gridPane.addRow(5, new javafx.scene.control.Label("Field of Work:"), fieldOfWorkField);
        gridPane.addRow(6, new javafx.scene.control.Label("Age:"), ageField);
        gridPane.addRow(7, new javafx.scene.control.Label("User Type:"), userTypeComboBox);
        gridPane.addRow(8, new javafx.scene.control.Label("Date of Birth:"), birthDatePicker);

        // Create a save button
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action to save the data
        saveButton.setOnAction(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String userEmail = userEmailField.getText();
            String username = usernameField.getText();
            String userPassword = userPasswordField.getText();
            String fieldOfWork = fieldOfWorkField.getText();
            int age = Integer.parseInt(ageField.getText());

            // Convert the String userType to the UserTypes enum
            String userTypeString = userTypeComboBox.getValue();
            UserTypes userType = UserTypes.valueOf(userTypeString);  // Convert to enum

//
            int monthOfBirth = birthDatePicker.getValue().getMonthValue();
            int dayOfBirth = birthDatePicker.getValue().getDayOfMonth();
            int yearOfBirth = birthDatePicker.getValue().getYear();

            // Call the SaveUserService to save the user
            SaveUserService.saveUser(nom, prenom, userEmail, username, userPassword, fieldOfWork,
                    age, userType, monthOfBirth, dayOfBirth, yearOfBirth);

            // Close the popup after saving the user
            stage.close();

            // Show success message
            showAlert("User Saved", "The user has been saved successfully.");
        });

        // Add the save button to the grid
        gridPane.addRow(9, saveButton);

        // Create and show the scene
        Scene scene = new Scene(gridPane, 400, 350);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        // Show an alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
