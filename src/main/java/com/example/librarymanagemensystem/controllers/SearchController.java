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
    @FXML private TableColumn<Book, String> genre;

    @FXML private TableColumn<Book, Boolean> isIssued;

//    private BookDAO bookDAO = new BookDAO();
    private Stack<String> searchHistory = new Stack<>();
    private Stack<String> navigationHistory = new Stack<>();

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        author.setCellValueFactory(new PropertyValueFactory<>("genre"));

        isIssued.setCellValueFactory(new PropertyValueFactory<>("isIssued"));
//        List<Book> books = BookDAO.getAllBooks();
//        observableBooks = FXCollections.observableArrayList(books);
//        bookQueue = new LinkedList<>();
//        tableView.setItems(observableBooks);
    }

    @FXML
    public void handleSearch() {
        String title = searchTextField.getText();
        List<Book> results = searchDB(title);
//        results.forEach(searchHistory::push);
        searchHistory.push(title);
        resultsTableView.getItems().setAll(results);
    }
    public List<Book> searchDB(String title){
        List<Book> results = BookDAO.getAllBooksWithIssued().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        return results;
    }

    @FXML
    public void handleNextSearch() {
        if (!searchHistory.isEmpty()) {

            String nextSearchTerm = searchHistory.pop();
//            if (nextSearchTerm == ti)
            navigationHistory.push(nextSearchTerm);
            List<Book> results = searchDB(nextSearchTerm);
            resultsTableView.getItems().setAll(results);
        }
    }

    @FXML
    public void handlePreviousSearch() {
        if (!navigationHistory.isEmpty()) {
            String previousTerm = navigationHistory.pop();
            searchHistory.push(previousTerm);
            List<Book> results = searchDB(previousTerm);
            resultsTableView.getItems().setAll(results);
        }
    }
//
//    @FXML
//    public void handleMostRecentSearch() {
//        if (!searchHistory.isEmpty()) {
//            resultsTableView.getItems().setAll(searchHistory.peek());
//        }
//    }

    @FXML
    public void handleClearHistory() {
        searchHistory.clear();
        navigationHistory.clear();
        resultsTableView.getItems().clear();
    }
}

