package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboard {
    private Stage stage;

    public AdminDashboard(Stage stage) {
        this.stage = stage;
        initialize();
    }

    private void initialize() {
        VBox root = new VBox(10);
        Label label = new Label("Admin Dashboard - Coming Soon");
        root.getChildren().add(label);
        
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
    }
}
