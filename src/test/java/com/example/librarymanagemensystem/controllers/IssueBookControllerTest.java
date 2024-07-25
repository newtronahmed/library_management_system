//package com.example.librarymanagemensystem.controllers;
//
//import com.example.librarymanagemensystem.DAO.BookDAO;
//import com.example.librarymanagemensystem.DAO.PatronDAO;
//import com.example.librarymanagemensystem.DAO.TransactionDAO;
//import com.example.librarymanagemensystem.Main;
//import com.example.librarymanagemensystem.models.Book;
//import com.example.librarymanagemensystem.models.Patron;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
////import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//import static org.mockito.Mockito.*;
//
//import java.util.Arrays;
//import java.sql.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(ApplicationExtension.class)
//public class IssueBookControllerTest {
//
//    private IssueBookController issueBookController;
//    private BookDAO bookDAO;
//    private PatronDAO patronDAO;
//    private TransactionDAO transactionDAO;
//    private Stage stage;
//
//    @Start
//    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("issue-book.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        issueBookController = fxmlLoader.getController();
//        stage.setScene(scene);
//        stage.show();
//        this.stage = stage;
//    }
//
//    @BeforeEach
//    public void setUp() {
//
//            bookDAO = mock(BookDAO.class);
//            patronDAO = mock(PatronDAO.class);
//            transactionDAO = mock(TransactionDAO.class);
//
//            issueBookController = new IssueBookController();
//            issueBookController.setBookDAO(bookDAO);
//            issueBookController.setPatronDAO(patronDAO);
//            issueBookController.setTransactionDAO(transactionDAO);
//
//
//        // Setup mock data
//        Book book1 = new  Book("newbook", "author1", "news");
//        Book book2 = new Book("newbook2", "author1", "news");
//        Patron patron1 = new Patron("patron1", "hello", new Date(System.currentTimeMillis()));
//
//        when(bookDAO.getAllBooksWithIssued()).thenReturn(List.of(book1,book2));
//        when(patronDAO.getAllPatrons()).thenReturn(List.of(patron1));
//
//        issueBookController.initialize();
//
//        verify(bookDAO).getAllBooksWithIssued();
//        verify(patronDAO).getAllPatrons();
//    }
//
//    @Test
//    public void testHandleAddToQueue(FxRobot robot) {
//        robot.clickOn("#bookComboBox");
//        robot.clickOn("Book 1");
//        robot.clickOn("#patronComboBox");
//        robot.clickOn("Patron 1");
//        robot.clickOn("#addToQueueButton");
//
//        assertTrue(issueBookController.getPendingIssues().contains(new Book("newbook", "author1", "news")));
//    }
//
//    @Test
//    public void testHandleSaveTransactions(FxRobot robot) {
//        robot.clickOn("#bookComboBox");
//        robot.clickOn("Book 1");
//        robot.clickOn("#patronComboBox");
//        robot.clickOn("Patron 1");
//        robot.clickOn("#addToQueueButton");
//        robot.clickOn("#saveTransactionsButton");
//
//        // Verify the alert
//        robot.interact(() -> {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("Books issued successfully.");
//            alert.showAndWait();
//        });
//
//        // Verify the pending issues are cleared
//        assertTrue(issueBookController.getPendingIssues().isEmpty());
//    }
//}
