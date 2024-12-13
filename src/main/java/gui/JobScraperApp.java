package gui;

import java.util.List;

import data.model.Job;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.JobInsertionService;
import service.JobRetrievalService;
import service.ScraperService;

public class JobScraperApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Step 1: Create a ListView to display job information
        ListView<String> jobListView = new ListView<>();
        jobListView.setPrefWidth(600); // Set preferred width to ensure it starts with a width
        jobListView.setMaxWidth(Double.MAX_VALUE); // Allow it to grow with the window

        // Step 2: Initialize scraper and get scraped data
        ScraperService scraperService = new ScraperService();
        List<String> jobData = scraperService.getAllJobData();
        jobListView.getItems().addAll(jobData);

        // Step 3: Create a Button to insert jobs into the database
        Button insertJobsButton = new Button("Insert Jobs into Database");
        insertJobsButton.setOnAction(event -> {
            // When the button is clicked, insert the scraped data into the database
            JobInsertionService jobInsertionService = new JobInsertionService();
            jobInsertionService.insertJobs(jobData);
            System.out.println("Jobs have been inserted into the database.");
        });

        // Step 4: Create a Button to view jobs from the database
        Button viewJobsButton = new Button("View Jobs from Database");
        viewJobsButton.setOnAction(event -> {
            // Fetch job data from the database using JobRetrievalService
            JobRetrievalService jobRetrievalService = new JobRetrievalService();
            List<Job> jobsFromDb = jobRetrievalService.getAllJobs();

            // Clear the ListView and add the fetched jobs
            jobListView.getItems().clear(); // Clear the old items

            for (Job job : jobsFromDb) {
                // Format the job data (you can customize the display format as needed)
                String jobDisplay = String.format("Title: %s, Company: %s, Experience: %s",
                        job.getTitle(), job.getCompany(), job.getExperience());
                jobListView.getItems().add(jobDisplay); // Add each job to the ListView
            }

            System.out.println("Jobs from the database have been displayed.");
        });

        // Step 5: Set up the layout and scene
        VBox root = new VBox(10, jobListView, insertJobsButton, viewJobsButton); // Added another button
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setResizable(true);

        // Handle the window close event
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        // Set up the stage and show it
        primaryStage.setTitle("Job Scraper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}