package Controller;

import Model.FieldChecker;
import Model.Session;
import View.ScreenHandler;
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
public class AccountCreationController extends Controller_superclass implements Initializable {
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
                createAccountCreationPopup("Account successfully created !", true);
            } else {
                createAccountCreationPopup("Error creating a new account. Username already in use.", false);
            }
        }
    }

    /**
     * Creates a popup stage when account creation has been attempted to inform user.
     * @param message   message to display to user
     * @param success   defines if account creation was successful or not
     */
    private void createAccountCreationPopup(String message, boolean success) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Account creation");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().add(new Label(message));
        int width = (message.length() <= 30) ? 200 : 300;
        Button button = new Button("OK");
        button.setOnMouseClicked(e -> {
            popupStage.close();
            if(success) ScreenHandler.change_scene(ScreenHandler.LOGINSCREEN);
        });
        popupStage.setOnCloseRequest(e ->{
            if(success) ScreenHandler.change_scene(ScreenHandler.LOGINSCREEN);
        });
        vBox.getChildren().add(button);
        Scene scene = new Scene(vBox, width,75);
        popupStage.setScene(scene);
        popupStage.show();
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
        FieldChecker fieldChecker = new FieldChecker();
        String defaultTextFieldStyle = "-fx-text-inner-color: black;";
        String redTextFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
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
        return termsCheckBox.isSelected() && fieldChecker.isValidAccount(usernameField.getText(), firstNameField.getText(), lastNameField.getText(), emailField.getText(), passwordField.getText(), passwordConfirmationField.getText());
    }

    /**
     * Brings the user back to login screen.
     */
    public void backToLoginScreen(){
        ScreenHandler.change_scene(ScreenHandler.LOGINSCREEN);
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
