package com.example.librarymanagemensystem.db;

import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.models.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TransactionDAO {

    public boolean issueBook(int patronId, int bookId) {
        LocalDate issueDate = LocalDate.now(); // Current date as issue date
        String sql = "INSERT INTO transactions (bookId, PatronId, issueDate, returnDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patronId);
            pstmt.setInt(2, bookId);
            pstmt.setDate(3, java.sql.Date.valueOf(issueDate));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                return true; // Issue successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Issue failed
    }
        public boolean addTransactions(List<Integer> bookIds, int patronId) {
            String query = "INSERT INTO Transactions (bookID, patronID, IssueDate) VALUES (?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                conn.setAutoCommit(false);
                for (int bookId : bookIds) {
                    pstmt.setInt(1, bookId);
                    pstmt.setInt(2, patronId);
                    pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    pstmt.addBatch();
                }
                int[] results = pstmt.executeBatch();
                conn.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


    // CRUD Operations for Transaction
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (bookId, PatronId, issueDate, returnDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getId());
            pstmt.setInt(2, transaction.getId());
            pstmt.setDate(3, transaction.getIssueDate());
            pstmt.setDate(4, transaction.getReturnDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction getTransaction(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Transaction(rs.getInt("id"), rs.getInt("bookId"), rs.getInt("PatronId"), rs.getDate("issueDate"), rs.getDate("returnDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transactions.add(new Transaction(rs.getInt("id"), rs.getInt("bookId"), rs.getInt("PatronId"), rs.getDate("issueDate"), rs.getDate("returnDate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET bookId = ?, PatronId = ?, issueDate = ?, returnDate = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getBookID());
            pstmt.setInt(2, transaction.getPatronID());
            pstmt.setDate(3, transaction.getIssueDate());
            pstmt.setDate(4, transaction.getReturnDate());
            pstmt.setInt(5, transaction.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
