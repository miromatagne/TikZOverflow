package Controller;

import Model.FileHandler;


import Model.Session;
import View.ScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class CompileListener extends ControllerSuperclass {

    @FXML private TextArea codeInterface;

    /**
     * when we click on "compile" button it send the text to a save file
     *
     */
    @FXML
    public void compile() {
        FileHandler fh = new FileHandler();
        fh.setupSaveProjectDirectory("project");
        boolean res = fh.createProject(codeInterface.getText());

    }

    @FXML
    public void modifButtonAction()
    {
        ScreenHandler.changeScene(ScreenHandler.MODIFICATIONSCREEN);
    }

    @Override
    public void update() {
        //Nothing to update for now but the project it will needs to display will probably be updated
    }

    /**
     * Action of "Log Out" button. Logs current user out and goes back to LoginScreen.
     */
    public void logout() {
        Session.getInstance().logOut();
        ScreenHandler.changeScene(ScreenHandler.LOGINSCREEN);
    }
}
