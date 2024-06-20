package com.example.librarymanagemensystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 320);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("manage-books.fxml"));
//        stage.setTitle("Books");
//        stage.setScene(new Scene(root));
//        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}