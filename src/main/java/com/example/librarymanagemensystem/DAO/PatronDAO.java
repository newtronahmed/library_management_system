package com.example.librarymanagemensystem.DAO;

import com.example.librarymanagemensystem.db.DatabaseConnection;
import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.models.Patron;
import com.example.librarymanagemensystem.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class PatronDAO {

    // CRUD Operations for Patron
    public static void addPatron(Patron Patron) {
        String sql = "INSERT INTO Patrons (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Patron.getName());
            pstmt.setString(2, Patron.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  boolean addPatrons (Queue<Patron> pendingPatrons){
        Connection conn = null;
        boolean success = false;

        try {
            conn = DatabaseUtils.getConnection();
            DatabaseUtils.beginTransaction(conn);

            processPatrons(conn, pendingPatrons);

            DatabaseUtils.commitTransaction(conn);
            success = true;
        } catch (SQLException e) {
            handleSQLException(conn, e);
        } finally {
            DatabaseUtils.closeResources(conn);
        }

        return success;
    }
    private void processPatrons(Connection conn, Queue<Patron> pendingPatrons) throws SQLException {
        while (!pendingPatrons.isEmpty()) {
            Patron patron = pendingPatrons.poll(); // Remove from the front of the queue
            insertPatron(conn, patron);
        }
    }
    public static void insertPatron(Connection conn, Patron patron) throws SQLException {
        String sql = "INSERT INTO Patrons (name, email, membershipDate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patron.getName());
            stmt.setString(2, patron.getEmail());
            stmt.setDate(3, patron.getMembershipDate());
            stmt.executeUpdate();
        }
    }
    private void handleSQLException(Connection conn, SQLException e) {
        System.err.println("Error saving patrons: " + e.getMessage());
        DatabaseUtils.rollbackTransaction(conn);
    }

    public static Patron getPatron(int id) {
        String sql = "SELECT * FROM Patrons WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Patron(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getDate("membershipDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Patron> getAllPatrons() {
        List<Patron> Patrons = new ArrayList<>();
        String sql = "SELECT * FROM Patrons";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patrons.add(new Patron(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getDate("membershipDate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Patrons;
    }

    public static void updatePatron(Patron Patron) {
        String sql = "UPDATE Patrons SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Patron.getName());
            pstmt.setString(2, Patron.getEmail());
            pstmt.setInt(3, Patron.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePatron(int id) {
        String sql = "DELETE FROM Patrons WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
