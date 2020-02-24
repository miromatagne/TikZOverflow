package Controller;

import Model.User;
import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;


/*Controller for the modification screen that contains the method used by the button or textfield :
    - Validate button : if clicked we read all the TextField and use the save object in Model in order to modified
      the username.txt.
    - Return button : if clicked the user return to the main screen and we notify him if he want to save the new data
      before quitting.
 */
public class Modification_Controller implements Initializable
{

    //Attribut
    @FXML private TextField usernameField ; // Attribut @FXML link directly to the .fxml corresponding to this controller class
    @FXML private TextField firstNameField ;
    @FXML private TextField lastNameField ;
    @FXML private TextField emailField ;
    @FXML private TextField passwordField ;
    @FXML private TextField passwordField1 ;

    private User user_temp ; //Temp user needed to test

    //Method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Uer created here to test the class but will be the user of the session
        user_temp = new User();
        user_temp.setUsername("amissena");
        user_temp.setFirstName("Alex");
        user_temp.setLastName("Miss");
        user_temp.setPassword("Voila");
        user_temp.setMail("amissena@ulb.ac.be");

        usernameField.setText(user_temp.getUsername());
        firstNameField.setText(user_temp.getFirstName());
        lastNameField.setText(user_temp.getLastName());
        emailField.setText(user_temp.getMail());
        passwordField.setText(user_temp.getPassword());
        passwordField1.setText(user_temp.getPassword());
        emailField.setText(user_temp.getMail());
    }

    @FXML
    public void validate_button_action(ActionEvent actionEvent)
    {

        System.out.println("Validate button clicked");

        /*Work in progress need the object save from Model in order to continue */
    }

    @FXML
    public void return_button_action(ActionEvent actionEvent)
    {
        ScreenHandler.change_scene(0);
    }
}
