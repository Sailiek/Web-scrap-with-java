package gui.AdminDashboardComponents;

import javafx.geometry.Pos;
import gui.AdminButtons.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class OperationsTabComponent extends Tab {
    public OperationsTabComponent() {
        // Set the tab title
        this.setText("Operations");

        // Create a VBox layout for the buttons
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        // Create buttons using custom button classes
        Button buttonSaveUser = new SaveUser();
        Button buttonGetUserByUsername = new GetUserByUsername();
        Button buttonGetUserByEmail = new GetUserByEmail();
        Button buttonGetAllUsers = new GetAllUsers();
        Button buttonGetUsersByType = new GetUsersByType();
        Button buttonUpdateUser = new UpdateUser();
        Button buttonDeleteUser = new DeleteUser();

        // Add buttons to the layout
        buttonContainer.getChildren().addAll(
                buttonSaveUser,
                buttonGetUserByUsername,
                buttonGetUserByEmail,
                buttonGetAllUsers,
                buttonGetUsersByType,
                buttonUpdateUser,
                buttonDeleteUser
        );

        // Add the VBox to the tab's content
        this.setContent(buttonContainer);
    }
}
