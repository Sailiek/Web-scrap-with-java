package gui.components;

import data.model.Offer;
import javafx.geometry.Insets;
import gui.components.charts.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import service.JobRetrievalService;

import java.util.List;

public class StatisticsComponent extends Tab {

    private final JobRetrievalService jobRetrievalService;
    private List<Offer> offers;
    private VBox chartContainer;
    private ComboBox<String> chartSelector;
    private Label totalJobsLabel;
    
    // Chart components
    private SectorDistributionChart sectorChart;
    private CityDistributionChart cityChart;
    private EducationDistributionChart educationChart;
    private ContractTypeDistributionChart contractTypeChart;

    public StatisticsComponent(JobRetrievalService jobRetrievalService) {
        this.jobRetrievalService = jobRetrievalService;
        
        // Initialize chart components
        sectorChart = new SectorDistributionChart();
        cityChart = new CityDistributionChart();
        educationChart = new EducationDistributionChart();
        contractTypeChart = new ContractTypeDistributionChart();
        
        setText("Statistics");
        setContent(createContent());
        loadInitialData();
    }

    private ScrollPane createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setFillWidth(true);
        VBox.setVgrow(content, Priority.ALWAYS);

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
        
        // Create chart container that scales with window
        chartContainer = new VBox();
        chartContainer.setFillWidth(true);
        chartContainer.setAlignment(javafx.geometry.Pos.CENTER);
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
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        // Make ScrollPane fully responsive
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
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
        if (offers == null || selectedChart == null) {
            System.out.println("Cannot update chart: offers=" + (offers != null ? offers.size() : "null") + ", selectedChart=" + selectedChart);
            return;
        }
        
        try {
            // Ensure UI updates happen on JavaFX Application Thread
            javafx.application.Platform.runLater(() -> {
                try {
                    chartContainer.getChildren().clear();
                    
                    System.out.println("Updating chart: " + selectedChart + " with " + offers.size() + " offers");
                    
                    switch (selectedChart) {
                        case "Distribution of Jobs by Sector" -> {
                            sectorChart.updateChart(offers);
                            chartContainer.getChildren().add(sectorChart);
                        }
                        case "Job Distribution by City" -> {
                            cityChart.updateChart(offers);
                            chartContainer.getChildren().add(cityChart);
                        }
                        case "Job Distribution by Education Level" -> {
                            educationChart.updateChart(offers);
                            chartContainer.getChildren().add(educationChart);
                        }
                        case "Distribution of Jobs by Contract Type" -> {
                            contractTypeChart.updateChart(offers);
                            chartContainer.getChildren().add(contractTypeChart);
                        }
                    }
                    
                    // Force layout update
                    chartContainer.requestLayout();
                } catch (Exception e) {
                    System.err.println("Error updating chart: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.err.println("Error in updateChart: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
