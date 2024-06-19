package com.example.librarymanagemensystem.db;

import com.example.librarymanagemensystem.models.Patron;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PatronDAO {
//    DatabaseConnection dbc = new DatabaseConnection();
    
    // CRUD Operations for Patron
    public void addPatron(Patron Patron) {
        String sql = "INSERT INTO Patrons (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Patron.getName());
            pstmt.setString(2, Patron.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<Patron> getAllPatrons() {
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
