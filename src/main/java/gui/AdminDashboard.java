package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import gui.AdminButtons.*;

public class AdminDashboard {
    private Stage stage;

    public AdminDashboard(Stage stage) {
        this.stage = stage;
        initialize();
    }

    private void initialize() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        // Create title label
        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create buttons
        SaveUser buttonSaveUser = new SaveUser();
        GetUserByUsername buttonGetUserByUsername = new GetUserByUsername();
        GetUserByEmail buttonGetUserByEmail = new GetUserByEmail();
        GetAllUsers buttonGetAllUsers = new GetAllUsers();
        GetUsersByType buttonGetUsersByType = new GetUsersByType();
        UpdateUser buttonUpdateUser = new UpdateUser();
        DeleteUser buttonDeleteUser = new DeleteUser();

        // Style buttons
        buttonSaveUser.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        buttonGetUserByUsername.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        buttonGetUserByEmail.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        buttonGetAllUsers.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        buttonGetUsersByType.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        buttonUpdateUser.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        buttonDeleteUser.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add components to the layout
        root.getChildren().addAll(
                titleLabel,
                buttonSaveUser,
                buttonGetUserByUsername,
                buttonGetUserByEmail,
                buttonGetAllUsers,
                buttonGetUsersByType,
                buttonUpdateUser,
                buttonDeleteUser
        );

        // Create scene and set the stage
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
    }
}
