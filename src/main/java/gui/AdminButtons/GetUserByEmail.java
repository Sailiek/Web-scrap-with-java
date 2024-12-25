package gui.AdminButtons;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import service.GetUserByEmailService;
import java.util.Optional;

public class GetUserByEmail extends Button {

    public GetUserByEmail() {
        super("Get User by Email");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener
        this.setOnAction(event -> {
            // Show a dialog to get the user's email
            showEmailInputDialog();
        });
    }

    private void showEmailInputDialog() {
        // Create a TextInputDialog to get the email input
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter User Email");
        dialog.setHeaderText("Please enter the email of the user you want to retrieve:");
        dialog.setContentText("Email:");

        // Show the dialog and capture the result
        Optional<String> result = dialog.showAndWait();

        // If the user provided an email, call the service method
        result.ifPresent(email -> {
            GetUserByEmailService.getUserByEmail(email); // Call the service with the email
        });
    }
}
