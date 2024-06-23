package com.example.librarymanagemensystem.controllers;

import com.example.librarymanagemensystem.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.librarymanagemensystem.models.Patron;
import com.example.librarymanagemensystem.DAO.PatronDAO;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PatronController extends  BaseController{
    @FXML
    private TableView<Patron> tableView;

    @FXML
    private TableColumn<Patron, Integer> id;

    @FXML
    private TableColumn<Patron, String> name;

    @FXML
    private TableColumn<Patron, String> email;

    @FXML
    private TableColumn<Patron, String> membershipDate;
    @FXML
    private TextField searchField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker membershipDatePicker;
    private Queue<Patron> PatronQueue;
    private ObservableList<Patron> observablePatron;
    private PatronDAO patronDAO = new PatronDAO();

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        membershipDate.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));

        List<Patron> Patron = patronDAO.getAllPatrons();
        observablePatron = FXCollections.observableArrayList(Patron);
        PatronQueue = new LinkedList<>();
        tableView.setItems(observablePatron);
    }
    @FXML
    private void handleAddPatronButton(ActionEvent event) throws ParseException {
        String name = nameField.getText();
        String email = emailField.getText();
//        String membershipDateString = membershipDateField.getText();
        LocalDate membershipDateLocal = membershipDatePicker.getValue();

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        // Validate input (e.g., ensure fields are not empty)
        if (name.isEmpty() || email.isEmpty() ) {
            // Optionally show an error message to the user
            System.out.println("All fields must be filled!");
            return;
        }
        //conversion causing errors
        Date membershipDate =  Date.from(membershipDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlMembershipDate = new java.sql.Date(membershipDate.getTime());

//        Date membershipDate = dateFormat.parse(membershipDateString);
        // Create a new Patron object
        Patron newPatron = new Patron(nameField.getText(), email, sqlMembershipDate);

        // Add the Patron to the TableView and Queue
        observablePatron.add(newPatron);
        PatronQueue.add(newPatron);

        // Clear form fields after adding
        nameField.clear();
        emailField.clear();
//        membershipDateField.;
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        savePatronToDatabase();
    }

    // Add a method to save Patron to the database
    private void savePatronToDatabase() {
        // Your database save logic here, iterating over PatronQueue
        if(PatronQueue.isEmpty()){
            return;
        }
        if (patronDAO.addPatrons(PatronQueue)){
            showAlert("Success", "Successfully saved to DB", Alert.AlertType.INFORMATION);
            tableView.refresh();
            PatronQueue.clear();
        }

    }

    @FXML
    private void handleReturnPatronButton(ActionEvent event) {
        // Implement return Patron logic
    }

}

