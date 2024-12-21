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

        // Create pie chart with proper layout and size constraints
        PieChart chart = new PieChart();
        chart.setLabelsVisible(true);
        chart.setLabelLineLength(20);
        chart.setStartAngle(90);
        chart.setClockwise(false);
        
        // Set size constraints to ensure the chart scales properly
        chart.setMinSize(800, 600);
        chart.setPrefSize(1000, 800);
        chart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // Ensure the chart uses available space and is visible
        chart.setScaleShape(true);
        chart.setPickOnBounds(true);
        chart.setAnimated(false); // Disable animations to ensure immediate rendering
        chart.setLegendVisible(true);
        chart.setVisible(true);
        chart.setManaged(true);
        
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

        // Create pie chart with proper layout and size constraints
        PieChart chart = new PieChart();
        chart.setLabelsVisible(true);
        chart.setLabelLineLength(20);
        chart.setStartAngle(90);
        chart.setClockwise(false);
        
        // Set size constraints to ensure the chart scales properly
        chart.setMinSize(800, 600);
        chart.setPrefSize(1000, 800);
        chart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // Ensure the chart uses available space and is visible
        chart.setScaleShape(true);
        chart.setPickOnBounds(true);
        chart.setAnimated(false); // Disable animations to ensure immediate rendering
        chart.setLegendVisible(true);
        chart.setVisible(true);
        chart.setManaged(true);
        
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

        // Create chart with proper layout and size constraints
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Job Distribution by City");
        
        // Set size constraints to ensure the chart scales properly
        chart.setMinSize(800, 600);
        chart.setPrefSize(1000, 800);
        chart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // Ensure the chart uses available space and is visible
        chart.setScaleShape(true);
        chart.setPickOnBounds(true);
        chart.setAnimated(false); // Disable animations to ensure immediate rendering
        chart.setLegendVisible(true);
        chart.setVisible(true);
        chart.setManaged(true);

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

        // Create chart with proper layout and size constraints
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Job Distribution by Education Level");
        
        // Set size constraints to ensure the chart scales properly
        chart.setMinSize(800, 600);
        chart.setPrefSize(1000, 800);
        chart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        // Ensure the chart uses available space and is visible
        chart.setScaleShape(true);
        chart.setPickOnBounds(true);
        chart.setAnimated(false); // Disable animations to ensure immediate rendering
        chart.setLegendVisible(true);
        chart.setVisible(true);
        chart.setManaged(true);

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
        if (level == null || level.isEmpty()) return "Non spécifié";
        
        // Print original value for debugging
        System.out.println("Original education level: " + level);
        
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
        String result = switch (normalized) {
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
                // Otherwise categorize as Other and log for debugging
                System.out.println("Unmatched education level: " + normalized);
                yield "Autre";
            }
        };
        
        // Print normalized value for debugging
        System.out.println("Normalized education level: " + result);
        return result;
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
