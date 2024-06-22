package com.example.librarymanagemensystem.controllers;


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
    public void gotomainpage() throws IOException {
        mainController.loadMainView(stage);
    }


    // Common methods that can be used by all controllers
}
