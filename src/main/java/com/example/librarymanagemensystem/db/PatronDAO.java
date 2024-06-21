package com.example.librarymanagemensystem.db;

import com.example.librarymanagemensystem.models.Patron;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class PatronDAO {
//    DatabaseConnection dbc = new DatabaseConnection();
    
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
    public static boolean addPatrons (Queue<Patron> pendingPatrons){
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
//            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            while (!pendingPatrons.isEmpty()) {
                Patron Patron = pendingPatrons.poll(); // Remove from the front of the queue
                String sql = "INSERT INTO Patrons (name, email, membershipDate) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, Patron.getName());
                stmt.setString(2, Patron.getEmail());
                stmt.setDate(3, Patron.getMembershipDate());
                stmt.executeUpdate();
            }

            conn.commit(); // Commit transaction if all inserts are successful
            success = true;
        } catch (SQLException e) {
            System.err.println("Error saving Patrons: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback if any error occurs
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
            }
        } finally {
            // Close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit to true
                    conn.close();
                }
            } catch (SQLException closeEx) {
                System.err.println("Error closing resources: " + closeEx.getMessage());
            }
        }

        return success;
    }

    public Patron getPatron(int id) {
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

    public void updatePatron(Patron Patron) {
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

    public void deletePatron(int id) {
        String sql = "DELETE FROM Patrons WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
