package gui.components.charts;

import data.model.Offer;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import java.util.*;
import java.util.stream.Collectors;

public class SectorDistributionChart extends VBox {
    private static final int TOP_SECTORS_COUNT = 15;
    private final PieChart chart;

    public SectorDistributionChart() {
        chart = new PieChart();
        setupChart();
        getChildren().add(chart);
    }

    private void setupChart() {
        chart.setLabelsVisible(true);
        chart.setLabelLineLength(15);
        chart.setStartAngle(90);
        chart.setClockwise(false);
        chart.setPrefSize(1200, 1200);
        chart.setStyle("-fx-pie-label-visible: true; -fx-pie-title-side: top; -fx-legend-side: right; -fx-pie-chart-radius: 300;");
        chart.setAnimated(false);
        chart.setLegendVisible(true);
        setAlignment(javafx.geometry.Pos.CENTER);
        chart.setTitle("Distribution of Jobs by Sector");
    }

    private List<Map.Entry<String, Long>> processTopSectors(List<Offer> offers) {
        Map<String, Long> sectorCounts = offers.stream()
                .collect(Collectors.groupingBy(
                        Offer::getSecteurActivite,
                        Collectors.counting()
                ));

        List<Map.Entry<String, Long>> sortedSectors = sectorCounts.entrySet().stream()
                .filter(entry -> entry.getKey() != null && !entry.getKey().isEmpty())
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());

        if (sortedSectors.size() <= TOP_SECTORS_COUNT) {
            return sortedSectors;
        }

        long othersCount = sortedSectors.stream()
                .skip(TOP_SECTORS_COUNT)
                .mapToLong(Map.Entry::getValue)
                .sum();

        List<Map.Entry<String, Long>> topSectors = new ArrayList<>(
                sortedSectors.subList(0, TOP_SECTORS_COUNT)
        );
        topSectors.add(new AbstractMap.SimpleEntry<>("Others", othersCount));
        return topSectors;
    }

    public void updateChart(List<Offer> offers) {
        if (offers == null || offers.isEmpty()) return;

        chart.getData().clear();
        List<Map.Entry<String, Long>> sectors = processTopSectors(offers);

        sectors.forEach(entry -> {
            PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
            chart.getData().add(data);

            double percentage = (entry.getValue() * 100.0) / offers.size();
            Tooltip tooltip = new Tooltip(String.format("%.1f%% (%d jobs)", percentage, entry.getValue()));
            Tooltip.install(data.getNode(), tooltip);
        });
    }
}
