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
import com.example.librarymanagemensystem.db.BookDAO;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BooksController {
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
    private TextField searchField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField genreField;
    private Queue<Book> bookQueue;
    private ObservableList<Book> observableBooks;

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
//        genre.setCellValueFactory(new PropertyValueFactory<>("isIssued"));

        List<Book> books = BookDAO.getAllBooks();
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

        // Clear form fields after adding
        titleField.clear();
        authorField.clear();
        genreField.clear();
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
         saveBooksToDatabase();
    }

    // Add a method to save books to the database
//    private void saveBooksToDatabase() {
//        // Your database save logic here, iterating over bookQueue
//        while (!bookQueue.isEmpty()) {
//            Book book = bookQueue.poll();  // Retrieves and removes the head of the queue
//            // Save each book to the database
//            // Example:
//            // database.saveBook(book);
//            BookDAO.addBook(book);
//            System.out.println("Saving book: " + book.getTitle() + " by " + book.getAuthor());
//        }
//    }
    // Add a method to save books to the database
    private void saveBooksToDatabase() {
        // Your database save logic here, iterating over bookQueue
        if(bookQueue.isEmpty()){
            return;
        }
//        while (!bookQueue.isEmpty()) {
//            Book book = bookQueue.poll();  // Retrieves and removes the head of the queue
            // Sa each book to the database
            // Example:
            // database.saveBook(book);
            if (BookDAO.addBooks(bookQueue)){
                showErrorAlert("Success", "Successfully saved to DB");
                tableView.refresh();
                bookQueue.clear();
            }

    }


    // Handle other button actions (Search, Return Book, etc.)
    @FXML
    private void handleSearchButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
//        stage.setScene(new Scene(root));
        stage.setTitle("Search");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleReturnBookButton(ActionEvent event) {
        // Implement return book logic
    }
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}