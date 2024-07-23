//package com.example.librarymanagemensystem.DAO;
//
////import com.example.librarymanagemensystem.DAO.PatronDAO;
//import com.example.librarymanagemensystem.models.Patron;
////import com.example.librarymanagemensystem.utils.DatabaseUtils;
//import org.junit.jupiter.api.*;
//import org.mockito.*;
//
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class PatronDAOTestOne {
//
//    @Mock
//    private Connection mockConnection;
//
//    @Mock
//    private PreparedStatement mockPreparedStatement;
//
//    @Mock
//    private ResultSet mockResultSet;
//
//    @InjectMocks
//    private PatronDAO patronDAO;
//
//    @BeforeAll
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testAddPatron() throws SQLException {
//        Patron patron = new Patron("John Doe", "john.doe@example.com", new Date(System.currentTimeMillis()));
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//
//        PatronDAO.addPatron(patron);
//
//        verify(mockPreparedStatement).setString(1, patron.getName());
//        verify(mockPreparedStatement).setString(2, patron.getEmail());
//        verify(mockPreparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testAddPatrons() throws SQLException {
//        Queue<Patron> patrons = new LinkedList<>();
//        patrons.add(new Patron("Alice Smith", "alice.smith@example.com", new Date(System.currentTimeMillis())));
//        patrons.add(new Patron("Bob Johnson", "bob.johnson@example.com", new Date(System.currentTimeMillis())));
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//
//        boolean result = new PatronDAO().addPatrons(patrons);
//        assertTrue(result);
//
//        verify(mockPreparedStatement, times(2)).executeUpdate();
//    }
//
//    @Test
//    public void testGetPatron() throws SQLException {
//        Patron patron = new Patron("Jane Doe", "jane.doe@example.com", new Date(System.currentTimeMillis()));
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(true);
//        when(mockResultSet.getInt("id")).thenReturn(patron.getId());
//        when(mockResultSet.getString("name")).thenReturn(patron.getName());
//        when(mockResultSet.getString("email")).thenReturn(patron.getEmail());
//        when(mockResultSet.getDate("membershipDate")).thenReturn(patron.getMembershipDate());
//
//        Patron retrievedPatron = PatronDAO.getPatron(patron.getId());
//        assertNotNull(retrievedPatron);
//        assertEquals(patron.getName(), retrievedPatron.getName());
//        assertEquals(patron.getEmail(), retrievedPatron.getEmail());
//    }
//
//    @Test
//    public void testGetAllPatrons() throws SQLException {
//        List<Patron> patrons = new LinkedList<>();
//        patrons.add(new Patron("Alice Smith", "alice.smith@example.com", new Date(System.currentTimeMillis())));
//        patrons.add(new Patron("Bob Johnson", "bob.johnson@example.com", new Date(System.currentTimeMillis())));
//
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(true, true, false); // Simulate two rows
//
//        when(mockResultSet.getInt("id")).thenReturn(1, 2);
//        when(mockResultSet.getString("name")).thenReturn("Alice Smith", "Bob Johnson");
//        when(mockResultSet.getString("email")).thenReturn("alice.smith@example.com", "bob.johnson@example.com");
//        when(mockResultSet.getDate("membershipDate")).thenReturn(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
//
//        List<Patron> retrievedPatrons = PatronDAO.getAllPatrons();
//        assertEquals(2, retrievedPatrons.size());
//    }
//
//    @Test
//    public void testUpdatePatron() throws SQLException {
//        Patron patron = new Patron("Tom Jones", "tom.jones@example.com", new Date(System.currentTimeMillis()));
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//
//        PatronDAO.updatePatron(patron);
//
//        verify(mockPreparedStatement).setString(1, patron.getName());
//        verify(mockPreparedStatement).setString(2, patron.getEmail());
//        verify(mockPreparedStatement).setInt(3, patron.getId());
//        verify(mockPreparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testDeletePatron() throws SQLException {
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//
//        PatronDAO.deletePatron(1);
//
//        verify(mockPreparedStatement).setInt(1, 1);
//        verify(mockPreparedStatement).executeUpdate();
//    }
//}
