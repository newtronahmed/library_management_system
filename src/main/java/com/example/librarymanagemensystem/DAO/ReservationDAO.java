package com.example.librarymanagemensystem.DAO;

import com.example.librarymanagemensystem.db.DatabaseConnection;
import com.example.librarymanagemensystem.models.Reservation;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO Reservations (bookID, patronID, reservationDate, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, reservation.getBookId());
            statement.setInt(2, reservation.getPatronId());
            statement.setDate(3, new java.sql.Date(reservation.getReservationDate().getTime()));
            statement.setString(4, reservation.getStatus());
            statement.executeUpdate();

            // Get generated ID and set it to the reservation object
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    public List<Reservation> getActiveReservationsForBook(int bookID) {
        String query = "SELECT * FROM Reservations WHERE bookID = ? AND status = 'ACTIVE' ORDER BY reservationDate";
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reservation reservation = new Reservation(
                            resultSet.getInt("id"),
                            resultSet.getInt("bookID"),
                            resultSet.getInt("patronID"),
                            resultSet.getDate("reservationDate"),
                            resultSet.getString("status")
                    );
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return reservations;
    }

    public void updateReservationStatus(int reservationID, String newStatus) {
        String query = "UPDATE Reservations SET status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newStatus);
            statement.setInt(2, reservationID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }


    // Other methods as needed
}
