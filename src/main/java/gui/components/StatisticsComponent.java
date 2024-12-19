package gui.components;

import data.model.Offer;
import javafx.geometry.Insets;
import javafx.scene.chart.Chart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import machine_learning.JobStatistics;
import service.JobRetrievalService;

import java.util.List;

public class StatisticsComponent extends Tab {

    private final JobRetrievalService jobRetrievalService;
    private List<Offer> offers;
    private VBox chartContainer;
    private ComboBox<String> chartSelector;
    private Label totalJobsLabel;

    public StatisticsComponent(JobRetrievalService jobRetrievalService) {
        this.jobRetrievalService = jobRetrievalService;
        setText("Statistics");
        setContent(createContent());
        loadInitialData();
    }

    private ScrollPane createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        // Create total jobs label
        totalJobsLabel = new Label();
        
        // Create chart selection ComboBox
        chartSelector = new ComboBox<>();
        chartSelector.getItems().addAll(
            "Distribution of Jobs by Sector",
            "Job Distribution by City",
            "Job Distribution by Education Level",
            "Distribution of Jobs by Contract Type"
        );
        chartSelector.setPromptText("Select a chart to display");
        
        // Create chart container
        chartContainer = new VBox();
        VBox.setVgrow(chartContainer, Priority.ALWAYS);
        
        // Add selection handler
        chartSelector.setOnAction(e -> updateChart(chartSelector.getValue()));

        // Add components to content
        content.getChildren().addAll(
            totalJobsLabel,
            new Label("Select Chart Type:"),
            chartSelector,
            chartContainer
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private void loadInitialData() {
        try {
            offers = jobRetrievalService.getAllJobs();
            updateTotalJobsLabel();
            // Select first option by default
            chartSelector.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTotalJobsLabel() {
        if (offers != null) {
            totalJobsLabel.setText(String.format("Total Jobs in Database: %d", offers.size()));
        } else {
            totalJobsLabel.setText("Total Jobs in Database: 0");
        }
    }

    public void refreshStatistics(List<Offer> newOffers) {
        this.offers = newOffers;
        updateTotalJobsLabel();
        updateChart(chartSelector.getValue());
    }

    private void updateChart(String selectedChart) {
        if (offers == null || selectedChart == null) return;
        
        chartContainer.getChildren().clear();
        
        Chart chart = switch (selectedChart) {
            case "Distribution of Jobs by Sector" -> 
                JobStatistics.createSectorDistributionChart(offers);
            case "Job Distribution by City" -> 
                JobStatistics.createCityDistributionChart(offers);
            case "Job Distribution by Education Level" -> 
                JobStatistics.createEducationDistributionChart(offers);
            case "Distribution of Jobs by Contract Type" ->
                JobStatistics.createContractTypeDistributionChart(offers);
            default -> null;
        };
        
        if (chart != null) {
            // Set preferred size for better visibility
            chart.setPrefWidth(800);
            chart.setPrefHeight(600);
            chartContainer.getChildren().add(chart);
        }
    }
}
