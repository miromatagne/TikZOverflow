package View;

import Controller.ScreenHandler;
import Controller.UserController;
import Model.FieldChecker;
import Model.FileHandler;
import Controller.Session;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/*Controller for the modification screen that contains the method used by the button or textfield.
 */
public class ModificationController extends ControllerSuperclass implements Initializable {

    //Attribut
    @FXML private TextField usernameField ; // Attribut @FXML link directly to the .fxml corresponding to this controller class
    @FXML private TextField firstNameField ;
    @FXML private TextField lastNameField ;
    @FXML private TextField emailField ;
    @FXML private PasswordField passwordField ;
    @FXML private PasswordField passwordConfirmationField;

    private UserController userController = new UserController();

    //Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    /**
     * Function update override the one from ControllerSuperClass
     * Used to update all the TextField with the information of the CurrentUser from Session
     */
    @Override
    public void update() {
        User userCurrent = Session.getInstance().getUser();
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
    public void validateButtonAction() {
        userController.setUsernameField(usernameField);
        userController.setFirstNameField(firstNameField);
        userController.setLastNameField(lastNameField);
        userController.setEmailField(emailField);
        userController.setPasswordField(passwordField);
        userController.setPasswordConfirmationField(passwordConfirmationField);
        userController.validateModification();
    }


    /**
     * Action of the return Button : Change screen to the main page
     */
    @FXML
    public void returnButtonAction()
    {
        ScreenHandler.changeScene(ScreenHandler.MAINPAGE);
    }
}
