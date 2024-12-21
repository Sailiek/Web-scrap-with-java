package gui.components;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import machine_learning.jobRecommendation;
import machine_learning.jobRecommendation.Job;
import service.JobRetrievalService;
import service.JobExportService;
import service.JobExportService.ExportFormat;
import gui.utils.AlertUtils;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecommendationComponent extends Tab {
    private final TextField keywordInput;
    private final TextField experienceInput;
    private final TextField locationInput;
    private final ComboBox<String> contractTypeCombo;
    private final TextArea recommendationsOutput;
    private final Button searchButton;
    private final Button downloadButton;
    private final ComboBox<ExportFormat> formatCombo;
    private final JobRetrievalService jobRetrievalService;
    private final JobExportService jobExportService;
    private List<Job> currentRecommendations;

    public RecommendationComponent(JobRetrievalService jobRetrievalService) {
        this.jobExportService = new JobExportService();
        this.currentRecommendations = null;
        setText("Job Recommendations");
        
        this.jobRetrievalService = jobRetrievalService;
        
        VBox content = new VBox(10);
        
        // Create input fields
        Label keywordLabel = new Label("Job Title or Keywords:");
        keywordInput = new TextField();
        keywordInput.setPromptText("Enter keywords");

        Label experienceLabel = new Label("Maximum Years of Experience:");
        experienceInput = new TextField();
        experienceInput.setPromptText("Enter max experience (e.g., 5)");

        Label locationLabel = new Label("Location:");
        locationInput = new TextField();
        locationInput.setPromptText("Enter location");

        Label contractTypeLabel = new Label("Contract Type:");
        contractTypeCombo = new ComboBox<>();
        contractTypeCombo.getItems().addAll("", "CDI", "CDD");
        contractTypeCombo.setPromptText("Select contract type");

        searchButton = new Button("Find Recommendations");
        
        recommendationsOutput = new TextArea();
        recommendationsOutput.setEditable(false);
        recommendationsOutput.setPrefRowCount(10);
        recommendationsOutput.setWrapText(true);

        // Create download controls
        HBox downloadControls = new HBox(10);
        downloadButton = new Button("Download");
        downloadButton.setDisable(true);
        
        formatCombo = new ComboBox<>();
        formatCombo.getItems().addAll(ExportFormat.values());
        formatCombo.setPromptText("Select format");
        
        downloadControls.getChildren().addAll(formatCombo, downloadButton);
        
        setupSearchButton();
        setupDownloadButton();
        
        // Add all components to the layout
        content.getChildren().addAll(
            keywordLabel, keywordInput,
            experienceLabel, experienceInput,
            locationLabel, locationInput,
            contractTypeLabel, contractTypeCombo,
            searchButton,
            new Label("Recommended Jobs:"),
            recommendationsOutput,
            downloadControls
        );
        
        setContent(content);
    }

    private void setupSearchButton() {
        searchButton.setOnAction(event -> {
            try {
                // Read jobs from CSV
                List<Job> jobList = jobRecommendation.readJobsFromCSV("src/main/java/machine_learning/final_inchaelh.csv");

                // Parse experience input
                String expInput = experienceInput.getText().trim();
                Double maxExperience = expInput.isEmpty() ? null : Double.parseDouble(expInput);

                // Get filtered jobs
                List<Job> filteredJobs = jobRecommendation.filterJobs(
                    jobList,
                    keywordInput.getText().trim(),
                    maxExperience,
                    locationInput.getText().trim(),
                    contractTypeCombo.getValue()
                );

                if (filteredJobs.isEmpty()) {
                    recommendationsOutput.setText("No jobs found matching your criteria.");
                    return;
                }

                // Get recommendations based on the first matching job
                Job targetJob = filteredJobs.get(0);
                List<Job> recommendations = jobRecommendation.recommendJobs(targetJob, filteredJobs, 5);

                // Format and display recommendations
                StringBuilder output = new StringBuilder();
                output.append("Based on your criteria, here are the top recommendations:\n\n");
                
                for (int i = 0; i < recommendations.size(); i++) {
                    Job job = recommendations.get(i);
                    output.append(String.format("Recommendation #%d:\n", i + 1));
                    output.append(job.toString());
                    output.append("\n");
                }

                recommendationsOutput.setText(output.toString());
                currentRecommendations = recommendations;
                downloadButton.setDisable(false);

            } catch (NumberFormatException e) {
                AlertUtils.showAlert("Error", "Please enter a valid number for experience.");
                currentRecommendations = null;
                downloadButton.setDisable(true);
            } catch (Exception e) {
                AlertUtils.showAlert("Error", "Error getting recommendations: " + e.getMessage());
                currentRecommendations = null;
                downloadButton.setDisable(true);
            }
        });
    }

    private void setupDownloadButton() {
        downloadButton.setOnAction(event -> {
            if (currentRecommendations == null || currentRecommendations.isEmpty()) {
                AlertUtils.showAlert("Error", "No recommendations to download.");
                return;
            }

            ExportFormat selectedFormat = formatCombo.getValue();
            if (selectedFormat == null) {
                AlertUtils.showAlert("Error", "Please select a format.");
                return;
            }

            try {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String filename = "job_recommendations_" + timestamp;
                
                String filePath = jobExportService.exportRecommendedJobs(currentRecommendations, selectedFormat, filename);
                AlertUtils.showAlert("Success", "File saved successfully at: " + filePath);
            } catch (Exception e) {
                AlertUtils.showAlert("Error", "Failed to export file: " + e.getMessage());
            }
        });
    }
}
