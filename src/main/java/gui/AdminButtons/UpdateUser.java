package gui.AdminButtons;

import javafx.scene.control.Button;
import service.UpdateUserService;

public class UpdateUser extends Button {

    public UpdateUser() {
        super("Update User");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener
        this.setOnAction(event -> {
            // Call the service method when clicked
            UpdateUserService.updateUser();
        });
    }
}
