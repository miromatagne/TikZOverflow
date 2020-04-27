package View.ViewControllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles Login screen interface interactions.
 */
public class LoginScreenViewController extends ControllerSuperclass implements Initializable {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label signUpLabel;

    private LoginScreenViewControllerListener listener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Tries to login with filled in username and password.
     */
    public void login() {
        listener.onLoginAttempt(usernameField.getText(), passwordField.getText());
    }

    /**
     * Change TextField style. Used when a field is not correct.
     * @param field Name of the field that must change
     * @param style New style to be applied. If different from "red",  it's
     *              considered to be "default".
     */
    public void setTextFieldStyle(String field, String style){
        String textFieldStyle = "-fx-text-inner-color: black;";
        if(style.equals("red")) {
            textFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
        }
        switch (field){
            case "username": usernameField.setStyle(textFieldStyle);break;
            case "password": passwordField.setStyle(textFieldStyle);break;
        }
    }

    /**
     * Action of "Sign Up" button. Changes from the "Log in" screen to the "Account creation" screen.
     */
    public void newAccount() {
        listener.onAccountCreation();
    }

    @Override
    public void update() {
        //No need to update login Screen
    }

    public void setListener(LoginScreenViewControllerListener listener){
        this.listener = listener;
    }

    public interface LoginScreenViewControllerListener{
        void onLoginAttempt(String username, String password);
        void onAccountCreation();
    }
}
