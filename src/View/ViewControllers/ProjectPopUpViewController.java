package View.ViewControllers;

import Model.Exceptions.ProjectCreationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProjectPopUpViewController {

    private Stage stage;

    @FXML
    private TextField popUpTitleText;

    @FXML
    private TextField popUpRenameText;

    private ProjectPopUpViewControllerListener listener;

    public void setListener(ProjectPopUpViewControllerListener listener){
        this.listener = listener;
    }
    @FXML
    void onPressShareInPopUp(ActionEvent event) {

    }

    @FXML
    void onPressRenameInPopUp(ActionEvent event) {
        listener.renameProject(popUpRenameText.getText());
        stage.close();
    }

    @FXML
    public void onPressCreateInPopUp(ActionEvent actionEvent) throws ProjectCreationException {
        listener.createProject(popUpTitleText.getText());
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public interface ProjectPopUpViewControllerListener {

        void createProject(String text) throws ProjectCreationException;

        void renameProject(String text);
    }
}
