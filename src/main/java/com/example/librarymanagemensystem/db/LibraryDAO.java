package com.example.librarymanagemensystem.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryDAO {
    public static void main(String[] args){
        getAllBooks();
    }
    public static void getAllBooks() {
        String query = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("BookID"));
                System.out.println("Title: " + rs.getString("Title"));
                // Process other columns
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

