package com.example.librarymanagemensystem;

import com.example.librarymanagemensystem.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainController = new MainController();
        mainController.loadMainView(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}