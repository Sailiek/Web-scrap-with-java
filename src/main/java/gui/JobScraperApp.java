package gui;

import data.model.Job;
import data.model.Offer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.JobInsertionService;
import service.JobRetrievalService;
import service.ScraperService;
import machine_learning.EnhancedJobPrediction;

import java.util.List;

public class JobScraperApp extends Application {

    private ListView<String> jobListView;
    private List<Offer> scrapedJobData;

    @Override
    public void start(Stage primaryStage) {
        // Create components
        jobListView = new ListView<>();
        jobListView.setPrefWidth(600);
        jobListView.setPrefHeight(300);
        jobListView.setMaxWidth(Double.MAX_VALUE);
        jobListView.setVisible(false); // Initially hidden

        // Create buttons
        Button scrapButton = new Button("Scrap");
        Button insertJobsButton = new Button("Insert to DB");
        Button viewJobsButton = new Button("See from DB");

        // ML Prediction components
        Label predictionLabel = new Label("Give our model a job title and it will predict the experience you need:");
        TextField jobTitleInput = new TextField();
        jobTitleInput.setPromptText("Enter job title (e.g., Product Manager)");
        TextArea predictionOutput = new TextArea();
        predictionOutput.setEditable(false);
        predictionOutput.setPrefRowCount(2);
        Button predictButton = new Button("Predict");

        // Scrap button action
        scrapButton.setOnAction(event -> {
            try {
                ScraperService scraperService = new ScraperService();
                scrapedJobData = scraperService.getAllJobData();
                jobListView.setVisible(false);
                showAlert("Success", "Scraping completed successfully! Found " + scrapedJobData.size() + " jobs.");
            } catch (Exception e) {
                showAlert("Error", "Error during scraping: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Insert jobs button action
        insertJobsButton.setOnAction(event -> {
            if (scrapedJobData != null && !scrapedJobData.isEmpty()) {
                try {
                    JobInsertionService jobInsertionService = new JobInsertionService();
                    jobInsertionService.insertOffers(scrapedJobData);
                    jobListView.setVisible(false);
                    showAlert("Success", "Jobs have been inserted into the database successfully!");
                } catch (Exception e) {
                    showAlert("Error", "Error inserting jobs into database: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                showAlert("Error", "Please scrap the data first!");
            }
        });

        // View jobs button action
        viewJobsButton.setOnAction(event -> {
            try {
                JobRetrievalService jobRetrievalService = new JobRetrievalService();
                List<Offer> jobsFromDb = jobRetrievalService.getAllJobs();

                jobListView.getItems().clear();
                
                for (Offer offer : jobsFromDb) {
                    StringBuilder jobDisplay = new StringBuilder();
                    jobDisplay.append(String.format("Title: %s\n", offer.getTitre()));
                    jobDisplay.append(String.format("Company: %s\n", offer.getNomEntreprise()));
                    jobDisplay.append(String.format("Location: %s, %s\n", offer.getVille(), offer.getRegion()));
                    jobDisplay.append(String.format("Contract: %s\n", offer.getTypeContrat()));
                    jobDisplay.append(String.format("Education: %s\n", offer.getNiveauEtudes()));
                    jobDisplay.append(String.format("Experience: %s\n", offer.getExperience()));
                    jobDisplay.append(String.format("Skills: %s\n", offer.getCompetencesRequises()));
                    jobDisplay.append(String.format("Languages: %s (%s)\n", offer.getLangue(), offer.getNiveauLangue()));
                    jobDisplay.append("----------------------------------------\n");
                    
                    jobListView.getItems().add(jobDisplay.toString());
                }
                
                jobListView.setVisible(true);
                if (jobsFromDb.isEmpty()) {
                    showAlert("Info", "No jobs found in the database.");
                } else {
                    showAlert("Success", "Retrieved " + jobsFromDb.size() + " jobs from database.");
                }
            } catch (Exception e) {
                showAlert("Error", "Error retrieving jobs from database: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Predict button action
        predictButton.setOnAction(event -> {
            String jobTitle = jobTitleInput.getText().trim();
            if (!jobTitle.isEmpty()) {
                try {
                    Job predictedJob = EnhancedJobPrediction.predictJobDetails(jobTitle);
                    if (predictedJob != null) {
                        String prediction = String.format(
                            "Closest job title: %s\n" +
                            "Predicted Study Level: %s\n" +
                            "Predicted Experience Level: %s",
                            predictedJob.getTitle(),
                            predictedJob.getEducationLevel(),
                            predictedJob.getExperience()
                        );
                        predictionOutput.setText(prediction);
                    } else {
                        predictionOutput.setText("No matching job found.");
                    }
                } catch (Exception e) {
                    showAlert("Error", "Error making prediction: " + e.getMessage());
                }
            } else {
                showAlert("Error", "Please enter a job title!");
            }
        });

        // Set up the layout
        VBox buttonsBox = new VBox(10, scrapButton, insertJobsButton, viewJobsButton);
        VBox predictionBox = new VBox(10, predictionLabel, jobTitleInput, predictButton, predictionOutput);
        
        VBox root = new VBox(15);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(buttonsBox, jobListView, predictionBox);

        Scene scene = new Scene(root, 650, 600);
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setTitle("Job Scraper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
