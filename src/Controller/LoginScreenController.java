package Controller;


import Model.Session;
import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController extends ControllerSuperclass implements Initializable {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label signUpLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * Checks if the username an password are correct with the session object. If they are, the user gets to his main screen.
     * If not, the incorrect credentials are highlighted in red.
     */
    public void login() {
        Session session = Session.getInstance();
        int valid = session.openSession(username.getText(),password.getText());
        final String redTextFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
        final String defaultTextFieldStyle = "-fx-text-inner-color: black;";
        if (valid == Session.CONNECTION_ESTABLISHED){
            ScreenHandler.changeScene(ScreenHandler.MAINPAGE);
        }
        else if (valid == Session.USER_NOT_REGISTERED){
            username.setStyle(redTextFieldStyle);
        }
        else if(valid == Session.INVALID_PASSWORD){
            password.setStyle(redTextFieldStyle);
            username.setStyle(defaultTextFieldStyle);

        }

    }

    /**
     * Action of "Sign Up" button. Changes from the "Log in" screen to the "Account creation" screen.
     */
    public void newAccount() {
        ScreenHandler.changeScene(ScreenHandler.ACCOUNTCREATION);
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
