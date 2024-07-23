package com.example.librarymanagemensystem.controllers;

import com.example.librarymanagemensystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class MainControllerTest {

    private MainController mainController;
    private Stage stage;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainController = fxmlLoader.getController();
        mainController.loadMainView(stage);
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    @BeforeEach
    public void setUp() {
        // Set up necessary preconditions or mocks
    }

    @Test
    public void testHandleViewBookButton(FxRobot robot) {
        // Click the view books button
        robot.clickOn("#view_books_button");

        // Check that the stage's title has changed to "Books"
        assertEquals("Books", stage.getTitle());
    }

    @Test
    public void testHandleManagePatronsButton(FxRobot robot) {
        // Click the manage patrons button
        robot.clickOn("#manage_books_button");

        // Check that the stage's title has changed to "Patrons"
        assertEquals("Patrons", stage.getTitle());
    }

    @Test
    public void testHandleIssueBookButton(FxRobot robot) {
        // Click the issue book button
        robot.clickOn("#issue_book_button");

        // Check that the stage's title has changed to "Issue Books"
        assertEquals("Issue Books", stage.getTitle());
    }

    @Test
    public void testHandleReserveBookButton(FxRobot robot) {
        // Click the reserve book button
        robot.clickOn("#reserve_book_button");

        // Check that the stage's title has changed to "Reservations"
        assertEquals("Reservations", stage.getTitle());
    }
}
