package Controller;

import Model.FileHandler;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;


public class CompileListener {

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
        fh.setupSaveProjectDirectory("project");
        boolean res = fh.createProject(codeInterface.getText());

    }

}
