package Controller;

import Model.Session;
import Model.User;
import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


/*Controller for the modification screen that contains the method used by the button or textfield :
    - Validate button : if clicked we read all the TextField and use the save object in Model in order to modified
      the username.txt.
    - Return button : if clicked the user return to the main screen and we notify him if he want to save the new data
      before quitting.
 */
public class modificationController extends controllerSuperclass implements Initializable
{

    //Attribut
    @FXML private TextField usernameField ; // Attribut @FXML link directly to the .fxml corresponding to this controller class
    @FXML private TextField firstNameField ;
    @FXML private TextField lastNameField ;
    @FXML private TextField emailField ;
    @FXML private TextField passwordField ;
    @FXML private TextField passwordField1 ;

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
        passwordField1.setText("");
    }


    @FXML
    public void validate_button_action()
    {

        System.out.println("Validate button clicked");

        /*Work in progress need the object save from Model in order to continue */
    }

    @FXML
    public void return_button_action()
    {
        ScreenHandler.changeScene(ScreenHandler.MAINPAGE);
    }
}
