package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scrapper.RekruteScraper;

import java.util.List;

public class JobScraperApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Step 1: Create a ListView to display job information
        ListView<String> jobListView = new ListView<>();

        // Step 2: Initialize scraper and get scraped data
        RekruteScraper scraper = new RekruteScraper();
        List<String> jobData = scraper.getScrapedData();

        // Step 3: Populate the ListView with job data
        jobListView.getItems().addAll(jobData);

        // Step 4: Set up the layout and scene
        VBox root = new VBox(jobListView);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Job Scraper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
