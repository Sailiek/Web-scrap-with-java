package gui.components;

import data.model.User;
import gui.AdminDashboard;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import service.*;
import data.model.UserTypes;
import gui.utils.AlertUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class LoginComponent extends VBox {
    private final Stage primaryStage;
    private final AuthenticateUserService authService;
    private final Runnable onClientLogin;
    public String currentusername;
    public static int nochance = 0;

    public LoginComponent(Stage primaryStage, AuthenticateUserService authService, Runnable onClientLogin) {
        this.primaryStage = primaryStage;
        this.authService = authService;
        this.onClientLogin = onClientLogin;

        setupLoginForm();
    }

    private void setupLoginForm() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        setSpacing(10);

        // Login form
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");


        loginButton.setOnAction(event -> {
            handleLogin(usernameField.getText(), passwordField.getText());

        });


        if (nochance == 0){
            Button Guest = new Button("Continue as a Guest");

            Guest.setOnAction(event -> {
                welcomeguest();
            });

            Button registerButton = new Button("Register");
            registerButton.setOnAction(e -> showRegistrationForm());

            getChildren().addAll(
                    new Label("Login"),
                    usernameField,
                    passwordField,
                    loginButton,
                    registerButton,
                    Guest
            );
        }





        else{
            Button registerButton = new Button("Register");
            registerButton.setOnAction(e -> showRegistrationForm());

            getChildren().addAll(
                    new Label("Login"),
                    usernameField,
                    passwordField,
                    loginButton,
                    registerButton
            );
        }


    }



    private void handleLogin(String username, String password) {
        if (username.isEmpty()) {
            AlertUtils.showError("Error", "Please fill in the username field");
            return;
        }
        if (password.isEmpty()) {
            AlertUtils.showError("Error", "Please fill in the password field");
        }

        User user = authService.authenticateUser(username, password);
        if (user != null) {
            UserTypes userType = user.getUserType();
            logUserLogin(username, userType); // Log the successful login

            if (userType == UserTypes.ADMIN) {
                currentusername = username;
                ReceiveCurrentUsername.currentUsername = currentusername;
                new AdminDashboard(primaryStage);
            } else if (userType == UserTypes.CLIENT) {
                currentusername = username;
                ReceiveCurrentUsername.currentUsername = currentusername;
                onClientLogin.run();
            }
        } else {
            AlertUtils.showError("Error", "Invalid credentials");
        }
    }


    private void welcomeguest(){
        AuthenticateUserService authService = new AuthenticateUserService();
        JobInsertionService jobInsertionService = new JobInsertionService();
        JobRetrievalService jobRetrievalService = new JobRetrievalService();
        ScraperService scraperService = new ScraperService();
        MainComponent mainGuest = new MainComponent(primaryStage, jobInsertionService, jobRetrievalService, authService, scraperService);
        mainGuest.onGuestLogin();

    }

    private void gheyerhamasdserch() {


        // Use Platform.runLater to close the stage on the JavaFX application thread
        Platform.runLater(() -> {

            this.nochance = 1;
            setupLoginForm();
        });
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void logUserLogin(String username, UserTypes userType) {
        String logFilePath = "logfile.txt"; // Specify the log file path
        LocalDateTime now = LocalDateTime.now(); // Get the current date and time
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Format the date and time
        String logEntry = String.format("Connexion : %s - User: %s, Type: %s%n", timestamp, username, userType);

        try (FileWriter writer = new FileWriter(logFilePath, true)) { // Open the file in append mode
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private void showRegistrationForm() {
        // Create a new window for the registration form
        Stage registrationStage = new Stage();
        registrationStage.setTitle("Register");

        // Create form fields for registration
        VBox registrationForm = new VBox(10);
        registrationForm.setPadding(new Insets(20));
        registrationForm.setAlignment(Pos.CENTER);

        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField emailField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField fieldOfWorkField = new TextField();
        Spinner<Integer> monthSpinner = new Spinner<>(1, 12, 1);
        Spinner<Integer> daySpinner = new Spinner<>(1, 31, 1);
        Spinner<Integer> yearSpinner = new Spinner<>(1900, 2024, 2000); // Default year is 2000

        UserTypes userType = UserTypes.CLIENT; // Set user type to CLIENT directly

        // Create register button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String userEmail = emailField.getText();
            String username = usernameField.getText();
            String userPassword = passwordField.getText();
            String fieldOfWork = fieldOfWorkField.getText();
            int monthOfBirth = monthSpinner.getValue();
            int dayOfBirth = daySpinner.getValue();
            int yearOfBirth = yearSpinner.getValue();

            // Calculate the user's age based on the date of birth
            int age = calculateAge(yearOfBirth, monthOfBirth, dayOfBirth);

            // Save the user using SaveUserService
            SaveUserService.saveUser(nom, prenom, userEmail, username, userPassword,
                    fieldOfWork, age, userType, monthOfBirth, dayOfBirth, yearOfBirth);

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration");
            alert.setHeaderText(null);
            alert.setContentText("User registered successfully!");
            alert.showAndWait();

            registrationStage.close(); // Close the registration form after successful registration
        });

        registrationForm.getChildren().addAll(
                new Label("Nom:"), nomField,
                new Label("Prenom:"), prenomField,
                new Label("Email:"), emailField,
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                new Label("Field of Work:"), fieldOfWorkField,
                new Label("Month of Birth:"), monthSpinner,
                new Label("Day of Birth:"), daySpinner,
                new Label("Year of Birth:"), yearSpinner,
                registerButton
        );

        ScrollPane scrollPane = new ScrollPane(registrationForm);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Scene registrationScene = new Scene(scrollPane, 400, 600);
        registrationStage.setScene(registrationScene);
        registrationStage.show();
    }

    private int calculateAge(int birthYear, int birthMonth, int birthDay) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

        int age = currentDate.getYear() - birthDate.getYear();

        if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
                (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--; // If the birthday hasn't happened yet this year, subtract 1
        }

        return age;
    }
}
