package com.example.librarymanagemensystem.controllers;


public abstract class BaseController {
    protected MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Common methods that can be used by all controllers
}
