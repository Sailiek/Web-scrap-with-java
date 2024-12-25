package gui.AdminDashboardComponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import service.AdminStatisticsService;

import java.util.Map;

public class StatisticsComponent extends VBox {
    private final AdminStatisticsService  statisticsService;

    public StatisticsComponent(AdminStatisticsService statisticsService) {
        this.statisticsService = statisticsService;

        // Add charts to the component
        getChildren().addAll(
                createTotalUsersChart(),
                createFieldOfWorkChart(),
                createAgeDistributionChart()
        );
    }

    private PieChart createTotalUsersChart() {
        int totalUsers = statisticsService.getTotalNumberOfUsers();
        PieChart chart = new PieChart(FXCollections.observableArrayList(
                new PieChart.Data("Total Users", totalUsers)
        ));
        chart.setTitle("Total Number of Users");
        return chart;
    }

    private PieChart createFieldOfWorkChart() {
        Map<String, Long> fieldOfWorkData = statisticsService.getTop15FieldsOfWork();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        fieldOfWorkData.forEach((field, count) ->
                data.add(new PieChart.Data(field, count))
        );

        PieChart chart = new PieChart(data);
        chart.setTitle("Top 15 Fields of Work");
        return chart;
    }

    private BarChart<String, Number> createAgeDistributionChart() {
        Map<String, Long> ageDistributionData = statisticsService.getAgeDistribution();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Age Group");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Age Distribution");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ageDistributionData.forEach((ageGroup, count) ->
                series.getData().add(new XYChart.Data<>(ageGroup, count))
        );

        chart.getData().add(series);
        return chart;
    }
}

