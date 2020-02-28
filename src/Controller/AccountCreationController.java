package Controller;

import Model.FieldChecker;
import Model.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    /**
     * Checks every input and highlights wrong ones in red when user clicks on "Create".
     */
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
            //Change Screen to login screen
        }
    }

    /**
     * Creates a pop-up window when user clicks on "I accept terms and conditions".
     * @throws IOException when terms and conditions file doesn't exist.
     */
    public void termsAndConditionsWindow() throws IOException {
        //Create window for tcu
        Parent tcuRoot = FXMLLoader.load(getClass().getResource("/View/termsAndConditions.fxml"));
        Scene tcuScene = new Scene(tcuRoot);
        Stage tcuStage = new Stage();
        tcuStage.initModality(Modality.APPLICATION_MODAL);
        tcuStage.setTitle("Terms and conditions");

        File f = new File("src/View/tcu.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String tmp, text = "";
        while ((tmp = br.readLine()) != null) {
            text = text.concat(tmp + '\n');
        }
        tcuStage.setScene(tcuScene);
        tcuStage.show();
        Text tcuFullText = (Text) tcuRoot.lookup("#tcuFullText");
        tcuFullText.setText(text);
        tcuFullText.wrappingWidthProperty().bind(tcuScene.widthProperty().subtract(20));
    }

    /**
     * Changes cursor to a hand when users hovers over it.
     */
    public void termsAndConditionsHand(){
        //Change the cursor to HAND
        termsAndConditionsText.setCursor(Cursor.HAND);
    }
}
