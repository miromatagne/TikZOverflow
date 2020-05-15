package View.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This abstract class is used for account view controllers (create and modification) because they have similarities.
 */
public abstract class AccountViewController {
    @FXML
    TextField usernameField, firstNameField, lastNameField, emailField;
    @FXML
    PasswordField passwordField, passwordConfirmationField;

    public static final String DEFAULT_STYLE = "-fx-text-inner-color: black;";
    public static final String ERROR_STYLE = "-fx-text-inner-color: red; -fx-text-box-border: red;";

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
     * Change firstNameField style. Used when the field switches from valid to invalid and vice-versa.
     *
     * @param style New style to be applied. Can be "ERROR_STYLE" (highlighted in red), or
     *              "DEFAULT_STYLE".
     */
    public void setFirstNameFieldStyle(String style){
        firstNameField.setStyle(style);
    }

    /**
     * Change lastNameField style. Used when the field switches from valid to invalid and vice-versa.
     *
     * @param style New style to be applied. Can be "ERROR_STYLE" (highlighted in red), or
     *              "DEFAULT_STYLE".
     */
    public void setLastNameFieldStyle(String style){
        lastNameField.setStyle(style);
    }

    /**
     * Change passwordField style. Used when the field switches from valid to invalid and vice-versa.
     *
     * @param style New style to be applied. Can be "ERROR_STYLE" (highlighted in red), or
     *              "DEFAULT_STYLE".
     */
    public void setPasswordFieldStyle(String style){
        passwordField.setStyle(style);
    }

    /**
     * Change passwordConfirmationField style. Used when the field switches from valid to invalid and vice-versa.
     *
     * @param style New style to be applied. Can be "ERROR_STYLE" (highlighted in red), or
     *              "DEFAULT_STYLE".
     */
    public void setPasswordConfirmationFieldStyle(String style){
        passwordConfirmationField.setStyle(style);
    }

    /**
     * Change emailField style. Used when the field switches from valid to invalid and vice-versa.
     *
     * @param style New style to be applied. Can be "ERROR_STYLE" (highlighted in red), or
     *              "DEFAULT_STYLE".
     */
    public void setEmailFieldStyle(String style){
        emailField.setStyle(style);
    }
}
