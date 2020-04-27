package Controller;

import Model.Exceptions.FileHandlerConstructorException;
import Model.Exceptions.SaveUserException;
import Model.FieldChecker;
import Model.FileHandler;
import Model.User;
import View.ViewControllers.AccountModificationViewController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles account modification screen behaviour.
 */
public class AccountModificationController implements AccountModificationViewController.AccountModificationViewControllerListener {
    private final Stage stage;
    private final AccountModificationControllerListener listener;
    private AccountModificationViewController controller;


    /**
     * Constructor.
     *
     * @param stage    stage this screen needs to be in
     * @param listener listener used to communicate with ScreenHandler
     */
    public AccountModificationController(Stage stage, AccountModificationControllerListener listener) {
        this.stage = stage;
        this.listener = listener;
    }

    /**
     * Method used when entering account modification screen.
     */
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/accountModification.fxml"));
            loader.load();
            controller = loader.getController();
            controller.setListener(this);
            stage.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            System.out.println("Error loading /View/FXML/accountModification.fxml");
            e.printStackTrace();
        }
    }

    /**
     * Checks whether all fields are valid, and highlights in red those that are not.
     *
     * @return TRUE if all fields are  valid
     * FALSE otherwise
     */
    public boolean validateInformation(String firstName, String lastName, String email, String password, String passwordConfirmation) {
        FieldChecker fieldChecker = new FieldChecker();

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


        return fieldChecker.isValidAccount(Session.getInstance().getUser().getUsername(), firstName, lastName, email, password, passwordConfirmation);
    }

    /**
     * If all fields are valid, modifies the user's info.
     *
     * @param firstName            first name
     * @param lastName             last name
     * @param email                email address
     * @param password             password
     * @param passwordConfirmation password confirmation
     */
    public boolean validateModification(String firstName, String lastName, String email, String password, String passwordConfirmation) {
        if (validateInformation(firstName, lastName, email, password, passwordConfirmation)) {
            try {
                User userCurrent = Session.getInstance().getUser();
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
            } catch (FileHandlerConstructorException e) {
                System.err.println("Error while creating the file handler");
                e.printStackTrace();
                e.getCause().printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * Informs the ScreenHandler that modification is done after saving user's info when clicking on "Validate" button.
     *
     * @param firstName            first name
     * @param lastName             last name
     * @param email                email address
     * @param password             password
     * @param passwordConfirmation password confirmation
     */
    @Override
    public void onValidation(String firstName, String lastName, String email, String password, String passwordConfirmation) {
        if (validateModification(firstName, lastName, email, password, passwordConfirmation)) {
            listener.onModificationDone();
        }

    }

    /**
     * Informs the ScreenHandler that modification is done when clicking on "Return" button.
     */
    @Override
    public void onReturnButtonPressed() {
        listener.onModificationDone();
    }


    /**
     * Interface used to communicate with ScreenHandler.
     */
    public interface AccountModificationControllerListener {
        void onModificationDone();
    }
}
