package gui.AdminDashboardComponents;


import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import service.AdminStatisticsService;

public class StatisticsTabComponent extends Tab {
    public StatisticsTabComponent(AdminStatisticsService statisticsService) {
        // Set the title of the tab
        setText("Statistics");

        // Create the StatisticsComponent
        StatisticsComponent statisticsComponent = new StatisticsComponent(statisticsService);

        // Wrap the component in a layout
        VBox content = new VBox(statisticsComponent);
        content.setSpacing(10);


        // Set the content of the tab
        setContent(content);
    }
}
