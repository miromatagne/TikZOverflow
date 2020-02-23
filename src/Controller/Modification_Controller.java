package Controller;

import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button validate_button ;

    //Method

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void validate_button_action(ActionEvent actionEvent)
    {
        TextField usernameField =  (TextField)ScreenHandler.get_current_scene().lookup("#usernameField") ;
        TextField firstNameField = (TextField)ScreenHandler.get_current_scene().lookup("#firstNameField") ;
        TextField lastNameField = (TextField)ScreenHandler.get_current_scene().lookup("#lastNameField") ;
        TextField emailField = (TextField)ScreenHandler.get_current_scene().lookup("#emailField") ;
        TextField passwordField = (TextField)ScreenHandler.get_current_scene().lookup("#passwordField") ;
        TextField passwordField1 = (TextField)ScreenHandler.get_current_scene().lookup("#passwordField1") ;

        System.out.println("Validate button clicked");

        /*Work in progress need the object save from Model in order to continue */
    }

    @FXML
    public void return_button_action(ActionEvent actionEvent)
    {
        ScreenHandler.change_scene(0);
    }
}
