package Controller;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountCreationControllerTest {

    /**
     * Main test for the AccountCreationController, launch a testAccountCreation object which extends Application
     * and all the function are tested in the start function of the testAccountCreation object (listeners fucntion included)
     */
    @Test
    void testClass(){
       Application.launch(testAccountCreation.class, null);
    }


    public static class testAccountCreation extends Application implements AccountCreationController.AccountCreationControllerListener{

        public AccountCreationController accountCreationController;
        public boolean backToLoginValid = false;
        public boolean createAccountCreationPopupValid = false;

        private String usernameValid = "amissena";
        private String usernameInvalid = "amissena?";

        private String firstNameValid = "amissena";
        private String firstNameInvalid = "Olababy85yo";

        private String lastNameValid = "amissena";
        private String lastNameInvalid = "Olababy85yo";

        private String emailValid = "amissena@ulb.ac.be";
        private String emailInvalid = "amissenaulb.ac.be";

        private String password = "amissena";
        private String passwordConfirmationDifferent = "amissenaNOT";

        @Override
        public void backToLoginScreenRequest() {
            this.backToLoginValid = true;
        }

        @Override
        public void createAccountCreationPopup(String s, boolean b) {
            if( (s == "Account successfully created !") || b){ createAccountCreationPopupValid = true;}
            else{createAccountCreationPopupValid = false;}
        }

        @Override
        public void start(Stage stage) throws Exception {
            Parent root = new AnchorPane();
            stage.setScene(new Scene(root));
            accountCreationController = new AccountCreationController(stage,this);

            assertTrue(showTest());
            stage.hide();
            validateInformationTest();
            backtoLoginTest();
            onAccountCreationAttemptTest();
            stage.close();
        }

        //Test functions

        private boolean showTest(){
            accountCreationController.show(); //Test if no exception are raised by the loader
            return true;
        }

        private void backtoLoginTest(){
            accountCreationController.backToLoginScreen();
            assertTrue(this.backToLoginValid);
        }

        private void validateInformationTest(){
            assertTrue(accountCreationController.validateInformation(usernameValid, firstNameValid, lastNameValid,
                    emailValid, password, password, true));
            assertFalse(accountCreationController.validateInformation(usernameValid, firstNameValid, lastNameValid,
                    emailValid, password, password, false));
            assertFalse(accountCreationController.validateInformation(usernameInvalid,firstNameValid, lastNameValid,
                    emailValid, password, password, true));
            assertFalse(accountCreationController.validateInformation(usernameValid,firstNameInvalid, lastNameValid,
                    emailValid, password, password, true));
            assertFalse(accountCreationController.validateInformation(usernameValid,firstNameValid, lastNameInvalid,
                    emailValid, password, password, true));
            assertFalse(accountCreationController.validateInformation(usernameValid,firstNameValid, lastNameValid,
                    emailInvalid, password, password, true));
            assertFalse(accountCreationController.validateInformation(usernameValid,firstNameValid, lastNameValid,
                    emailValid, password, passwordConfirmationDifferent, true));
        }

        private void onAccountCreationAttemptTest(){
            accountCreationController.onAccountCreationAttempt(usernameValid, firstNameValid, lastNameValid,
                    emailValid, password, password, false);
            assertFalse(this.createAccountCreationPopupValid);

            accountCreationController.onAccountCreationAttempt(usernameInvalid,firstNameValid, lastNameValid,
                    emailValid, password, password, true);
            assertFalse(this.createAccountCreationPopupValid);

            accountCreationController.onAccountCreationAttempt(usernameValid,firstNameInvalid, lastNameValid,
                    emailValid, password, password, true);
            assertFalse(this.createAccountCreationPopupValid);

            accountCreationController.onAccountCreationAttempt(usernameValid,firstNameValid, lastNameInvalid,
                    emailValid, password, password, true);
            assertFalse(this.createAccountCreationPopupValid);

            accountCreationController.onAccountCreationAttempt(usernameValid,firstNameValid, lastNameValid,
                    emailInvalid, password, password, true);
            assertFalse(this.createAccountCreationPopupValid);

            accountCreationController.onAccountCreationAttempt(usernameValid,firstNameValid, lastNameValid,
                    emailValid, password, passwordConfirmationDifferent, true);
            assertFalse(this.createAccountCreationPopupValid);
        }

    }

}
