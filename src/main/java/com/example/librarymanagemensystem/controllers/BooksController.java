package com.example.librarymanagemensystem.controllers;
import com.example.librarymanagemensystem.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.DAO.BookDAO;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BooksController extends BaseController {
    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, Integer> id;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> genre;
    @FXML
    private TableColumn<Book, String> isIssued;
    @FXML
    private TableColumn<Book, Integer> transactionCount;
    @FXML
    private TextField searchField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField genreField;
    private Queue<Book> bookQueue;
    private ObservableList<Book> observableBooks;
    private BookDAO bookDAO = new BookDAO();

    @FXML
    public void initialize() {
        initializeTable();
        List<Book> books = bookDAO.getAllBooksWithIssued();
        observableBooks = FXCollections.observableArrayList(books);
        bookQueue = new LinkedList<>();
        tableView.setItems(observableBooks);
    }

    @FXML
    private void handleAddBookButton(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();

        // Validate input (e.g., ensure fields are not empty)
        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            // Optionally show an error message to the user
            System.out.println("All fields must be filled!");
            return;
        }

        // Create a new Book object
        Book newBook = new Book(title, author, genre);

        // Add the book to the TableView and Queue
        observableBooks.add(newBook);
//        observableBooks.remove();
        bookQueue.add(newBook);
        resetFields();

    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
         saveBooksToDatabase();
    }

    // method to save books to the database
    private void saveBooksToDatabase() {
        if(bookQueue.isEmpty()){
            return;
        }
            if (bookDAO.addBooks(bookQueue)){
                showErrorAlert("Success", "Successfully saved to DB");
                tableView.refresh();
                bookQueue.clear();
            }

    }

    private void resetFields(){
        // Clear form fields after adding
        titleField.clear();
        authorField.clear();
        genreField.clear();
    }

    @FXML
    private void handleReturnBookButton(ActionEvent event) {
        // Implement return book logic
    }
    private void initializeTable(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        isIssued.setCellValueFactory(new PropertyValueFactory<>("isIssued"));
        transactionCount.setCellValueFactory(new PropertyValueFactory<>("transactionCount"));

    }

}
