package Controller;

import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

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
    public void validate_button_action(javafx.event.ActionEvent actionEvent)
    {
        System.out.println("Save ok !");
    }

}
