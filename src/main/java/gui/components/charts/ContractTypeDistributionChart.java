package gui.components.charts;

import data.model.Offer;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContractTypeDistributionChart extends VBox {
    private PieChart chart;

    public ContractTypeDistributionChart() {
        // Initialize chart with proper layout and size constraints
        chart = new PieChart();
        setupChart();
        getChildren().add(chart);
    }

    private void setupChart() {
        chart.setLabelsVisible(true);
        chart.setLabelLineLength(20);
        chart.setStartAngle(90);
        chart.setClockwise(false);
        
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
        
        chart.setTitle("Distribution of Jobs by Contract Type");
    }

    public void updateChart(List<Offer> offers) {
        if (offers == null || offers.isEmpty()) return;

        // Clear previous data
        chart.getData().clear();

        // Group by contract type and count
        Map<String, Long> contractCounts = offers.stream()
            .map(offer -> normalizeContractType(offer.getTypeContrat()))
            .collect(Collectors.groupingBy(
                type -> type,
                Collectors.counting()
            ));

        contractCounts.forEach((type, count) -> {
            PieChart.Data data = new PieChart.Data(type, count);
            chart.getData().add(data);
            
            // Add tooltip showing percentage
            double percentage = (count * 100.0) / offers.size();
            Tooltip tooltip = new Tooltip(String.format("%.1f%% (%d jobs)", percentage, count));
            Tooltip.install(data.getNode(), tooltip);
        });
    }

    private String normalizeContractType(String type) {
        if (type == null || type.isEmpty()) return "NA";
        
        String normalized = type.toUpperCase().trim();
        
        return switch (normalized) {
            case "CDI", "CONTRAT À DURÉE INDÉTERMINÉE" -> "CDI";
            case "CDD", "CONTRAT À DURÉE DÉTERMINÉE" -> "CDD";
            default -> "NA";
        };
    }
}
