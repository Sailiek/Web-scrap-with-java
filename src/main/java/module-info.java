module project.projetjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // Add jsoup dependency
    requires org.jsoup;
    requires java.sql;

    // Open the gui package to javafx.graphics
    opens gui to javafx.graphics;

    // Export other packages
    opens project.projetjava to javafx.fxml;
    exports project.projetjava;
}
