package gui.components;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import service.AuthenticateUserService;
import service.JobInsertionService;
import service.JobRetrievalService;
import service.ScraperService;
import service.UserManagementService;

public class MainComponent extends TabPane {

    private final Stage primaryStage;
    private final AuthenticateUserService authService;

    public MainComponent(Stage primaryStage,
                         JobInsertionService jobInsertionService,
                         JobRetrievalService jobRetrievalService,
                         ScraperService scraperService,
                         UserManagementService userManagementService,
                         AuthenticateUserService authService, // Add authService parameter
                         String currentUsername) {

        this.primaryStage = primaryStage; // Store primaryStage for later use
        this.authService = authService; // Store authService for later use

        // Create components
        HomeComponent homeComponent = new HomeComponent();
        ScrapingComponent scrapingComponent = new ScrapingComponent(scraperService, jobInsertionService);
        StatisticsComponent statisticsComponent = new StatisticsComponent(jobRetrievalService);
        DatabaseComponent databaseComponent = new DatabaseComponent(
                jobInsertionService,
                jobRetrievalService,
                scrapingComponent,
                statisticsComponent
        );
        MachineLearningComponent machineLearningComponent = new MachineLearningComponent(jobRetrievalService);
        UserManagementComponent userManagementComponent = new UserManagementComponent(userManagementService, currentUsername);

        // Create the Logout Button Tab
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> logout());

        // Create a simple layout for the logout button within a HBox
        HBox logoutHBox = new HBox(logoutButton);
        logoutHBox.setPadding(new Insets(10));
        logoutHBox.setSpacing(10);

        // Create a tab for the logout button
        Tab logoutTab = new Tab("Logout");
        logoutTab.setContent(logoutHBox);

        // Add tabs to the TabPane
        getTabs().addAll(
                homeComponent,
                scrapingComponent,
                databaseComponent,
                machineLearningComponent,
                statisticsComponent,
                userManagementComponent,
                logoutTab // Add the logout tab here
        );
    }

    private void logout() {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("You will be redirected to the login screen.");

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                // Redirect to the login screen (use your primaryStage for this)
                showLoginScreen();
            }
        });
    }

    private void showLoginScreen() {
        // Create the LoginComponent and pass necessary services
        LoginComponent loginComponent = new LoginComponent(primaryStage, authService, this::onClientLogin);

        // Set the scene to the LoginComponent
        primaryStage.setScene(new Scene(loginComponent, 300, 250)); // Adjust the size as necessary
        primaryStage.show();
    }

    private void onClientLogin() {
        // Handle actions after a client logs in
        // For example, navigate to the client dashboard or main screen
        System.out.println("Client logged in successfully!");
    }
}
