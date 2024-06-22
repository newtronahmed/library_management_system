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
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Menu");
//        stage.setScene(scene);
//        stage.show();
        MainController mainController = new MainController();
        mainController.loadMainView(stage);
    }



    public static void main(String[] args) {
        launch(args);
    }
}