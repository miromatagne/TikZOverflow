package Controller;

import Model.FieldChecker;
import View.ViewControllers.AccountViewController;


/**
 * This abstract class is used for account controllers (create and modification) because they have similarities
 */
public abstract class AccountController {

    /**
     * Checks whether all fields are valid, and highlights in red those that are not.
     *
     * @return TRUE if all fields are  valid
     * FALSE otherwise
     */
    public boolean validateInformation(AccountViewController controller ,String username, String firstName, String lastName, String email, String password, String passwordConfirmation) {
        FieldChecker fieldChecker = new FieldChecker();
        if (!fieldChecker.isValidUsername(username)) {
            controller.setTextFieldStyle("username", "red");
        } else {
            controller.setTextFieldStyle("username", "default");
        }
        if (!fieldChecker.isValidName(firstName)) {
            controller.setTextFieldStyle("firstName", "red");
        } else {
            controller.setTextFieldStyle("firstName", "default");
        }
        if (!fieldChecker.isValidName(lastName)) {
            controller.setTextFieldStyle("lastName", "red");
        } else {
            controller.setTextFieldStyle("lastName", "default");
        }
        if (!fieldChecker.isValidMail(email)) {
            controller.setTextFieldStyle("email", "red");
        } else {
            controller.setTextFieldStyle("email", "default");
        }
        if (!password.equals(passwordConfirmation) || password.equals("")) {
            controller.setTextFieldStyle("password", "red");
            controller.setTextFieldStyle("passwordConfirmation", "red");
        } else {
            controller.setTextFieldStyle("password", "default");
            controller.setTextFieldStyle("passwordConfirmation", "default");
        }

        return fieldChecker.isValidAccount(username, firstName, lastName, email, password, passwordConfirmation);
    }
}
