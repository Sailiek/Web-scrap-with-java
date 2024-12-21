package gui.components;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import service.JobRetrievalService;

public class MachineLearningComponent extends Tab {
    private final TabPane subTabPane;
    private final PredictionComponent predictionComponent;
    private final RecommendationComponent recommendationComponent;

    public MachineLearningComponent(JobRetrievalService jobRetrievalService) {
        setText("Machine Learning");
        
        // Create sub-tab pane
        subTabPane = new TabPane();
        
        // Create sub-components
        predictionComponent = new PredictionComponent(jobRetrievalService);
        recommendationComponent = new RecommendationComponent(jobRetrievalService);
        
        // Add sub-tabs
        subTabPane.getTabs().addAll(
            predictionComponent,
            recommendationComponent
        );
        
        setContent(subTabPane);
    }
}
