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
import com.example.librarymanagemensystem.db.PatronDAO;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PatronController {
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

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        membershipDate.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));

        List<Patron> Patron = PatronDAO.getAllPatrons();
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
//        observablePatron.remove();
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
//    private void savePatronToDatabase() {
//        // Your database save logic here, iterating over PatronQueue
//        while (!PatronQueue.isEmpty()) {
//            Patron Patron = PatronQueue.poll();  // Retrieves and removes the head of the queue
//            // Save each Patron to the database
//            // Example:
//            // database.savePatron(Patron);
//            PatronDAO.addPatron(Patron);
//            System.out.println("Saving Patron: " + Patron.getname() + " by " + Patron.getemail());
//        }
//    }
    // Add a method to save Patron to the database
    private void savePatronToDatabase() {
        // Your database save logic here, iterating over PatronQueue
        if(PatronQueue.isEmpty()){
            return;
        }
//        while (!PatronQueue.isEmpty()) {
//            Patron Patron = PatronQueue.poll();  // Retrieves and removes the head of the queue
        // Sa each Patron to the database
        // Example:
        // database.savePatron(Patron);
        if (PatronDAO.addPatrons(PatronQueue)){
            showErrorAlert("Success", "Successfully saved to DB");
            tableView.refresh();
            PatronQueue.clear();
        }

    }


    // Handle other button actions (Search, Return Patron, etc.)
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
    private void handleReturnPatronButton(ActionEvent event) {
        // Implement return Patron logic
    }
    private void showErrorAlert(String name, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(name);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

