package com.example.librarymanagemensystem.controllers;

import com.example.librarymanagemensystem.DAO.ReservationDAO;
import com.example.librarymanagemensystem.models.Reservation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ReservationController extends  BaseController{

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField patronIdField;

    @FXML
    private DatePicker reservationDatePicker;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Button reserveButton;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> idColumn;

    @FXML
    private TableColumn<Reservation, Integer> bookIdColumn;

    @FXML
    private TableColumn<Reservation, Integer> patronIdColumn;

    @FXML
    private TableColumn<Reservation, Date> reservationDateColumn;

    @FXML
    private TableColumn<Reservation, String> statusColumn;

    private ReservationDAO reservationDAO;

    public ReservationController() {
        reservationDAO = new ReservationDAO();
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusComboBox.setItems(FXCollections.observableArrayList("ACTIVE", "INACTIVE"));

        loadActiveReservations();
    }

    @FXML
    private void handleReserveBook() {
        String bookIdText = bookIdField.getText();
        String patronIdText = patronIdField.getText();
        LocalDate reservationDate = reservationDatePicker.getValue();
        String status = statusComboBox.getValue();

        if (bookIdText.isEmpty() || patronIdText.isEmpty() || reservationDate == null || status == null) {
            statusLabel.setText("All fields must be filled");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdText);
            int patronId = Integer.parseInt(patronIdText);
            Date date = java.sql.Date.valueOf(reservationDate);

            Reservation reservation = new Reservation(bookId, patronId, date, status);
            reservationDAO.addReservation(reservation);

            statusLabel.setText("Reservation added successfully");
            clearFields();
            loadActiveReservations();
        } catch (NumberFormatException e) {
            statusLabel.setText("Book ID and Patron ID must be numbers");
        }
    }

    private void clearFields() {
        bookIdField.clear();
        patronIdField.clear();
        reservationDatePicker.setValue(null);
        statusComboBox.setValue(null);
        statusLabel.setText("");
    }

    private void loadActiveReservations() {
        List<Reservation> reservations = reservationDAO.getActiveReservationsForBook(-1); // Pass -1 to get all active reservations
        reservationTable.setItems(FXCollections.observableArrayList(reservations));
    }
}
