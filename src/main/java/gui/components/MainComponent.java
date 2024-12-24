package gui.components;

import javafx.scene.control.TabPane;
import service.JobInsertionService;
import service.JobRetrievalService;
import service.ScraperService;

public class MainComponent extends TabPane {
    public MainComponent(JobInsertionService jobInsertionService, 
                        JobRetrievalService jobRetrievalService,
                        ScraperService scraperService) {
        
        // Create components
        HomeComponent homeComponent = new HomeComponent();
        ScrapingComponent scrapingComponent = new ScrapingComponent(scraperService, jobInsertionService);
        StatisticsComponent statisticsComponent = new StatisticsComponent(jobRetrievalService);
        DatabaseComponent databaseComponent = new DatabaseComponent(
            jobInsertionService, 
            jobRetrievalService, 
            scrapingComponent,
            statisticsComponent
        );
        MachineLearningComponent machineLearningComponent = new MachineLearningComponent(jobRetrievalService);
        
        // Add tabs
        getTabs().addAll(
            homeComponent,
            scrapingComponent,
            databaseComponent,
            machineLearningComponent,
            statisticsComponent
        );
    }
}
