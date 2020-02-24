package Controller;

import Model.FieldChecker;
import Model.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountCreationController implements Initializable {
    @FXML
    TextField usernameField, firstNameField, lastNameField, emailField;
    @FXML
    PasswordField passwordField, passwordConfirmationField;
    @FXML
    CheckBox termsCheckBox;
    @FXML
    Text termsAndConditionsText;
    @FXML
    Button createAccountButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void createAccount() {
        final String redTextFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
        final String defaultTextFieldStyle = "-fx-text-inner-color: black;";
        FieldChecker fieldChecker = new FieldChecker();
        if(!fieldChecker.isValidUsername(usernameField.getText())) usernameField.setStyle(redTextFieldStyle); else usernameField.setStyle(defaultTextFieldStyle);
        if(!fieldChecker.isValidName(firstNameField.getText())) firstNameField.setStyle(redTextFieldStyle); else firstNameField.setStyle(defaultTextFieldStyle);
        if(!fieldChecker.isValidName(lastNameField.getText())) lastNameField.setStyle(redTextFieldStyle); else lastNameField.setStyle(defaultTextFieldStyle);
        if(!fieldChecker.isValidMail(emailField.getText())) emailField.setStyle(redTextFieldStyle); else emailField.setStyle(defaultTextFieldStyle);
        if(!passwordField.getText().equals(passwordConfirmationField.getText()) || passwordField.getText().equals("") || passwordConfirmationField.getText().equals("")){
            passwordField.setStyle(redTextFieldStyle);
            passwordConfirmationField.setStyle(redTextFieldStyle);
        } else {
            passwordField.setStyle(defaultTextFieldStyle);
            passwordConfirmationField.setStyle(defaultTextFieldStyle);
        }
        if(!termsCheckBox.isSelected()) termsAndConditionsText.setStyle("-fx-fill: red;"); else termsAndConditionsText.setStyle("-fx-fill: #0077cc");
        if(termsCheckBox.isSelected()) { // account information check is made into session
            Session.getInstance().createAccount(usernameField.getText(), firstNameField.getText(), lastNameField.getText(), emailField.getText(), passwordField.getText(),
                    passwordConfirmationField.getText());
        }
    }
}
