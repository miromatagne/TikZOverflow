package Controller;

import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Modification_button extends Controller_superclass implements Initializable {

    //Attribut

   //Method

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void modif_button_action(javafx.event.ActionEvent actionEvent)
    {
        ScreenHandler.change_scene(1);
    }

    @Override
    public void update()
    {}
}
