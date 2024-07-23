package com.example.librarymanagemensystem.DAO;

import com.example.librarymanagemensystem.models.Patron;
import com.example.librarymanagemensystem.db.DatabaseConnection;
import com.example.librarymanagemensystem.utils.DatabaseUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatronDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private MockedStatic<DatabaseConnection> mockedDatabaseConnection;
    private MockedStatic<DatabaseUtils> mockedDatabaseUtils;

    @BeforeEach
    public void setUp() throws Exception {
        // Mock DatabaseConnection and DatabaseUtils to return our mockConnection
        mockedDatabaseConnection = mockStatic(DatabaseConnection.class);
        mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);

        mockedDatabaseUtils = mockStatic(DatabaseUtils.class);
        mockedDatabaseUtils.when(() -> DatabaseUtils.getConnection()).thenReturn(mockConnection);
    }

    @Test
    public void testAddPatron() throws Exception {
        Patron patron = new Patron(1, "Name1", "email1@example.com", new java.sql.Date(System.currentTimeMillis()));
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        PatronDAO.addPatron(patron);

        verify(mockPreparedStatement).setString(1, "Name1");
        verify(mockPreparedStatement).setString(2, "email1@example.com");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetPatron() throws Exception {
        Patron patron = new Patron(1, "Name1", "email1@example.com", new java.sql.Date(System.currentTimeMillis()));
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(patron.getId());
        when(mockResultSet.getString("name")).thenReturn(patron.getName());
        when(mockResultSet.getString("email")).thenReturn(patron.getEmail());
        when(mockResultSet.getDate("membershipDate")).thenReturn(patron.getMembershipDate());

        Patron retrievedPatron = PatronDAO.getPatron(1);

        assertNotNull(retrievedPatron);
        assertEquals("Name1", retrievedPatron.getName());
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        Patron patron1 = new Patron(1, "Name1", "email1@example.com", new java.sql.Date(System.currentTimeMillis()));
        Patron patron2 = new Patron(2, "Name2", "email2@example.com", new java.sql.Date(System.currentTimeMillis()));

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(patron1.getId(), patron2.getId());
        when(mockResultSet.getString("name")).thenReturn(patron1.getName(), patron2.getName());
        when(mockResultSet.getString("email")).thenReturn(patron1.getEmail(), patron2.getEmail());
        when(mockResultSet.getDate("membershipDate")).thenReturn(patron1.getMembershipDate(), patron2.getMembershipDate());

        List<Patron> patrons = PatronDAO.getAllPatrons();

        assertEquals(2, patrons.size());
    }

    @Test
    public void testUpdatePatron() throws Exception {
        Patron patron = new Patron(1, "Tom Jones", "tom.jones@example.com", new java.sql.Date(System.currentTimeMillis()));

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        PatronDAO.updatePatron(patron);

        verify(mockPreparedStatement).setString(1, "Tom Jones");
        verify(mockPreparedStatement).setString(2, "tom.jones@example.com");
        verify(mockPreparedStatement).setInt(3, patron.getId());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeletePatron() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        PatronDAO.deletePatron(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testAddPatrons() throws Exception {
        Queue<Patron> patrons = new LinkedList<>();
        patrons.add(new Patron(1, "Name1", "email1@example.com", new java.sql.Date(System.currentTimeMillis())));
        patrons.add(new Patron(2, "Name2", "email2@example.com", new java.sql.Date(System.currentTimeMillis())));

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        PatronDAO patronDAO = new PatronDAO();
        boolean result = patronDAO.addPatrons(patrons);

        assertTrue(result);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }

    @AfterEach
    public void tearDown() {
        // Close the mocked static context after each test
        mockedDatabaseConnection.close();
        mockedDatabaseUtils.close();
    }
}
