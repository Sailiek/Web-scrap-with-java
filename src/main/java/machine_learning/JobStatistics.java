package machine_learning;

import data.model.Offer;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobStatistics {

    /**
     * Creates a pie chart showing distribution of jobs by sector
     */
    public static PieChart createSectorDistributionChart(List<Offer> offers) {
        // Group by sector and count
        Map<String, Long> sectorCounts = offers.stream()
            .collect(Collectors.groupingBy(
                Offer::getSecteurActivite,
                Collectors.counting()
            ));

        // Create pie chart
        PieChart chart = new PieChart();
        sectorCounts.forEach((sector, count) -> {
            if (sector != null && !sector.isEmpty()) {
                PieChart.Data data = new PieChart.Data(sector, count);
                chart.getData().add(data);
                
                // Add tooltip showing percentage
                double percentage = (count * 100.0) / offers.size();
                Tooltip tooltip = new Tooltip(String.format("%.1f%% (%d jobs)", percentage, count));
                Tooltip.install(data.getNode(), tooltip);
            }
        });

        chart.setTitle("Distribution of Jobs by Sector");
        return chart;
    }

    /**
     * Creates a pie chart showing distribution of jobs by contract type (CDI, CDD, NA)
     */
    public static PieChart createContractTypeDistributionChart(List<Offer> offers) {
        // Group by contract type and count
        Map<String, Long> contractCounts = offers.stream()
            .map(offer -> normalizeContractType(offer.getTypeContrat()))
            .collect(Collectors.groupingBy(
                type -> type,
                Collectors.counting()
            ));

        // Create pie chart
        PieChart chart = new PieChart();
        contractCounts.forEach((type, count) -> {
            PieChart.Data data = new PieChart.Data(type, count);
            chart.getData().add(data);
            
            // Add tooltip showing percentage
            double percentage = (count * 100.0) / offers.size();
            Tooltip tooltip = new Tooltip(String.format("%.1f%% (%d jobs)", percentage, count));
            Tooltip.install(data.getNode(), tooltip);
        });

        chart.setTitle("Distribution of Jobs by Contract Type");
        return chart;
    }

    /**
     * Creates a bar chart showing job distribution by city
     */
    public static BarChart<String, Number> createCityDistributionChart(List<Offer> offers) {
        // Setup axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("City");
        yAxis.setLabel("Number of Jobs");

        // Create chart
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Job Distribution by City");

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
        return chart;
    }

    /**
     * Creates a bar chart showing distribution by education level
     */
    public static BarChart<String, Number> createEducationDistributionChart(List<Offer> offers) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Education Level");
        yAxis.setLabel("Number of Jobs");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Job Distribution by Education Level");

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
        return chart;
    }

    /**
     * Normalizes education level strings to handle variations
     */
    private static String normalizeEducationLevel(String level) {
        if (level == null || level.isEmpty()) return "";
        
        // Convert to uppercase for case-insensitive comparison
        String normalized = level.toUpperCase().trim();
        
        // Handle BAC+ variations
        if (normalized.contains("BAC+") || normalized.contains("BAC +")) {
            normalized = normalized.replaceAll("\\s+", ""); // Remove spaces
            normalized = normalized.replace("BAC+", "BAC+"); // Standardize format
        }
        
        // Handle specific cases
        return switch (normalized) {
            case "BAC+2", "BAC +2", "BAC+ 2", "BTS", "DUT", "DEUG" -> "BAC+2";
            case "BAC+3", "BAC +3", "BAC+ 3", "LICENCE", "LICENSE" -> "BAC+3";
            case "BAC+4", "BAC +4", "BAC+ 4", "MAITRISE" -> "BAC+4";
            case "BAC+5", "BAC +5", "BAC+ 5", "MASTER", "INGENIEUR" -> "BAC+5";
            case "BAC+8", "BAC +8", "BAC+ 8", "DOCTORAT", "PHD" -> "BAC+8";
            case "BAC", "BACCALAUREAT" -> "BAC";
            default -> normalized;
        };
    }

    /**
     * Normalizes contract type strings to CDI, CDD, or NA
     */
    private static String normalizeContractType(String type) {
        if (type == null || type.isEmpty()) return "NA";
        
        String normalized = type.toUpperCase().trim();
        
        return switch (normalized) {
            case "CDI", "CONTRAT À DURÉE INDÉTERMINÉE" -> "CDI";
            case "CDD", "CONTRAT À DURÉE DÉTERMINÉE" -> "CDD";
            default -> "NA";
        };
    }
}
