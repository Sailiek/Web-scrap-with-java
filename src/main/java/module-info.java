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

    requires weka.stable;
    requires java.base;
    requires org.apache.commons.csv;

    opens machine_learning;
    exports machine_learning;



    // Export other packages
    opens project.projetjava to javafx.fxml;
    
}
