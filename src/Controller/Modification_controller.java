package Controller;

import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Modification_controller implements Initializable {

    //Attribut
    @FXML
    private Button modif_button ;

   //Method

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void modif_button_action(javafx.event.ActionEvent actionEvent)
    {
        ScreenHandler.change_scene(1);
    }
}
