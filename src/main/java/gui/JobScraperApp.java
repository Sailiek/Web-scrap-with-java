package gui;

import gui.components.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.*;
import data.util.DatabaseConnectionManager;
import data.model.User;

public class JobScraperApp extends Application {
    private Stage primaryStage;
    private AuthenticateUserService authService;
    private JobInsertionService jobInsertionService;
    private JobRetrievalService jobRetrievalService;
    private ScraperService scraperService;
    private UserManagementService userManagementService;



    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        initializeServices();
        showLoginForm();
    }

    private void initializeServices() {
        this.authService = new AuthenticateUserService();
        this.jobInsertionService = new JobInsertionService();
        this.jobRetrievalService = new JobRetrievalService();
        this.scraperService = new ScraperService();
        this.userManagementService = new UserManagementService();
        // Initialize the new service
    }

    private void showLoginForm() {
        LoginComponent loginComponent = new LoginComponent(
                primaryStage,
                authService,
                this::showMainInterface
        );

        Scene scene = new Scene(loginComponent, 300, 250);
        primaryStage.setTitle("Login - Job Scraper Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMainInterface() {
        User user;
        String name = ReceiveCurrentUsername.currentUsername;
        user = GetUserService.getUserByUsername(name);
        MainComponent mainComponent = new MainComponent(
                primaryStage,               // Pass the primaryStage
                jobInsertionService,        // Pass the jobInsertionService
                jobRetrievalService,        // Pass the jobRetrievalService
                scraperService,             // Pass the scraperService
                userManagementService,      // Pass the userManagementService
                authService,                 // Pass the authService
                user
        );

        Scene scene = new Scene(mainComponent, 600, 600);
        primaryStage.setTitle("Job Scraper Application");
        primaryStage.setScene(scene);
    }


    public static void main(String[] args) {
        launch();
    }
}
