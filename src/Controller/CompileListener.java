package Controller;

import Model.FileHandler;


import View.ScreenHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;


public class CompileListener extends Controller_superclass {

    @FXML private TextArea codeInterface;

    @FXML
    public void compile(ActionEvent event) {
        /*
         *  ---------------------------------
         *  Parameter : mouse event
         *  Function : compile when user click on the button "compile"
         *  ---------------------------------
         */
        FileHandler fh = new FileHandler();
       // fh.setupSaveProjectDirectory("project");
       // boolean res = fh.createProject(codeInterface.getText());

    }

    @FXML
    public void modif_button_action(javafx.event.ActionEvent actionEvent)
    {
        ScreenHandler.change_scene(1);
    }

    @Override
    public void update() {
        //Nothing to update for now but the project it will needs to display will probably be updated
    }
}
