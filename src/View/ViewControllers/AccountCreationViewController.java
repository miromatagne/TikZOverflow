package View.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Handles account creation interface elements.
 */
public class AccountCreationViewController extends AccountViewController {
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

    public static final String TCU_DEFAULT_STYLE = "-fx-fill: #0077cc";
    public static final String TCU_ERROR_STYLE = "-fx-fill: red;";

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
     * Brings the user back to login screen.
     */
    public void backToLoginScreen() {
        listener.backToLoginScreen();
    }

    public void setListener(AccountCreationViewControllerListener listener) {
        this.listener = listener;
    }

    public void setTCUStyle(String style) {
        termsAndConditionsText.setStyle(style);
    }

    /**
     * Interface used to relay view information to a controller.
     */
    public interface AccountCreationViewControllerListener{

        void backToLoginScreen();
        void onAccountCreationAttempt(String username, String firstName, String lastName, String email, String password, String passwordConfirmation, boolean isBoxChecked);
        void showTermsAndConditions();
    }
}
