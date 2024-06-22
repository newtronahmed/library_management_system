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
    private BookDAO bookDAO = new BookDAO();
    private Stack<String> searchHistory = new Stack<>();
    private String currentSearchTerm = "";
    @FXML

    public void initialize () {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        author.setCellValueFactory(new PropertyValueFactory<>("genre"));
        isIssued.setCellValueFactory(new PropertyValueFactory<>("isIssued"));
    }
    @FXML
    public void handleSearch() {
        String title = searchTextField.getText();
        if (!title.equals(currentSearchTerm)) {
            if (!currentSearchTerm.isEmpty()) {
                searchHistory.push(currentSearchTerm);
            }
            currentSearchTerm = title;
            List<Book> results = searchDB(title);
            resultsTableView.getItems().setAll(results);
        }
    }

    @FXML
    public void handleNextSearch() {
        if (!searchHistory.isEmpty()) {
            String nextSearchTerm = searchHistory.pop();
            if (!currentSearchTerm.isEmpty()) {
                searchHistory.push(currentSearchTerm);
            }
            currentSearchTerm = nextSearchTerm;
            List<Book> results = searchDB(nextSearchTerm);
            resultsTableView.getItems().setAll(results);
            searchTextField.setText(nextSearchTerm);
        }
    }

    @FXML
    public void handlePreviousSearch() {
        if (!searchHistory.isEmpty()) {
            String previousTerm = searchHistory.pop();
            if (!currentSearchTerm.isEmpty()) {
                searchHistory.push(currentSearchTerm);
            }
            currentSearchTerm = previousTerm;
            List<Book> results = searchDB(previousTerm);
            resultsTableView.getItems().setAll(results);
            searchTextField.setText(previousTerm);
        }
    }

    @FXML
    public void handleClearHistory() {
        searchHistory.clear();
        currentSearchTerm = "";
        resultsTableView.getItems().clear();
        searchTextField.clear();
    }
    public List<Book> searchDB(String title){
        return bookDAO.getAllBooksWithIssued().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());

    }
}

