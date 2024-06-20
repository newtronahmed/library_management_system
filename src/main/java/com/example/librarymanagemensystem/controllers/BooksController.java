package com.example.librarymanagemensystem.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.db.BookDAO;
import java.util.List;

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

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));

        List<Book> books = BookDAO.getAllBooks();
        ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);
        tableView.setItems(observableBooks);
    }
}
