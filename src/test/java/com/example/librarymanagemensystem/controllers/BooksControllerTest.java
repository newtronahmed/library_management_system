package com.example.librarymanagemensystem.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.example.librarymanagemensystem.Main;
import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.DAO.BookDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
public class BooksControllerTest {

    private BooksController booksController;
    private BookDAO bookDAOMock;
    private TableView<Book> tableView;
    private TextField searchField;
    private Stage stage;

    @Start
    public void start(Stage stage) throws Exception {
        // Load the main page
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    @BeforeEach
    public void setUp() {
        bookDAOMock = mock(BookDAO.class);
        // Assume booksController is set after navigating to books page
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testNavigateToBooksPageAndLoadBooks(FxRobot robot) throws Exception {
        // Click the button that navigates to the books page
        robot.clickOn("#view_books_button"); // Replace with the actual button's fx:id

        // Load the books page
        // Load the books page
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("manage-books.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                booksController = fxmlLoader.getController();
                booksController.setBookDAO(bookDAOMock);
                booksController.setStage(stage);

                // Mock the book list to be returned by the BookDAO
                List<Book> mockBooks = Arrays.asList(new Book("Title1", "Author1","Genre1"), new Book("Title2", "Author2", "Genre2"));
                when(bookDAOMock.getAllBooks()).thenReturn(mockBooks);

                // Call the method under test
                booksController.loadBooks();

                // Verify the books were loaded into the table view
                TableView<Book> tableView = (TableView<Book>) scene.lookup("#tableView"); // Replace with the actual TableView's fx:id
                assertEquals(mockBooks.size(), tableView.getItems().size());
                assertEquals(mockBooks.get(0), tableView.getItems().get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
