package gui.AdminButtons;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import service.GetUserByUsernameService;
import java.util.Optional;

public class GetUserByUsername extends Button {

    public GetUserByUsername() {
        super("Get User by Username");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener
        this.setOnAction(event -> {
            showUsernameInputDialog();
        });
    }

    private void showUsernameInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Username");
        dialog.setHeaderText("Please enter the USERNAME of the user you want to retrieve:");
        dialog.setContentText("Username:");

        // Show the dialog and capture the result
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(username -> {
            GetUserByUsernameService.getUserByUsername(username);
        });
    }
}
