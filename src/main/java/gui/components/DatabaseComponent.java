package gui.components;

import data.model.Offer;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import service.JobInsertionService;
import service.JobRetrievalService;
import gui.utils.AlertUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class DatabaseComponent extends Tab {
    private final Button insertJobsButton;
    private final Button viewJobsButton;
    private final Button emptyDatabaseButton;
    private final ScrapingComponent scrapingComponent;
    private final JobInsertionService jobInsertionService;
    private final JobListComponent jobListComponent;
    private final StatisticsComponent statisticsComponent;

    public DatabaseComponent(JobInsertionService jobInsertionService, JobRetrievalService jobRetrievalService, 
                           ScrapingComponent scrapingComponent, StatisticsComponent statisticsComponent) {
        setText("Database"); // Set the tab title
        
        this.jobInsertionService = jobInsertionService;
        this.scrapingComponent = scrapingComponent;
        this.statisticsComponent = statisticsComponent;
        
        VBox content = new VBox();
        content.setSpacing(20);
        
        // Initialize JobListComponent
        this.jobListComponent = new JobListComponent();
        
        // Create a VBox for buttons
        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
        buttonBox.setPadding(new javafx.geometry.Insets(20));
        
        // Initialize and style buttons
        insertJobsButton = createStyledButton("Insert to DB", "#4CAF50");
        viewJobsButton = createStyledButton("See from DB", "#2196F3");
        emptyDatabaseButton = createStyledButton("Empty Database", "#f44336");
        
        setupButtons();
        buttonBox.getChildren().addAll(insertJobsButton, viewJobsButton, emptyDatabaseButton);
        content.getChildren().addAll(buttonBox, jobListComponent);
        
        setContent(content); // Set the VBox as the tab's content
    }

    private void setupButtons() {
        setupInsertButton();
        setupViewButton();
        setupEmptyDatabaseButton();
    }

    private void setupInsertButton() {
        insertJobsButton.setOnAction(event -> {
            List<Offer> scrapedJobData = scrapingComponent.getScrapedJobData();
            if (scrapedJobData != null && !scrapedJobData.isEmpty()) {
                try {
                    jobInsertionService.insertOffers(scrapedJobData);
                    AlertUtils.showAlert("Success", "Jobs have been inserted into the database successfully!");
                    // After successful insertion, refresh the view
                    refreshJobView();
                } catch (Exception e) {
                    AlertUtils.showAlert("Error", "Error inserting jobs into database: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                AlertUtils.showAlert("Error", "Please scrap the data first!");
            }
        });
    }

    private void setupViewButton() {
        viewJobsButton.setOnAction(event -> {
            refreshJobView();
        });
    }

    private void setupEmptyDatabaseButton() {
        emptyDatabaseButton.setOnAction(event -> {
            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirm Database Empty");
            confirmDialog.setHeaderText("Empty Database");
            confirmDialog.setContentText("Are you sure you want to empty the database? This action cannot be undone.");

            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    jobInsertionService.emptyDatabase();
                    AlertUtils.showAlert("Success", "Database has been emptied successfully!");
                    // Refresh the view to show empty state
                    refreshJobView();
                } catch (Exception e) {
                    AlertUtils.showAlert("Error", "Error emptying database: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private Button createStyledButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;", baseColor));
        button.setMinWidth(150);
        
        // Calculate a slightly darker color for hover effect (reduce brightness by 10%)
        String hoverColor = baseColor.replace("#", "#dd");
        
        button.setOnMouseEntered(e -> 
            button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;", hoverColor))
        );
        button.setOnMouseExited(e -> 
            button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;", baseColor))
        );
        
        return button;
    }

    private void refreshJobView() {
        try {
            JobRetrievalService jobRetrievalService = new JobRetrievalService();
            List<Offer> jobsFromDb = jobRetrievalService.getAllJobs();
            
            // Update the job list view
            jobListComponent.displayJobs(jobsFromDb);
            
            // Update statistics
            statisticsComponent.refreshStatistics(jobsFromDb);
            
            if (jobsFromDb.isEmpty()) {
                AlertUtils.showAlert("Info", "No jobs found in the database.");
            } else {
                AlertUtils.showAlert("Success", "Retrieved " + jobsFromDb.size() + " jobs from database.");
            }
        } catch (Exception e) {
            AlertUtils.showAlert("Error", "Error retrieving jobs from database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
