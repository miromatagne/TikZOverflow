package Controller;

import Model.FieldChecker;
import Model.FileHandler;
import Model.Session;
import Model.User;
import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/*Controller for the modification screen that contains the method used by the button or textfield.
 */
public class ModificationController extends ControllerSuperclass implements Initializable
{

    //Attribut
    @FXML private TextField usernameField ; // Attribut @FXML link directly to the .fxml corresponding to this controller class
    @FXML private TextField firstNameField ;
    @FXML private TextField lastNameField ;
    @FXML private TextField emailField ;
    @FXML private TextField passwordField ;
    @FXML private TextField passwordConfirmationField;

    private User userCurrent ;

    //Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        userCurrent = new User() ;
    }

    @Override
    public void update()
    {
        userCurrent = Session.getInstance().getUser();
        usernameField.setText(userCurrent.getUsername());
        firstNameField.setText(userCurrent.getFirstName());
        lastNameField.setText(userCurrent.getLastName());
        emailField.setText(userCurrent.getMail());
        passwordField.setText(userCurrent.getPassword());
        emailField.setText(userCurrent.getMail());
        passwordConfirmationField.setText("");
    }


    /**
     *  Action of the validate button : - Check if all the information in the field are correct
     *                                  - Save the information of the user in the file username.txt
     */
    @FXML
    public void validate_button_action()
    {
        boolean validateInformation = true ;
        // Need to check if a fct can be made to generalize this check with the creationAccount screen/controller
        FieldChecker fieldChecker = new FieldChecker();
        String defaultTextFieldStyle = "-fx-text-inner-color: black;";
        String redTextFieldStyle = "-fx-text-inner-color: red; -fx-text-box-border: red;";
        if(!fieldChecker.isValidName(firstNameField.getText())){firstNameField.setStyle(redTextFieldStyle);validateInformation = false;} else{ firstNameField.setStyle(defaultTextFieldStyle);}
        if(!fieldChecker.isValidName(lastNameField.getText())){lastNameField.setStyle(redTextFieldStyle);validateInformation = false;} else{ lastNameField.setStyle(defaultTextFieldStyle);}
        if(!fieldChecker.isValidMail(emailField.getText())){emailField.setStyle(redTextFieldStyle);validateInformation = false;} else{ emailField.setStyle(defaultTextFieldStyle);}
        if(!passwordField.getText().equals(passwordConfirmationField.getText()) || passwordField.getText().equals("") || passwordConfirmationField.getText().equals("")){
            passwordField.setStyle(redTextFieldStyle);
            passwordConfirmationField.setStyle(redTextFieldStyle);
            validateInformation = false;
        } else {
            passwordField.setStyle(defaultTextFieldStyle);
            passwordConfirmationField.setStyle(defaultTextFieldStyle);
        }

        userCurrent.setLastName(lastNameField.getText());
        userCurrent.setFirstName(firstNameField.getText());
        userCurrent.setMail(emailField.getText());
        userCurrent.setPassword(passwordField.getText());
        Session.getInstance().setUser(userCurrent);

        if(validateInformation) {
            FileHandler handler = new FileHandler();
            if (!handler.saveUser(userCurrent)) {
                System.out.println("Error in saving the user");
            }
        }
    }

    @FXML
    public void return_button_action()
    {
        ScreenHandler.changeScene(ScreenHandler.MAINPAGE);
    }
}
