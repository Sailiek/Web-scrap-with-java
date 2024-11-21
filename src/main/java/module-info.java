module org.example.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.jsoup;

    // Open the packages to javafx.fxml so JavaFX can access the FXML files
    opens gui to javafx.fxml;
    opens service to javafx.fxml;
    opens scrapper to javafx.fxml;

    // Export the packages that contain the public classes you want to use
    exports gui;
    exports service;
    exports scrapper;
}
