package com.example.librarymanagemensystem.controllers;

import com.example.librarymanagemensystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button view_books_button;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void handleViewBookButton(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("manage-books.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("manage-books.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("manage-books2.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
//        stage.setScene(new Scene(root));
        stage.setTitle("Books");
        stage.setScene(scene);
        stage.show();
//        stage.show();
//        stage.setTitle("Books");
//        stage.setScene(new Scene(root));
//        stage.show();
    }

}