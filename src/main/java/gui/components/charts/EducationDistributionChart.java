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

public class EducationDistributionChart extends VBox {
    private BarChart<String, Number> chart;

    public EducationDistributionChart() {
        setupChart();
        getChildren().add(chart);
    }

    private void setupChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Education Level");
        yAxis.setLabel("Number of Jobs");

        // Create chart
        chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Job Distribution by Education Level");
        
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

        // Normalize education levels before grouping
        Map<String, Long> educationCounts = offers.stream()
            .map(offer -> normalizeEducationLevel(offer.getNiveauEtudes()))
            .filter(edu -> edu != null && !edu.isEmpty())
            .collect(Collectors.groupingBy(
                edu -> edu,
                Collectors.counting()
            ));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Jobs");
        educationCounts.forEach((edu, count) -> {
            XYChart.Data<String, Number> data = new XYChart.Data<>(edu, count);
            series.getData().add(data);
            
            // Add tooltip showing count
            data.nodeProperty().addListener((obs, old, node) -> {
                if (node != null) {
                    Tooltip tooltip = new Tooltip(count + " jobs");
                    Tooltip.install(node, tooltip);
                }
            });
        });

        chart.getData().add(series);
    }

    private String normalizeEducationLevel(String level) {
        if (level == null || level.isEmpty()) return "Non spécifié";
        
        // Convert to uppercase for case-insensitive comparison and normalize accents
        String normalized = level.toUpperCase()
            .trim()
            .replace('É', 'E')
            .replace('È', 'E')
            .replace('Ê', 'E')
            .replace('À', 'A')
            .replace('Â', 'A')
            .replace('Î', 'I')
            .replace('Ô', 'O')
            .replace('Û', 'U');
        
        // Handle BAC+ variations with any number
        if (normalized.matches(".*BAC\\s*\\+\\s*\\d.*")) {
            normalized = normalized.replaceAll("\\s+", ""); // Remove all spaces
            normalized = normalized.replaceAll("BAC\\s*\\+\\s*(\\d+).*", "BAC+$1"); // Standardize BAC+ format
        }
        
        // Handle specific cases
        return switch (normalized) {
            // BAC+2 variations
            case "BAC+2", "BTS", "DUT", "DEUG", "TECHNICIEN SPECIALISE", "DIPLOME TECHNICIEN SPECIALISE",
                 "TECHNICIEN SUPERIEUR", "DIPLOME DE TECHNICIEN SUPERIEUR", "DTS", "DEUST" -> "BAC+2";
            
            // BAC+3 variations
            case "BAC+3", "LICENCE", "LICENSE", "BACHELOR", "DIPLOME DE LICENCE",
                 "LICENCE PROFESSIONNELLE", "LICENCE PRO" -> "BAC+3";
            
            // BAC+4 variations
            case "BAC+4", "MAITRISE", "MASTER 1", "M1", "DIPLOME DE MAITRISE" -> "BAC+4";
            
            // BAC+5 variations
            case "BAC+5", "MASTER", "MASTER 2", "M2", "INGENIEUR", "INGENIEUR D'ETAT",
                 "DIPLOME D'INGENIEUR", "DIPLOME D'INGENIEUR D'ETAT", "DIPLOME DE MASTER",
                 "MASTER SPECIALISE", "MBA" -> "BAC+5";
            
            // BAC+8 variations
            case "BAC+8", "DOCTORAT", "PHD", "DOCTEUR", "DOCTORATE", "THESE DE DOCTORAT" -> "BAC+8";
            
            // BAC variations
            case "BAC", "BACCALAUREAT", "NIVEAU BAC" -> "BAC";
            
            // Professional formation variations
            case "FORMATION PROFESSIONNELLE", "FORMATION", "QUALIFICATION",
                 "FORMATION QUALIFIANTE", "DIPLOME DE FORMATION PROFESSIONNELLE" -> "Formation Professionnelle";
            
            default -> {
                // If it contains BAC+ with a number, use that standardized format
                if (normalized.matches(".*BAC\\+\\d.*")) {
                    yield normalized.replaceAll(".*?(BAC\\+\\d).*", "$1");
                }
                yield "Autre";
            }
        };
    }
}
