package com.example.librarymanagemensystem.utils;

import com.example.librarymanagemensystem.db.DatabaseConnection;
import com.example.librarymanagemensystem.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtils {

    public static Connection getConnection() throws SQLException {
        // Return a connection to the database
        return DatabaseConnection.getConnection();
    }

    public static void beginTransaction(Connection conn) throws SQLException {
        conn.setAutoCommit(false); // Start transaction
    }

    public static void commitTransaction(Connection conn) throws SQLException {
        if (conn != null) {
            conn.commit(); // Commit transaction if all inserts are successful
        }
    }

    public static void rollbackTransaction(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback(); // Rollback if any error occurs
            }
        } catch (SQLException rollbackEx) {
            System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
        }
    }

    public static void closeResources(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
                conn.close();
            } catch (SQLException closeEx) {
                System.err.println("Error closing resources: " + closeEx.getMessage());
            }
        }
    }


}

