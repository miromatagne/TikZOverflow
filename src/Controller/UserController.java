package Controller;

import Model.FieldChecker;
import Model.FileHandler;
import Model.User;
import View.ViewControllers.AccountController;
import View.ViewControllers.LoginScreenViewController;

/**
 * Handles all requests regarding the user coming from View.
 */
public class UserController {
    private User userCurrent = new User();
    private AccountController accountController;
    private LoginScreenViewController loginScreenViewController;
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
        if (true) {
            userCurrent.setUsername(username);
            userCurrent.setLastName(lastName);
            userCurrent.setFirstName(firstName);
            userCurrent.setMail(email);
            userCurrent.setPassword(password);
            Session.getInstance().setUser(userCurrent);
            FileHandler handler = new FileHandler();
            if (!handler.saveUser(userCurrent)) {
                System.out.println("Error in saving the user");
            }
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

    public void setLoginScreenViewController(LoginScreenViewController loginScreenViewController) {
        this.loginScreenViewController = loginScreenViewController;
    }
}
