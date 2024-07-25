package com.example.librarymanagemensystem.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class DatabaseConnectionTest {

    @Test
    public void testGetConnectionSuccess() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "Connection should not be null");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetConnectionFailure() {
        // Change the URL to an invalid one for this test
        String invalidUrl = "jdbc:mysql://localhost:3306/invalid_database";
        String user = "library_ms";
        String password = "AhZu@#2024";

        assertThrows(SQLException.class, () -> {
            DriverManager.getConnection(invalidUrl, user, password);
        }, "Expected SQLException for invalid URL");
    }
}
