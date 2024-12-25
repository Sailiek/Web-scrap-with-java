package gui.AdminButtons;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import service.DeleteUserService;

public class DeleteUser extends Button {

    public DeleteUser() {
        super("Delete User");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener
        this.setOnAction(event -> {
            // Create a TextInputDialog to prompt for the username
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Delete User");
            dialog.setHeaderText("Enter the username of the user to delete:");
            dialog.setContentText("Username:");

            // Show the dialog and get the result
            dialog.showAndWait().ifPresent(username -> {
                // Call the service method and pass the username for deletion
                DeleteUserService.deleteUser(username);
            });
        });
    }
}
