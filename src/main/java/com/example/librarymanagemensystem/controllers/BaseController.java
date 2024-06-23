package com.example.librarymanagemensystem.controllers;


import com.example.librarymanagemensystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    protected MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void goToMainPage() throws IOException {
        mainController.loadMainView(stage);
    }
    protected void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleSearchButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        SearchController searchController = fxmlLoader.getController();
        searchController.setMainController(this.mainController);
        searchController.setStage(stage);
        stage.setTitle("Search");
        stage.setScene(scene);
        stage.show();
    }


    // Common methods that can be used by all controllers
}
