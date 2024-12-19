package gui.components;

import data.model.Offer;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;

public class JobListComponent extends VBox {
    private ListView<String> jobListView;

    public JobListComponent() {
        jobListView = new ListView<>();
        jobListView.setPrefWidth(600);
        jobListView.setPrefHeight(300);
        jobListView.setMaxWidth(Double.MAX_VALUE);
        jobListView.setVisible(false);
        
        getChildren().add(jobListView);
    }

    public void displayJobs(List<Offer> jobs) {
        jobListView.getItems().clear();
        
        for (Offer offer : jobs) {
            StringBuilder jobDisplay = new StringBuilder();
            jobDisplay.append(String.format("Title: %s\n", offer.getTitre()));
            jobDisplay.append(String.format("Company: %s\n", offer.getNomEntreprise()));
            jobDisplay.append(String.format("Location: %s, %s\n", offer.getVille(), offer.getRegion()));
            jobDisplay.append(String.format("Contract: %s\n", offer.getTypeContrat()));
            jobDisplay.append(String.format("Education: %s\n", offer.getNiveauEtudes()));
            jobDisplay.append(String.format("Experience: %s\n", offer.getExperience()));
            jobDisplay.append(String.format("Skills: %s\n", offer.getCompetencesRequises()));
            jobDisplay.append(String.format("Languages: %s (%s)\n", offer.getLangue(), offer.getNiveauLangue()));
            jobDisplay.append("----------------------------------------\n");
            
            jobListView.getItems().add(jobDisplay.toString());
        }
        
        jobListView.setVisible(true);
    }

    public void hide() {
        jobListView.setVisible(false);
    }

    public ListView<String> getJobListView() {
        return jobListView;
    }
}
