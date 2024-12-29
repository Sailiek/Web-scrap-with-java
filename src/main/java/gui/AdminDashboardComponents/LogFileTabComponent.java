package gui.AdminDashboardComponents;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class LogFileTabComponent extends Tab {
    public LogFileTabComponent() {
        // Set the title of the tab
        setText("Log File");

        // Create the VisualizeLogFile component
        VisualizeLogFile VLF = new VisualizeLogFile();

        // Wrap the component in a VBox layout
        VBox content = new VBox(VLF);
        content.setSpacing(10);
        content.setPadding(new Insets(15));

        // Set the content of the tab
        setContent(content);
    }
}
