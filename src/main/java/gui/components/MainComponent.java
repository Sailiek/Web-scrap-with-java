package gui.components;

import data.model.User;
import data.model.UserTypes;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import service.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class MainComponent extends TabPane {

    private final Stage primaryStage;
    private final AuthenticateUserService authService;
    private static User my_user;
    public MainComponent(Stage primaryStage,
                         JobInsertionService jobInsertionService,
                         JobRetrievalService jobRetrievalService,
                         ScraperService scraperService,
                         UserManagementService userManagementService,
                         AuthenticateUserService authService,
                         User user
    ) {

        this.primaryStage = primaryStage; // Store primaryStage for later use
        this.authService = authService; // Store authService for later use
        this.my_user = user;
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
        UserManagementComponent userManagementComponent = new UserManagementComponent(userManagementService);

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
                logUserLogout(this.my_user);
                this.my_user = null;
                showLoginScreen();
            }
        });
    }


    private void logUserLogout(User user) {
        String logFilePath = "logfile.txt"; // Specify the log file path
        LocalDateTime now = LocalDateTime.now(); // Get the current date and time
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Format the date and time
        String logEntry = String.format("Deconnexion : %s - User: %s, Type: %s%n", timestamp, user.getUsername(), user.getUserType());

        try (FileWriter writer = new FileWriter(logFilePath, true)) { // Open the file in append mode
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }



    private void showLoginScreen() {
        // Create the LoginComponent and pass necessary services
        AuthenticateUserService authService = new AuthenticateUserService();
        LoginComponent loginComponent = new LoginComponent(primaryStage, authService, this::onClientLogin);

        // Set the scene to the LoginComponent

        Platform.runLater(() -> {

            primaryStage.setScene(new Scene(loginComponent, 300, 250)); // Adjust the size as necessary
            primaryStage.show();

        });

    }

    private void onClientLogin() {
        // Handle actions after a client logs in
        // For example, navigate to the client dashboard or main screen
        System.out.println("Client logged in successfully!");
        User new_user = new User();
        String new_currentusername = ReceiveCurrentUsername.currentUsername;
        new_user = GetUserService.getUserByUsername(new_currentusername);
        JobInsertionService jobInsertionService = new JobInsertionService();
        JobRetrievalService jobRetrievalService = new JobRetrievalService();
        ScraperService scraperService = new ScraperService();
        UserManagementService userManagementService = new UserManagementService();
        MainComponent new_maincompo = new MainComponent(primaryStage, jobInsertionService, jobRetrievalService,scraperService,userManagementService,authService,new_user);
        primaryStage.setScene(new Scene(new_maincompo, 1000, 600)); // Adjust the size as necessary
        primaryStage.show();
    }



    public MainComponent(Stage primaryStage,
                         JobInsertionService jobInsertionService,
                         JobRetrievalService jobRetrievalService,
                         AuthenticateUserService authService,
                         ScraperService scraperService

    ) {

        this.primaryStage = primaryStage;
        this.authService = authService;
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



        // Add tabs to the TabPane
        getTabs().addAll(
                homeComponent,
                scrapingComponent,
                databaseComponent,
                machineLearningComponent,
                statisticsComponent
        );
    }


    void onGuestLogin(){
        System.out.println("Client logged in successfully!");
        JobInsertionService jobInsertionService = new JobInsertionService();
        JobRetrievalService jobRetrievalService = new JobRetrievalService();
        ScraperService scraperService = new ScraperService();
        MainComponent new_maincompo = new MainComponent(primaryStage, jobInsertionService, jobRetrievalService,authService, scraperService);
        primaryStage.setScene(new Scene(new_maincompo, 1000, 600)); // Adjust the size as necessary
        primaryStage.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LoginComponent.nochance = 1;
                showLoginScreen();
                timer.cancel();
                System.out.println("Timer canceled");
            }
        }, 5000);
    }


}
