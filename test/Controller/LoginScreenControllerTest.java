package Controller;

import Model.User;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginScreenControllerTest {

    @Test
    void testClass(){
        Application.launch(testLoginScreenController.class, null);
    }

    public static class testLoginScreenController extends Application implements LoginScreenController.LoginScreenControllerListener{

        public LoginScreenController loginScreenController;

        @Override
        public void onSuccessfulLoginRequest() {}

        @Override
        public void onAccountCreationRequest() {}

        @Override
        public void start(Stage stage) throws Exception {
            Parent root = new AnchorPane();
            stage.setScene(new Scene(root));
            loginScreenController = new LoginScreenController(stage,this);

            assertTrue(showTest());
            stage.hide();
            validateLoginTest();
            stage.close();
        }

        //Tests Functions

        public boolean showTest(){
            loginScreenController.show();
            return true;
        }

        public void validateLoginTest(){
            String username = "test";
            String password = "password";
            loginScreenController.validateLogin(username, password);

            User usertest = Session.getInstance().getUser();
            assertEquals(usertest.getUsername(), username);
            assertEquals(usertest.getPassword(), password);
            assertEquals(usertest.getFirstName(), "testing");
            assertEquals(usertest.getLastName(), "testing");
            assertEquals(usertest.getMail(), "a@gmail.com");
        }
    }

}