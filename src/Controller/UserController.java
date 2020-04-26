package Controller;

import Controller.Exceptions.SessionOpeningException;
import Model.Exceptions.SaveUserException;
import Model.FieldChecker;
import Model.FileHandler;
import Model.User;
import View.ViewControllers.AccountController;
import View.ViewControllers.LoginScreenController;

/**
 * Handles all requests regarding the user coming from View.
 */
public class UserController {
    private User userCurrent = new User();
    private AccountController accountController;
    private LoginScreenController loginScreenController;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String username;

    /**
     * If all fields are valid, modifies the user's info.
     */
    public void validateModification() {
        if (validateInformation()) {
            try {
                userCurrent.setUsername(username);
                userCurrent.setLastName(lastName);
                userCurrent.setFirstName(firstName);
                userCurrent.setMail(email);
                userCurrent.setPassword(password);
                Session.getInstance().setUser(userCurrent);
                FileHandler handler = new FileHandler();
                handler.saveUser(userCurrent);
            } catch (SaveUserException e) {
                System.err.println("Error in saving the user");
                e.printStackTrace();
                e.getCause().printStackTrace();
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
        if (!fieldChecker.isValidUsername(username)) {
            accountController.setTextFieldStyle("username", "red");
        } else {
            accountController.setTextFieldStyle("username", "default");
        }
        if (!fieldChecker.isValidName(firstName)) {
            accountController.setTextFieldStyle("firstName", "red");
        } else {
            accountController.setTextFieldStyle("firstName", "default");
        }
        if (!fieldChecker.isValidName(lastName)) {
            accountController.setTextFieldStyle("lastName", "red");
        } else {
            accountController.setTextFieldStyle("lastName", "default");
        }
        if (!fieldChecker.isValidMail(email)) {
            accountController.setTextFieldStyle("email", "red");
        } else {
            accountController.setTextFieldStyle("email", "default");
        }
        if (!password.equals(passwordConfirmation) || password.equals("")) {
            accountController.setTextFieldStyle("password", "red");
            accountController.setTextFieldStyle("passwordConfirmation", "red");
        } else {
            accountController.setTextFieldStyle("password", "default");
            accountController.setTextFieldStyle("passwordConfirmation", "default");
        }

        return fieldChecker.isValidAccount(username, firstName, lastName, email, password, passwordConfirmation);
    }

    /**
     * Checks if the username and password are correct with the session object. If they are, the user gets to his main screen.
     * If not, the incorrect credentials are highlighted in red.
     */
    public void validateLogin() {
        try {
            Session session = Session.getInstance();
            int valid = session.openSession(username, password);
            if (valid == Session.CONNECTION_ESTABLISHED) {
                ScreenHandler.changeScene(ScreenHandler.MAIN_PAGE);
            } else if (valid == Session.USER_NOT_REGISTERED) {
                loginScreenController.setTextFieldStyle("username", "red");
            } else if (valid == Session.INVALID_PASSWORD) {
                loginScreenController.setTextFieldStyle("password", "red");
                loginScreenController.setTextFieldStyle("username", "default");
            }
        } catch (SessionOpeningException e) {
            System.err.println("Error while opening a session");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountController(AccountController accountController) {
        this.accountController = accountController;
    }

    public void setLoginScreenController(LoginScreenController loginScreenController) {
        this.loginScreenController = loginScreenController;
    }
}
