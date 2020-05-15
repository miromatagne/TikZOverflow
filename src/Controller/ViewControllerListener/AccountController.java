package Controller.ViewControllerListener;

import Model.FieldChecker;
import View.ViewControllers.AccountViewController;


/**
 * This abstract class is used for account controllers (create and modification) because they have similarities.
 */
public abstract class AccountController {

    /**
     *Checks whether all fields are valid, and highlights in red those that are not.
     *
     * @param controller           AccountViewController where the fields are going to be highlighted
     * @param username             username that needs to be checked
     * @param firstName            first name that needs to be checked
     * @param lastName             last name that needs to be checked
     * @param email                email that needs to be checked
     * @param password             password that needs to be checked
     * @param passwordConfirmation password confirmation that needs to be checked
     * @return TRUE if all fields are  valid
     * FALSE otherwise
     */
    public boolean validateInformation(AccountViewController controller ,String username, String firstName, String lastName, String email, String password, String passwordConfirmation) {
        FieldChecker fieldChecker = new FieldChecker();
        if (!fieldChecker.isValidUsername(username)) {
            controller.setUsernameFieldStyle(AccountViewController.ERROR_STYLE);
        } else {
            controller.setUsernameFieldStyle(AccountViewController.DEFAULT_STYLE);
        }
        if (!fieldChecker.isValidName(firstName)) {
            controller.setFirstNameFieldStyle(AccountViewController.ERROR_STYLE);
        } else {
            controller.setFirstNameFieldStyle(AccountViewController.DEFAULT_STYLE);
        }
        if (!fieldChecker.isValidName(lastName)) {
            controller.setLastNameFieldStyle(AccountViewController.ERROR_STYLE);
        } else {
            controller.setLastNameFieldStyle(AccountViewController.DEFAULT_STYLE);
        }
        if (!fieldChecker.isValidMail(email)) {
            controller.setEmailFieldStyle(AccountViewController.ERROR_STYLE);
        } else {
            controller.setEmailFieldStyle(AccountViewController.DEFAULT_STYLE);
        }
        if (!password.equals(passwordConfirmation) || password.equals("")) {
            controller.setPasswordFieldStyle(AccountViewController.ERROR_STYLE);
            controller.setPasswordConfirmationFieldStyle(AccountViewController.ERROR_STYLE);
        } else {
            controller.setPasswordFieldStyle(AccountViewController.DEFAULT_STYLE);
            controller.setPasswordConfirmationFieldStyle(AccountViewController.DEFAULT_STYLE);
        }

        return fieldChecker.isValidAccount(username, firstName, lastName, email, password, passwordConfirmation);
    }
}
