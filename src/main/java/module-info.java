module com.example.librarymanagemensystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires mysql.connector.j;

    opens com.example.librarymanagemensystem to javafx.fxml;
    exports com.example.librarymanagemensystem;
    exports com.example.librarymanagemensystem.controllers;
    exports com.example.librarymanagemensystem.models;

    opens com.example.librarymanagemensystem.controllers to javafx.fxml;
}