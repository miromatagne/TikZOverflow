package View.ViewControllers;

import Controller.Session;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the modification screen that contains the methods used by the buttons and to update the textfields.
 */
public class AccountModificationViewController extends AccountViewController implements Initializable {

    //Attribut
    @FXML
    private TextField usernameField; // Attribut @FXML link directly to the .fxml corresponding to this controller class
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmationField;

    private AccountModificationViewControllerListener listener;

    //Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
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
        passwordConfirmationField.setText("");

        setTextFieldStyle("firstName", "default");
        setTextFieldStyle("lastName", "default");
        setTextFieldStyle("email", "default");
        setTextFieldStyle("password", "default");
        setTextFieldStyle("passwordConfirmation", "default");
    }


    /**
     * Action of the validate button : - Check if all the information in the field are correct
     * - Save the information of the user in the file username.txt
     */
    @FXML
    public void validateButtonAction() {
        listener.onValidation(usernameField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                passwordConfirmationField.getText());
    }




    /**
     * Action of the return Button : Change screen to the main page
     */
    @FXML
    public void returnButtonAction() {
        listener.onReturnButtonPressed();
    }

    public void setListener(AccountModificationViewControllerListener listener){
        this.listener = listener;
    }
    /**
     * Interface used to relay information to corresponding controller
     */
    public interface AccountModificationViewControllerListener {
        void onValidation(String username, String firstName, String lastName, String email, String password, String passwordConfirmation);
        void onReturnButtonPressed();
    }
}
