package com.example.librarymanagemensystem.controllers;


import com.example.librarymanagemensystem.db.BookDAO;
import com.example.librarymanagemensystem.models.Book;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class SearchController {
    @FXML private TextField searchTextField;
    @FXML private TableView<Book> resultsTableView;
    @FXML private TableColumn<Book, Integer> id;
    @FXML private TableColumn<Book, String> title;
    @FXML private TableColumn<Book, String> author;
    @FXML private TableColumn<Book, Boolean> isIssued;

//    private BookDAO bookDAO = new BookDAO();
    private Stack<Book> searchHistory = new Stack<>();
    private Stack<Book> navigationHistory = new Stack<>();

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        isIssued.setCellValueFactory(new PropertyValueFactory<>("isIssued"));
//        List<Book> books = BookDAO.getAllBooks();
//        observableBooks = FXCollections.observableArrayList(books);
//        bookQueue = new LinkedList<>();
//        tableView.setItems(observableBooks);
    }

    @FXML
    public void handleSearch() {
        String title = searchTextField.getText();
        List<Book> results = BookDAO.getAllBooksWithIssued().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        results.forEach(searchHistory::push);
        resultsTableView.getItems().setAll(results);
    }

    @FXML
    public void handleNextSearch() {
        if (!searchHistory.isEmpty()) {
            Book nextBook = searchHistory.pop();
            navigationHistory.push(nextBook);
            resultsTableView.getItems().setAll(nextBook);
        }
    }

    @FXML
    public void handlePreviousSearch() {
        if (!navigationHistory.isEmpty()) {
            Book previousBook = navigationHistory.pop();
            searchHistory.push(previousBook);
            resultsTableView.getItems().setAll(previousBook);
        }
    }

    @FXML
    public void handleMostRecentSearch() {
        if (!searchHistory.isEmpty()) {
            resultsTableView.getItems().setAll(searchHistory.peek());
        }
    }

    @FXML
    public void handleClearHistory() {
        searchHistory.clear();
        navigationHistory.clear();
        resultsTableView.getItems().clear();
    }
}

