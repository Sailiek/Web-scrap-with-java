package gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AuthenticateUserService;
import data.model.User;
import data.model.UserTypes;
import gui.utils.AlertUtils;
import gui.AdminDashboard;

public class LoginComponent extends VBox {
    private final Stage primaryStage;
    private final AuthenticateUserService authService;
    private final Runnable onClientLogin;

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

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        getChildren().addAll(
            new Label("Login"),
            usernameField,
            passwordField,
            loginButton
        );

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
    }

    private void handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            AlertUtils.showError("Error", "Please fill in all fields");
            return;
        }

        User user = authService.authenticateUser(username, password);
        if (user != null) {
            UserTypes userType = user.getUserType();
            if (userType == UserTypes.ADMIN || userType == UserTypes.SUPERADMIN) {
                new AdminDashboard(primaryStage);
            } else if (userType == UserTypes.CLIENT || userType == UserTypes.GUEST) {
                onClientLogin.run();
            } else {
                AlertUtils.showError("Error", "Invalid user type");
            }
        } else {
            AlertUtils.showError("Error", "Invalid credentials");
        }
    }
}
