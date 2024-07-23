package com.example.librarymanagemensystem.DAO;

import com.example.librarymanagemensystem.db.DatabaseConnection;
import com.example.librarymanagemensystem.models.Transaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private MockedStatic<DatabaseConnection> mockedDatabaseConnection;

    @BeforeEach
    public void setUp() {
        mockedDatabaseConnection = mockStatic(DatabaseConnection.class);
        mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
    }

    @AfterEach
    public void tearDown() {
        mockedDatabaseConnection.close();
    }

    @Test
    public void testIssueBook() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        TransactionDAO transactionDAO = new TransactionDAO();
        boolean result = transactionDAO.issueBook(1, 1);

        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).setDate(3, java.sql.Date.valueOf(LocalDate.now()));
    }

    @Test
    public void testAddTransactions() throws Exception {
        List<Integer> bookIds = Arrays.asList(1, 2, 3);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        TransactionDAO transactionDAO = new TransactionDAO();
        boolean result = transactionDAO.addTransactions(bookIds, 1);

        assertTrue(result);
        verify(mockPreparedStatement, times(3)).addBatch();
        verify(mockPreparedStatement, times(1)).executeBatch();
    }

    @Test
    public void testAddTransaction() throws Exception {
        Transaction transaction = new Transaction(1, 1, 1, java.sql.Date.valueOf(LocalDate.now()), null);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.addTransaction(transaction);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).setDate(3, transaction.getIssueDate());
        verify(mockPreparedStatement).setDate(4, transaction.getReturnDate());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetTransaction() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("bookId")).thenReturn(1);
        when(mockResultSet.getInt("PatronId")).thenReturn(1);
        when(mockResultSet.getDate("issueDate")).thenReturn(java.sql.Date.valueOf(LocalDate.now()));
        when(mockResultSet.getDate("returnDate")).thenReturn(null);

        TransactionDAO transactionDAO = new TransactionDAO();
        Transaction transaction = transactionDAO.getTransaction(1);

        assertNotNull(transaction);
        assertEquals(1, transaction.getId());
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getInt("bookId")).thenReturn(1, 2);
        when(mockResultSet.getInt("PatronId")).thenReturn(1, 2);
        when(mockResultSet.getDate("issueDate")).thenReturn(java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(LocalDate.now()));
        when(mockResultSet.getDate("returnDate")).thenReturn(null, null);

        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();

        assertEquals(2, transactions.size());
    }

    @Test
    public void testUpdateTransaction() throws Exception {
        Transaction transaction = new Transaction(1, 1, 1, java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(LocalDate.now().plusDays(7)));
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.updateTransaction(transaction);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).setDate(3, transaction.getIssueDate());
        verify(mockPreparedStatement).setDate(4, transaction.getReturnDate());
        verify(mockPreparedStatement).setInt(5, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.deleteTransaction(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}
