package Controller.ViewControllerListener;

import Controller.AlertController;
import Controller.Exceptions.Session.SessionOpeningException;
import Controller.Session;
import View.ViewControllers.LoginScreenViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles login screen behaviour.
 */
public class LoginScreenController implements LoginScreenViewController.LoginScreenViewControllerListener {
    private final Stage stage;
    private final LoginScreenControllerListener listener;
    private LoginScreenViewController controller;

    /**
     * Constructor.
     *
     * @param stage    stage this screen needs to be in.
     * @param listener listener used to communicate with ScreenHandler
     */
    public LoginScreenController(Stage stage, LoginScreenControllerListener listener) {
        this.stage = stage;
        this.listener = listener;
    }

    /**
     * Opens this scene as first scene and shows stage.
     */
    public void createScene() {
        try {
            FXMLLoader loader = getLoader();
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        } catch (IOException e) {
            AlertController.showStageError("Error while loading the login screen fxml file.", "Process aborted", true);
        }
    }


    /**
     * Opens this scene coming from another one.
     */
    public void show() {
        try {
            FXMLLoader loader = getLoader();
            stage.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            AlertController.showStageError("Error while loading the login screen fxml file.", "Process aborted", true);
        }
    }

    /**
     * Gets parent root from FXML file.
     *
     * @return root
     * @throws IOException if FXML file was not found
     */
    private FXMLLoader getLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/loginScreen.fxml"));
        loader.load();
        controller = loader.getController();
        controller.setListener(this);
        return loader;
    }

    /**
     * Checks if the username and password are correct with the session object. If they are, the user gets to his main screen.
     * If not, the incorrect credentials are highlighted in red.
     *
     * @param username username that needs to be checked
     * @param password password that needs to be checked
     */
    public void validateLogin(String username, String password) {
        try {
            Session session = Session.getInstance();
            int valid = session.openSession(username, password);
            if (valid == Session.CONNECTION_ESTABLISHED) {
                listener.onSuccessfulLoginRequest();
            } else if (valid == Session.USER_NOT_REGISTERED) {
                controller.setUsernameFieldStyle(LoginScreenViewController.ERROR_STYLE);
            } else if (valid == Session.INVALID_PASSWORD) {
                controller.setPasswordFieldStyle(LoginScreenViewController.ERROR_STYLE);
                controller.setUsernameFieldStyle(LoginScreenViewController.DEFAULT_STYLE);
            }
        } catch (SessionOpeningException e) {
            AlertController.showStageError("Error while opening the session.", "Process aborted", true);
        }
    }

    /**
     * Checks login information and eventually requests the ScreenHandler to go to main page.
     *
     * @param username username
     * @param password password
     */
    @Override
    public void onLoginAttempt(String username, String password) {
        validateLogin(username, password);
    }

    /**
     * Requests the ScreenHandler to switch to account creation page.
     */
    @Override
    public void onAccountCreation() {
        listener.onAccountCreationRequest();
    }

    /**
     * Interface used to communicate with ScreenHandler.
     */
    public interface LoginScreenControllerListener {
        void onSuccessfulLoginRequest();

        void onAccountCreationRequest();
    }

}
