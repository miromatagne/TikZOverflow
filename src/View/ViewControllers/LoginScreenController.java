package View.ViewControllers;


import Controller.ScreenHandler;
import Controller.UserController;
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
public class LoginScreenController extends ControllerSuperclass implements Initializable {
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label signUpLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Tries to login with filled in username and password.
     */
    public void login() {
        UserController userController = new UserController();
        userController.setUsername(usernameField.getText());
        userController.setPassword(passwordField.getText());
        userController.setLoginScreenController(this);
        userController.validateLogin();
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
        ScreenHandler.changeScene(ScreenHandler.ACCOUNT_CREATION);
    }

    @Override
    public void update() {
        //No need to update login Screen
    }

    /**
     * Changes the cursor to a hand when the mouse hovers over the "Sign Up" button.
     */
    public void signUpHand() {
        signUpLabel.setCursor(Cursor.HAND);
    }
}
