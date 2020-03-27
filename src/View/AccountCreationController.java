package View;

import Controller.ScreenHandler;
import Controller.UserController;
import Model.FieldChecker;
import Controller.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles account creation interface elements.
 */
public class AccountCreationController extends ControllerSuperclass implements Initializable {
    @FXML
    TextField usernameField, firstNameField, lastNameField, emailField;
    @FXML
    PasswordField passwordField, passwordConfirmationField;
    @FXML
    CheckBox termsCheckBox;
    @FXML
    Text termsAndConditionsText, backToLoginText;
    @FXML
    Button createAccountButton;

    ScreenHandler screenHandler = new ScreenHandler();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void update() {

    }

    /**
     * Checks every input and highlights wrong ones in red when user clicks on "Create".
     */
    public void createAccount() {
        if(checkFieldsAndCheckbox()) { // all fields are ok, we can create account
            if(Session.getInstance().createAccount(usernameField.getText(), firstNameField.getText(), lastNameField.getText(), emailField.getText(), passwordField.getText())){ // popup before going back to login screen
                screenHandler.createAccountCreationPopup("Account successfully created !", true);
            } else {
                screenHandler.createAccountCreationPopup("Error creating a new account. Username already in use.", false);
            }
        }
    }


    /**
     * Creates a pop-up window when user clicks on "I accept terms and conditions".
     * @throws IOException when terms and conditions file doesn't exist.
     */
    public void termsAndConditionsWindow() throws IOException {
        screenHandler.tcuWindow();
    }

    /**
     * Changes cursor to hand when users hovers over terms and conditions text.
     */
    public void termsAndConditionsHand(){
        changeCursorToHand(termsAndConditionsText);
    }

    /**
     * Checks if fields and checkbox are well filled.
     * @return true if everything is ok, false otherwise
     */
    private boolean checkFieldsAndCheckbox(){
        UserController userController = new UserController();
        userController.setUsernameField(usernameField);
        userController.setEmailField(emailField);
        userController.setFirstNameField(firstNameField);
        userController.setLastNameField(lastNameField);
        userController.setPasswordConfirmationField(passwordConfirmationField);
        userController.setPasswordField(passwordField);

        boolean validCreation = userController.validateInformation();
        if(!termsCheckBox.isSelected()) termsAndConditionsText.setStyle("-fx-fill: red;"); else termsAndConditionsText.setStyle("-fx-fill: #0077cc");
        return termsCheckBox.isSelected() && validCreation;
    }

    /**
     * Brings the user back to login screen.
     */
    public void backToLoginScreen(){
        ScreenHandler.changeScene(ScreenHandler.LOGINSCREEN);
    }

    /**
     * Changes cursor to hand when user hovers over back to login text.
     */
    public void backToLoginTextHand(){
        changeCursorToHand(backToLoginText);
    }

    /**
     * Changes cursor to hand.
     * @param text when given text is hovered, cursor changes to hand.
     */
    private void changeCursorToHand(Text text){
        text.setCursor(Cursor.HAND);
    }
}
