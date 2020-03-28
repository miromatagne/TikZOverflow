package Controller;

import Model.FieldChecker;
import Model.FileHandler;
import Model.User;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Handles all requests regarding the user coming from View.
 */
public class UserController {
    private User userCurrent = new User();
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField passwordConfirmationField;
    private TextField usernameField;

    /**
     * If all fields are valid, modifies the user's info.
     */
    public void validateModification() {
        if (validateInformation()) {
            userCurrent.setUsername(usernameField.getText());
            userCurrent.setLastName(lastNameField.getText());
            userCurrent.setFirstName(firstNameField.getText());
            userCurrent.setMail(emailField.getText());
            userCurrent.setPassword(passwordField.getText());
            Session.getInstance().setUser(userCurrent);
            FileHandler handler = new FileHandler();
            if (!handler.saveUser(userCurrent)) {
                System.out.println("Error in saving the user");
            }
        }
    }

    /**
     * Checks whether all fields are valid, and highlights in red those that are not.
     *
     * @return TRUE if all fields are  valid
     * FALSE otherwise
     */
    public boolean validateInformation() {
        FieldChecker fieldChecker = new FieldChecker();
        String defaultTextFieldStyle = "-fx-text-inner-color: black;";
        String redTextFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
        if (!fieldChecker.isValidUsername(usernameField.getText())) usernameField.setStyle(redTextFieldStyle);
        else usernameField.setStyle(defaultTextFieldStyle);
        if (!fieldChecker.isValidName(firstNameField.getText())) {
            firstNameField.setStyle(redTextFieldStyle);
        } else {
            firstNameField.setStyle(defaultTextFieldStyle);
        }
        if (!fieldChecker.isValidName(lastNameField.getText())) {
            lastNameField.setStyle(redTextFieldStyle);
        } else {
            lastNameField.setStyle(defaultTextFieldStyle);
        }
        if (!fieldChecker.isValidMail(emailField.getText())) {
            emailField.setStyle(redTextFieldStyle);
        } else {
            emailField.setStyle(defaultTextFieldStyle);
        }
        if (!passwordField.getText().equals(passwordConfirmationField.getText()) || passwordField.getText().equals("")) {
            passwordField.setStyle(redTextFieldStyle);
            passwordConfirmationField.setStyle(redTextFieldStyle);
        } else {
            passwordField.setStyle(defaultTextFieldStyle);
            passwordConfirmationField.setStyle(defaultTextFieldStyle);
        }

        return fieldChecker.isValidAccount(usernameField.getText(), firstNameField.getText(), lastNameField.getText(), emailField.getText(), passwordField.getText(), passwordConfirmationField.getText());
    }

    /**
     * Checks if the username and password are correct with the session object. If they are, the user gets to his main screen.
     * If not, the incorrect credentials are highlighted in red.
     */
    public void validateLogin(TextField usernameField, PasswordField passwordField) {
        Session session = Session.getInstance();
        int valid = session.openSession(usernameField.getText(), passwordField.getText());
        final String redTextFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
        final String defaultTextFieldStyle = "-fx-text-inner-color: black;";
        if (valid == Session.CONNECTION_ESTABLISHED) {
            ScreenHandler.changeScene(ScreenHandler.MAIN_PAGE);
        } else if (valid == Session.USER_NOT_REGISTERED) {
            usernameField.setStyle(redTextFieldStyle);
        } else if (valid == Session.INVALID_PASSWORD) {
            passwordField.setStyle(redTextFieldStyle);
            usernameField.setStyle(defaultTextFieldStyle);
        }
    }

    public void setFirstNameField(TextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public void setLastNameField(TextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setPasswordConfirmationField(PasswordField passwordConfirmationField) {
        this.passwordConfirmationField = passwordConfirmationField;

    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

}
