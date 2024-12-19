package gui.components;

import data.model.Offer;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import service.ScraperService;
import service.JobInsertionService;
import gui.utils.AlertUtils;

import java.util.List;

public class ScrapingComponent extends Tab {
    private final Button scrapButton;
    private List<Offer> scrapedJobData;
    private final ScraperService scraperService;
    private final JobInsertionService jobInsertionService;

    public ScrapingComponent(ScraperService scraperService, JobInsertionService jobInsertionService) {
        setText("Scraping"); // Set the tab title
        
        this.scraperService = scraperService;
        this.jobInsertionService = jobInsertionService;
        
        VBox content = new VBox();
        content.setSpacing(10);
        
        scrapButton = new Button("Scrap");
        setupScrapButton();
        content.getChildren().add(scrapButton);
        
        setContent(content); // Set the VBox as the tab's content
    }

    private void setupScrapButton() {
        scrapButton.setOnAction(event -> {
            try {
                scrapedJobData = scraperService.getAllJobData();
                AlertUtils.showAlert("Success", "Scraping completed successfully! Found " + scrapedJobData.size() + " jobs.");
            } catch (Exception e) {
                AlertUtils.showAlert("Error", "Error during scraping: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public List<Offer> getScrapedJobData() {
        return scrapedJobData;
    }
}
