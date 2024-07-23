package com.example.librarymanagemensystem.DAO;

import com.example.librarymanagemensystem.models.Book;
import com.example.librarymanagemensystem.db.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookDAOTest {

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
    public void setUp() throws Exception {
        // Mock DatabaseConnection to return our mockConnection
        mockedDatabaseConnection = mockStatic(DatabaseConnection.class);
        mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
    }

    @Test
    public void testAddBook() throws Exception {
        Book book = new Book(1, "Title1", "Author1", "Genre1");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        BookDAO.addBook(book);

        verify(mockPreparedStatement).setString(1, "Title1");
        verify(mockPreparedStatement).setString(2, "Author1");
        verify(mockPreparedStatement).setString(3, "Genre1");
        verify(mockPreparedStatement).executeUpdate();
    }


    @Test
    public void testGetBook() throws Exception {
        Book book = new Book(1, "Title1", "Author1", "Genre1");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(book.getId());
        when(mockResultSet.getString("title")).thenReturn(book.getTitle());
        when(mockResultSet.getString("author")).thenReturn(book.getAuthor());
        when(mockResultSet.getString("genre")).thenReturn(book.getGenre());
//        when(mockResultSet.getBoolean("isIssued")).thenReturn(book.getIsIssued());

        Book retrievedBook = BookDAO.getBook(1);

        assertNotNull(retrievedBook);
        assertEquals("Title1", retrievedBook.getTitle());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book(1, "Title1", "Author1", "Genre1");
        Book book2 = new Book(2, "Title2", "Author2", "Genre2");
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(book1.getId(), book2.getId());
        when(mockResultSet.getString("title")).thenReturn(book1.getTitle(), book2.getTitle());
        when(mockResultSet.getString("author")).thenReturn(book1.getAuthor(), book2.getAuthor());
        when(mockResultSet.getString("genre")).thenReturn(book1.getGenre(), book2.getGenre());
//        when(mockResultSet.getBoolean("isIssued")).thenReturn(book1.getIsIssued(), book2.getIsIssued());

        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.getAllBooks();

        assertEquals(2, books.size());
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book(1, "Title1", "Author1", "Genre1");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        BookDAO.updateBook(book);

        verify(mockPreparedStatement).setString(1, "Title1");
        verify(mockPreparedStatement).setString(2, "Author1");
        verify(mockPreparedStatement).setString(3, "Genre1");
        verify(mockPreparedStatement).setInt(4, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteBook() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        BookDAO.deleteBook(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testAddBooks() throws Exception {
        Queue<Book> books = new LinkedList<>();
        books.add(new Book(1, "Title1", "Author1", "Genre1"));
        books.add(new Book(2, "Title2", "Author2", "Genre2"));

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        BookDAO bookDAO = new BookDAO();
        boolean result = bookDAO.addBooks(books);

        assertTrue(result);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }

    @AfterEach
    public void tearDown() {
        // Close the mocked static context after each test
        mockedDatabaseConnection.close();
    }
}
