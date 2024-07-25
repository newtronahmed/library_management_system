//package com.example.librarymanagemensystem.controllers;
//
//import com.example.librarymanagemensystem.Main;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxRobot;
//import org.testfx.api.FxToolkit;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(ApplicationExtension.class)
//public class BaseControllerTest {
//
//    private BaseController baseController;
//    private Stage stage;
//
//    @Start
//    public void start(Stage stage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        baseController = fxmlLoader.getController();
//        baseController.setStage(stage);
//        stage.setScene(scene);
//        stage.show();
//        this.stage = stage;
//    }
//
//    @BeforeEach
//    public void setUp() {
//        // Set up necessary preconditions or mocks
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        FxToolkit.hideStage();
//    }
//
//    @Test
//    public void testGoToMainPage(FxRobot robot) throws IOException {
//        MainController mainControllerMock = mock(MainController.class);
//        baseController.setMainController(mainControllerMock);
//        baseController.goToMainPage();
//
//        verify(mainControllerMock).loadMainView(stage);
//    }
//
//    @Test
//    public void testShowErrorAlert(FxRobot robot) {
//        robot.interact(() -> baseController.showErrorAlert("Error Title", "Error Message"));
//
//        // Verify alert was shown
//        robot.targetWindow(stage);
//        robot.clickOn("OK");
//    }
//
//    @Test
//    public void testShowAlert(FxRobot robot) {
//        robot.interact(() -> baseController.showAlert("Info Title", "Info Message", Alert.AlertType.INFORMATION));
//
//        // Verify alert was shown
//        robot.targetWindow(stage);
//        robot.clickOn("OK");
//    }
//
//    @Test
//    public void testHandleSearchButton(FxRobot robot) throws IOException {
//        robot.clickOn("#searchButton");
//
//        // Verify that the scene has changed to the search view
//        assertEquals("Search", stage.getTitle());
//    }
//}
