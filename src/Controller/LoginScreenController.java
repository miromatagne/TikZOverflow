package Controller;


import Model.Session;
import View.ScreenHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController extends controllerSuperclass implements Initializable {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(ActionEvent actionEvent) throws IOException {
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

    public void newAccount(MouseEvent mouseEvent) throws IOException {
        ScreenHandler.changeScene(ScreenHandler.ACCOUNTCREATION);
    }

    @Override
    public void update() {
        //No need to update login Screen
    }
}
