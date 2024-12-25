package gui.AdminButtons;

import javafx.scene.control.Button;
import service.GetAllUsersService;

public class GetAllUsers extends Button {

    public GetAllUsers() {
        super("Get All Users");
        this.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Add action listener
        this.setOnAction(event -> {
            // Call the service method when clicked
            GetAllUsersService.getAllUsers();
        });
    }
}
