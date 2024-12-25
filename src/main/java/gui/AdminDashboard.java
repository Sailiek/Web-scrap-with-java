package gui;

import gui.AdminDashboardComponents.OperationsTabComponent;
import gui.AdminDashboardComponents.StatisticsTabComponent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AdminStatisticsService;
import data.dao.UserDAOImpl;

public class AdminDashboard {
    private Stage stage;

    public AdminDashboard(Stage stage) {
        this.stage = stage;
        initialize();
    }

    private void initialize() {
        // Create the root layout
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        // Create title label
        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create a TabPane for tabs
        TabPane tabPane = new TabPane();

        // Add the OperationsTab
        OperationsTabComponent operationsTab = new OperationsTabComponent();
        tabPane.getTabs().add(operationsTab);

        // Add the StatisticsTab
        UserDAOImpl userDAO = new UserDAOImpl(); // DAO instance
        AdminStatisticsService statisticsService = new AdminStatisticsService(userDAO); // Service instance
        StatisticsTabComponent statisticsTab = new StatisticsTabComponent(statisticsService);
        tabPane.getTabs().add(statisticsTab);

        // Add components to the root layout
        root.getChildren().addAll(titleLabel, tabPane);

        // Create scene and set the stage
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
    }
}
