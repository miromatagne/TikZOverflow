package Controller;

import Model.User;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountModificationControllerTest {

    /**
     * Main test for the AccountModificationController, launch a testAccountModification object which extends
     * Application and all the function are tested in the start function of the testAccountModification object.
     */
    @Test
    void testClass() {
        Application.launch(testAccountModification.class,null);
    }

    public static class testAccountModification extends Application implements AccountModificationController.AccountModificationControllerListener {

        public AccountModificationController accountModificationController;


        @Override
        public void onModificationDone() {}

        @Override
        public void start(Stage stage) throws Exception {
            Parent root = new AnchorPane();
            stage.setScene(new Scene(root));
            accountModificationController = new AccountModificationController(stage, this);

            //Need to setup a user in order to test all the function of the modificationController
            User userTest = new User();
            userTest.setUsername("test");
            userTest.setFirstName("first");
            userTest.setLastName("last");
            userTest.setMail("mail@");
            userTest.setPassword("pass");
            Session.getInstance().setUser(userTest);

            assertTrue(showTest());
            stage.hide();
            validateInformationTest();
            validateModificationTest();

            stage.close();
        }

        //Test Functions

        private boolean showTest(){
            accountModificationController.show(); //Test if no exception are raised by the loader
            return true;
        }

        private void validateInformationTest(){
            String firstNameValid = "amissena";
            String firstNameInvalid = "Olababy85yo";

            String lastNameValid = "amissena";
            String lastNameInvalid = "Olababy85yo";

            String emailValid = "amissena@ulb.ac.be";
            String emailInvalid = "amissenaulb.ac.be";

            String password = "amissena";
            String passwordConfirmationDifferent = "amissenaNOT";

            assertTrue(accountModificationController.validateInformation(firstNameValid, lastNameValid,
                    emailValid, password, password));
            assertFalse(accountModificationController.validateInformation(firstNameInvalid, lastNameValid,
                    emailValid, password, password));
            assertFalse(accountModificationController.validateInformation(firstNameValid, lastNameInvalid,
                    emailValid, password, password));
            assertFalse(accountModificationController.validateInformation(firstNameValid, lastNameValid,
                    emailInvalid, password, password));
            assertFalse(accountModificationController.validateInformation(firstNameValid, lastNameValid,
                    emailValid, password, passwordConfirmationDifferent));
        }

        private void validateModificationTest() {
            assertTrue(accountModificationController.validateModification("testing","testing",
                    "a@gmail.com","password", "password"));

            assertEquals(Session.getInstance().getUser().getUsername(),"test");
            assertEquals(Session.getInstance().getUser().getFirstName(), "testing");
            assertEquals(Session.getInstance().getUser().getLastName(), "testing");
            assertEquals(Session.getInstance().getUser().getPassword(), "password");
            assertEquals(Session.getInstance().getUser().getMail(), "a@gmail.com");
        }

    }
}