package com.example.librarymanagemensystem.DAO;
import com.example.librarymanagemensystem.db.DatabaseConnection;
import com.example.librarymanagemensystem.models.*;
import com.example.librarymanagemensystem.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class BookDAO {
    

    // CRUD Operations for Book
    public static void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addBooks (Queue<Book> pendingBooks){
        Connection conn = null;
        boolean success = false;

        try {
            conn = DatabaseUtils.getConnection();
            DatabaseUtils.beginTransaction(conn);

            processBooks(conn, pendingBooks);

            DatabaseUtils.commitTransaction(conn);
            success = true;
        } catch (SQLException e) {
            handleSQLException(conn, e);
        } finally {
            DatabaseUtils.closeResources(conn);
        }

        return success;
    }
    private void processBooks(Connection conn, Queue<Book> pendingBooks) throws SQLException {
        while (!pendingBooks.isEmpty()) {
            Book book = pendingBooks.poll(); // Remove from the front of the queue
            insertBook(conn, book);
        }
    }
    public static void insertBook(Connection conn, Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getGenre());
            stmt.executeUpdate();
        }
    }

    private void handleSQLException(Connection conn, SQLException e) {
        System.err.println("Error saving books: " + e.getMessage());
        DatabaseUtils.rollbackTransaction(conn);
    }

    public static Book getBook(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getString("genre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    public  List<Book> getAllBooksWithIssued(){
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.id, b.title, b.author, b.genre, " +
                "COUNT(t.id) AS transactionCount, " +
                "CASE WHEN EXISTS (" +
                "    SELECT 1 FROM Transactions t2 " +
                "    WHERE t2.bookID = b.id AND t2.ReturnDate IS NULL" +
                ") THEN 1 ELSE 0 END AS isIssued " +
                "FROM Books b " +
                "LEFT JOIN Transactions t ON b.id = t.bookID " +
                "GROUP BY b.id, b.title, b.author, b.genre " +
                "ORDER BY transactionCount DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getString("genre"), rs.getBoolean("isIssued"), rs.getInt("transactionCount")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, genre= ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());

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

