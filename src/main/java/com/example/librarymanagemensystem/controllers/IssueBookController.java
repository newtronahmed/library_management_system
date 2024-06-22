package com.example.librarymanagemensystem.controllers;

import com.example.librarymanagemensystem.db.BookDAO;
import com.example.librarymanagemensystem.db.PatronDAO;
import com.example.librarymanagemensystem.db.TransactionDAO;
import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.models.Patron;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class IssueBookController {

    @FXML private ComboBox<Book> bookComboBox;
    @FXML private ComboBox<Patron> patronComboBox;
    @FXML private ListView<Book> pendingIssuesListView;
    @FXML private Button addToQueueButton;
    @FXML private Button saveTransactionsButton;

    private BookDAO bookDAO = new BookDAO();
    private PatronDAO patronDAO = new PatronDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private ObservableList<Patron> patrons = FXCollections.observableArrayList();
    private ObservableList<Book> pendingIssues = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        bookComboBox.setItems(books);
        patronComboBox.setItems(patrons);
        pendingIssuesListView.setItems(pendingIssues);

        books.addAll(bookDAO.getAllBooksWithIssued());
        patrons.addAll(patronDAO.getAllPatrons());

        addToQueueButton.setDisable(true);
        saveTransactionsButton.setDisable(true);

        bookComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                addToQueueButton.setDisable(newVal == null || patronComboBox.getValue() == null));

        patronComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                addToQueueButton.setDisable(newVal == null || bookComboBox.getValue() == null));
    }

    @FXML
    private void handleAddToQueue(ActionEvent event) {
        Book selectedBook = bookComboBox.getValue();
        if (selectedBook != null && !selectedBook.getIsIssued() && !pendingIssues.contains(selectedBook)) {
            pendingIssues.add(selectedBook);
            bookComboBox.getSelectionModel().clearSelection();
            saveTransactionsButton.setDisable(false);
        }
    }

    @FXML
    private void handleSaveTransactions(ActionEvent event) {
        Patron selectedPatron = patronComboBox.getValue();
        if (selectedPatron == null || pendingIssues.isEmpty()) {
            showAlert("Error", "Please select a patron and add books to the queue.");
            return;
        }

        List<Integer> bookIds = pendingIssues.stream()
                .map(Book::getId)
                .collect(Collectors.toList());

        boolean success = transactionDAO.addTransactions(bookIds, selectedPatron.getId());
        if (success) {
            pendingIssues.forEach(book -> book.setIsIssued(true));
            showAlert("Success", "Books issued successfully.");
            clearSelections();
        } else {
            showAlert("Error", "Failed to issue books. Please try again.");
        }
    }

    private void clearSelections() {
        pendingIssues.clear();
        patronComboBox.getSelectionModel().clearSelection();
        bookComboBox.getSelectionModel().clearSelection();
        saveTransactionsButton.setDisable(true);
        addToQueueButton.setDisable(true);
        books.removeIf(Book::getIsIssued);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}