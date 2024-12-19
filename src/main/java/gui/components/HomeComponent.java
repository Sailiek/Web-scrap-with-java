package gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class HomeComponent extends Tab {
    
    public HomeComponent() {
        setText("Home"); // Set the tab title
        
        VBox content = new VBox();
        content.setSpacing(20);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to Job Scraper");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        Label descriptionLabel = new Label(
            "This application helps you find and analyze job opportunities from various sources.\n\n" +
            "Features:\n" +
            "• Web scraping from multiple job websites\n" +
            "• Database storage and retrieval of job listings\n" +
            "• AI-powered job predictions\n" +
            "• Statistical analysis of job market trends\n\n" +
            "Use the tabs above to navigate through different functionalities."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        descriptionLabel.setFont(Font.font("System", 14));

        content.getChildren().addAll(welcomeLabel, descriptionLabel);
        
        setContent(content); // Set the VBox as the tab's content
    }
}
