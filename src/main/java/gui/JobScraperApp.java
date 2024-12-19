package gui;

import gui.components.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import service.JobInsertionService;
import service.JobRetrievalService;
import service.ScraperService;

public class JobScraperApp extends Application {

    @Override
    public void start(Stage stage) {
        // Create services
        JobInsertionService jobInsertionService = new JobInsertionService();
        JobRetrievalService jobRetrievalService = new JobRetrievalService();
        ScraperService scraperService = new ScraperService();

        // Create tab pane
        TabPane tabPane = new TabPane();
        
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
        PredictionComponent predictionComponent = new PredictionComponent(jobRetrievalService);
        
        // Add tabs
        tabPane.getTabs().addAll(
            homeComponent,
            scrapingComponent,
            databaseComponent,
            predictionComponent,
            statisticsComponent
        );

        // Create scene with larger default size to accommodate charts
        Scene scene = new Scene(tabPane, 600, 600);
        
        // Configure stage
        stage.setTitle("Job Scraper Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
