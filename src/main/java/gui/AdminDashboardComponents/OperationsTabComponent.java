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

        // Create a VBox layout for the buttons with improved styling
        VBox buttonContainer = new VBox(20); // Increased spacing
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setStyle("-fx-padding: 20px; -fx-background-color: #f5f5f5;");

        // Create buttons using custom button classes with styling
        Button buttonSaveUser = styleButton(new SaveUser(), "#4CAF50"); // Green for create
        Button buttonGetUserByUsername = styleButton(new GetUserByUsername(), "#2196F3"); // Blue for search
        Button buttonGetUserByEmail = styleButton(new GetUserByEmail(), "#2196F3"); // Blue for search
        Button buttonGetAllUsers = styleButton(new GetAllUsers(), "#2196F3"); // Blue for search
        Button buttonGetUsersByType = styleButton(new GetUsersByType(), "#2196F3"); // Blue for search
        Button buttonUpdateUser = styleButton(new UpdateUser(), "#FFA726"); // Orange for update
        Button buttonDeleteUser = styleButton(new DeleteUser(), "#F44336"); // Red for delete

        // Set consistent width for all buttons
        double buttonWidth = 250;
        buttonSaveUser.setPrefWidth(buttonWidth);
        buttonGetUserByUsername.setPrefWidth(buttonWidth);
        buttonGetUserByEmail.setPrefWidth(buttonWidth);
        buttonGetAllUsers.setPrefWidth(buttonWidth);
        buttonGetUsersByType.setPrefWidth(buttonWidth);
        buttonUpdateUser.setPrefWidth(buttonWidth);
        buttonDeleteUser.setPrefWidth(buttonWidth);

        // Add buttons to the layout with spacing
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

    private Button styleButton(Button button, String color) {
        // Base style
        String baseStyle = String.format(
            "-fx-background-color: %s; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 10px 20px; " +
            "-fx-cursor: hand; " +
            "-fx-background-radius: 5;",
            color
        );
        button.setStyle(baseStyle);

        // Hover effect
        button.setOnMouseEntered(e -> 
            button.setStyle(baseStyle + "-fx-background-color: derive(" + color + ", 20%);")
        );
        button.setOnMouseExited(e -> 
            button.setStyle(baseStyle)
        );

        return button;
    }
}
