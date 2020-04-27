package Controller;

import Controller.Exceptions.SessionOpeningException;
import View.ViewControllers.LoginScreenViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController implements LoginScreenViewController.LoginScreenViewControllerListener {
    private Stage stage;
    private LoginScreenControllerListener listener;
    private LoginScreenViewController controller;

    public LoginScreenController(Stage stage, LoginScreenControllerListener listener){
        this.stage = stage;
        this.listener = listener;
    }

    public void createScene(){
        try {
            FXMLLoader loader = getLoader();
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading /View/FXML/LoginScreen.fxml");
            e.printStackTrace();
        }
    }


    public void show(){
        try {
            FXMLLoader loader = getLoader();
            stage.getScene().setRoot(loader.getRoot());
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading /View/FXML/LoginScreen.fxml");
            e.printStackTrace();
        }
    }

    private FXMLLoader getLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/LoginScreen.fxml"));
        loader.load();
        controller = loader.getController();
        controller.setListener(this);
        return loader;
    }
    
    /**
     * Checks if the username and password are correct with the session object. If they are, the user gets to his main screen.
     * If not, the incorrect credentials are highlighted in red.
     */
    public void validateLogin(String username, String password) {
        try {
            Session session = Session.getInstance();
            int valid = session.openSession(username, password);
            if (valid == Session.CONNECTION_ESTABLISHED) {
                listener.onSuccessfulLoginRequest();
            } else if (valid == Session.USER_NOT_REGISTERED) {
                controller.setTextFieldStyle("username", "red");
            } else if (valid == Session.INVALID_PASSWORD) {
                controller.setTextFieldStyle("password", "red");
                controller.setTextFieldStyle("username", "default");
            }
        } catch (SessionOpeningException e){
            System.err.println("Error while opening a session");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    @Override
    public void onLoginAttempt(String username, String password) {
        validateLogin(username, password);
    }

    @Override
    public void onAccountCreation() {
        listener.onAccountCreationRequest();
    }

    public interface LoginScreenControllerListener {
         void onSuccessfulLoginRequest();
         void onAccountCreationRequest();
    }

}
