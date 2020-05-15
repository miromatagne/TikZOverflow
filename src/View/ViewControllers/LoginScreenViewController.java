package View.ViewControllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Handles Login screen interface interactions.
 */
public class LoginScreenViewController {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label signUpLabel;

    public static final String DEFAULT_STYLE = "-fx-text-inner-color: black;";
    public static final String ERROR_STYLE = "-fx-text-inner-color: red; -fx-text-box-border: red;";

    private LoginScreenViewControllerListener listener;


    /**
     * Tries to login with filled in username and password.
     */
    public void login() {
        listener.onLoginAttempt(usernameField.getText(), passwordField.getText());
    }

    /**
     * Change usernameField style. Used when the field switches from valid to invalid and vice-versa.
     *
     * @param style New style to be applied. Can be "ERROR_STYLE" (highlighted in red), or
     *              "DEFAULT_STYLE".
     */
    public void setUsernameFieldStyle(String style){
        usernameField.setStyle(style);
    }

    /**
     * Change passwordField style. Used when the field switches from valid to invalid and vice-versa.
     * @param style New style to be applied. Can be "ERROR_STYLE" (highlighted in red), or
     *              "DEFAULT_STYLE".
     */
    public void setPasswordFieldStyle(String style){
        passwordField.setStyle(style);
    }

    /**
     * Action of "Sign Up" button. Changes from the "Log in" screen to the "Account creation" screen.
     */
    public void newAccount() {
        listener.onAccountCreation();
    }

    public void setListener(LoginScreenViewControllerListener listener){
        this.listener = listener;
    }

    /**
     * Interface used to relay information to corresponding controller.
     */
    public interface LoginScreenViewControllerListener{
        void onLoginAttempt(String username, String password);

        void onAccountCreation();
    }
}
