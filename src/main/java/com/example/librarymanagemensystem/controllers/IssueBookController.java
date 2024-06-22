package com.example.librarymanagemensystem.controllers;


import com.example.librarymanagemensystem.db.BookDAO;
import com.example.librarymanagemensystem.db.PatronDAO;
import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.models.Patron;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;

public class IssueBookController {

    @FXML private ComboBox<Book> bookComboBox;
    @FXML private ComboBox<Patron> patronComboBox;

    private BookDAO bookDAO = new BookDAO();
    private PatronDAO patronDAO = new PatronDAO();
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private ObservableList<Patron> patrons = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize comboboxes with data (books and patrons)
        bookComboBox.setItems(books);
        patronComboBox.setItems(patrons);

        // Example data initialization (replace with actual data retrieval logic)
        books.addAll(bookDAO.getAllBooks());
        patrons.addAll(patronDAO.getAllPatrons());
    }



    @FXML
    private void handleIssueBook(ActionEvent event) {
        Book selectedBook = bookComboBox.getValue();
        Patron selectedPatron = patronComboBox.getValue();

        if (selectedBook == null || selectedPatron == null) {
            showAlert("Error", "Please select both a book and a patron.");
            return;
        }

        // Example: Issue the selected book to the selected patron
        boolean issuedSuccessfully = issueBookToPatron(selectedBook, selectedPatron);

        if (issuedSuccessfully) {
            showAlert("Success", "Book issued successfully.");
            // Optionally, update UI or navigate to another page
        } else {
            showAlert("Error", "Failed to issue book. Please try again.");
        }
    }

    private boolean issueBookToPatron(Book book, Patron patron) {
        // Implement logic to issue the book to the patron
        // Example:
//        return bookService.issueBook(book, patron);
return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

