package View.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Handles account creation interface elements.
 */
public class AccountCreationViewController extends AccountController{
    @FXML
    TextField usernameField, firstNameField, lastNameField, emailField;
    @FXML
    PasswordField passwordField, passwordConfirmationField;
    @FXML
    CheckBox termsCheckBox;
    @FXML
    Text termsAndConditionsText, backToLoginText;
    @FXML
    Button createAccountButton;

    private AccountCreationViewControllerListener listener;

    @Override
    public void update() {
        //No need to update the account creation panel
    }

    /**
     * Checks every input and highlights wrong ones in red when user clicks on "Create".
     */
    public void createAccount() {
        listener.onAccountCreationAttempt(
                usernameField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                passwordConfirmationField.getText(),
                termsCheckBox.isSelected());
    }

    /**
     * Creates a pop-up window when user clicks on "I accept terms and conditions".
     */
    public void termsAndConditionsWindow() {
        listener.showTermsAndConditions();
    }

    /**
     * Changes cursor to hand when users hovers over terms and conditions text.
     */
    public void termsAndConditionsHand() {
        changeCursorToHand(termsAndConditionsText);
    }



    /**
     * Brings the user back to login screen.
     */
    public void backToLoginScreen() {
        listener.backToLoginScreen();
    }

    /**
     * Changes cursor to hand when user hovers over back to login text.
     */
    public void backToLoginTextHand() {
        changeCursorToHand(backToLoginText);
    }

    /**
     * Changes cursor to hand.
     *
     * @param text when given text is hovered, cursor changes to hand.
     */
    private void changeCursorToHand(Text text) {
        text.setCursor(Cursor.HAND);
    }

    public void setListener(AccountCreationViewControllerListener listener) {
        this.listener = listener;
    }

    public void setTCUStyle(String style) {
        switch (style) {
            case "default": termsAndConditionsText.setStyle("-fx-fill: #0077cc");break;
            case "red" : termsAndConditionsText.setStyle("-fx-fill: red;");
        }
    }

    public interface AccountCreationViewControllerListener{

        void backToLoginScreen();
        void onAccountCreationAttempt(String username, String firstName, String lastName, String email, String password, String passwordConfirmation, boolean isBoxChecked);
        void showTermsAndConditions();
    }
}
