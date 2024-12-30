package gui.components;

import data.model.Job;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import machine_learning.EnhancedJobPrediction;
import service.JobRetrievalService;
import gui.utils.AlertUtils;

public class PredictionComponent extends Tab {
    private final Label predictionLabel;
    private final TextField jobTitleInput;
    private final TextArea predictionOutput;
    private final Button predictButton;
    private final JobRetrievalService jobRetrievalService;

    public PredictionComponent(JobRetrievalService jobRetrievalService) {
        setText("Prediction"); // Set the tab title

        this.jobRetrievalService = jobRetrievalService;

        VBox content = new VBox();
        content.setSpacing(10);

        predictionLabel = new Label("Give our model a job title and it will predict the experience you need:");
        jobTitleInput = new TextField();
        jobTitleInput.setPromptText("Enter job title (e.g., Product Manager)");
        predictionOutput = new TextArea();
        predictionOutput.setEditable(false);
        predictionOutput.setPrefRowCount(2);
        predictButton = new Button("Predict");

        setupPredictButton();

        content.getChildren().addAll(predictionLabel, jobTitleInput, predictButton, predictionOutput);

        setContent(content); // Set the VBox as the tab's content
    }

    private void setupPredictButton() {
        predictButton.setOnAction(event -> {
            String jobTitle = jobTitleInput.getText().trim();
            if (!jobTitle.isEmpty()) {
                try {
                    Job predictedJob = EnhancedJobPrediction.predictJobDetails(jobTitle);
                    if (predictedJob != null) {
                        String prediction = String.format(

                                "Predicted Study Level: %s\n" +
                                        "Predicted Experience Level: %s",


                                predictedJob.getEducationLevel(),
                                predictedJob.getExperience()
                        );
                        predictionOutput.setText(prediction);
                    } else {
                        predictionOutput.setText("No matching job found.");
                    }
                } catch (Exception e) {
                    AlertUtils.showAlert("Error", "Error making prediction: " + e.getMessage());
                }
            } else {
                AlertUtils.showAlert("Error", "Please enter a job title!");
            }
        });
    }
}
