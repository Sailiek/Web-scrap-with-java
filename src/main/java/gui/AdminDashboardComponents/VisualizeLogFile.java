package gui.AdminDashboardComponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class VisualizeLogFile extends VBox {

    public VisualizeLogFile() {
        // Add the log file content to this VBox
        getChildren().add(showLogfile());
    }

    private Node showLogfile() {
        // Create a VBox to hold the log content
        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(15));
        content.setAlignment(Pos.CENTER);

        // Add a title to the log display
        Label title = new Label("Log File");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));

        // File path for the log file
        String filePath = "logfile.txt";

        // Read the log file content
        StringBuilder logContent = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                logContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display the log content or a message if the log is empty
        Label logLabel;
        if (logContent.length() > 0) {
            logLabel = new Label(logContent.toString());
        } else {
            logLabel = new Label("Log File is empty");
        }

        logLabel.setWrapText(true);
        logLabel.setTextAlignment(TextAlignment.LEFT);
        logLabel.setFont(Font.font("System", 14));

        // Wrap the logLabel in a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(logLabel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        // Set a preferred size for the ScrollPane
        scrollPane.setPrefHeight(400); // Adjust as needed
        scrollPane.setPrefWidth(600); // Adjust as needed

        // Add the title and scrollable log content to the VBox
        content.getChildren().addAll(title, scrollPane);

        return content;
    }
}
