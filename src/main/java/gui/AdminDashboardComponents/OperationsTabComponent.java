package gui.AdminDashboardComponents;

import javafx.geometry.Pos;
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

        // Create buttons
        Button buttonSaveUser = new Button("Save User");
        Button buttonGetUserByUsername = new Button("Get User by Username");
        Button buttonGetUserByEmail = new Button("Get User by Email");
        Button buttonGetAllUsers = new Button("Get All Users");
        Button buttonGetUsersByType = new Button("Get Users by Type");
        Button buttonUpdateUser = new Button("Update User");
        Button buttonDeleteUser = new Button("Delete User");

        // Style buttons
        String buttonStyle = "-fx-font-size: 14px; -fx-padding: 10px 20px;";
        buttonSaveUser.setStyle(buttonStyle);
        buttonGetUserByUsername.setStyle(buttonStyle);
        buttonGetUserByEmail.setStyle(buttonStyle);
        buttonGetAllUsers.setStyle(buttonStyle);
        buttonGetUsersByType.setStyle(buttonStyle);
        buttonUpdateUser.setStyle(buttonStyle);
        buttonDeleteUser.setStyle(buttonStyle);

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
