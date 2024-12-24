package gui;

import gui.components.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.JobInsertionService;
import service.JobRetrievalService;
import service.ScraperService;
import service.AuthenticateUserService;

public class JobScraperApp extends Application {
    private Stage primaryStage;
    private AuthenticateUserService authService;
    private JobInsertionService jobInsertionService;
    private JobRetrievalService jobRetrievalService;
    private ScraperService scraperService;

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
        MainComponent mainComponent = new MainComponent(
            jobInsertionService,
            jobRetrievalService,
            scraperService
        );

        Scene scene = new Scene(mainComponent, 600, 600);
        primaryStage.setTitle("Job Scraper Application");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
