package com.example.librarymanagemensystem.db;
import com.example.librarymanagemensystem.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BookDAO {
    

    // CRUD Operations for Book
    public static void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, isIssued) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setBoolean(3, book.getIsIssued());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean addBooks (Queue<Book> pendingBooks){
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
//            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            while (!pendingBooks.isEmpty()) {
                Book book = pendingBooks.poll(); // Remove from the front of the queue
                String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getGenre());
                stmt.executeUpdate();
            }

            conn.commit(); // Commit transaction if all inserts are successful
            success = true;
        } catch (SQLException e) {
            System.err.println("Error saving books: " + e.getMessage());
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

    public static Book getBook(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getString("genre"), rs.getBoolean("isIssued"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getString("genre"), rs.getBoolean("isIssued")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public static List<Book> getAllBooksWithIssued(){
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.id, b.title, b.author, b.genre, " +
                "CASE WHEN t.bookID IS NULL THEN 0 " +
                "ELSE 1 END AS isIssued " +
                "FROM Books b " +
                "LEFT JOIN Transactions t ON b.id = t.bookID AND t.ReturnDate IS NULL";

//        try (Connection conn = DriverManager.getConnection(url, username, password);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

//                System.out.println("Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Status: " + isIssued);
                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getString("genre"), rs.getBoolean("isIssued")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;

    }

    public static void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isIssued = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
//            pstmt.setBoolean(3, book.isIssued());
            pstmt.setInt(4, book.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

