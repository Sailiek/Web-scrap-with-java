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
        jobListView.setPrefWidth(600); // Set preferred width to ensure it starts with a width
        jobListView.setMaxWidth(Double.MAX_VALUE); // Allow it to grow with the window

        // Step 2: Initialize scraper and get scraped data
        RekruteScraper scraper = new RekruteScraper();
        List<String> jobData = scraper.getScrapedData();

        // Step 3: Populate the ListView with job data
        jobListView.getItems().addAll(jobData);

        // Step 4: Set up the layout and scene
        VBox root = new VBox(jobListView);
        Scene scene = new Scene(root, 600, 400);

        // Make sure the window is resizable
        primaryStage.setResizable(true);

        // Stop the program when the "X" button is pressed
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0); // Exit the program explicitly
        });

        primaryStage.setTitle("Job Scraper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
