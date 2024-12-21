package gui.components.charts;

import data.model.Offer;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CityDistributionChart extends VBox {
    private BarChart<String, Number> chart;

    public CityDistributionChart() {
        setupChart();
        getChildren().add(chart);
    }

    private void setupChart() {
        // Setup axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("City");
        yAxis.setLabel("Number of Jobs");

        // Create chart
        chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Job Distribution by City");
        
        // Set size constraints
        chart.setMinSize(800, 600);
        chart.setPrefSize(1000, 800);
        chart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // Ensure visibility
        chart.setScaleShape(true);
        chart.setPickOnBounds(true);
        chart.setAnimated(false);
        chart.setLegendVisible(true);
        chart.setVisible(true);
        chart.setManaged(true);
    }

    public void updateChart(List<Offer> offers) {
        if (offers == null || offers.isEmpty()) return;

        // Clear previous data
        chart.getData().clear();

        // Group by city and count
        Map<String, Long> cityCounts = offers.stream()
            .collect(Collectors.groupingBy(
                Offer::getVille,
                Collectors.counting()
            ));

        // Create data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Jobs");
        cityCounts.forEach((city, count) -> {
            if (city != null && !city.isEmpty()) {
                XYChart.Data<String, Number> data = new XYChart.Data<>(city, count);
                series.getData().add(data);
                
                // Add tooltip showing count
                data.nodeProperty().addListener((obs, old, node) -> {
                    if (node != null) {
                        Tooltip tooltip = new Tooltip(count + " jobs");
                        Tooltip.install(node, tooltip);
                    }
                });
            }
        });

        chart.getData().add(series);
    }
}
