package View.ViewControllers;

import Model.Exceptions.ProjectCreationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class ProjectPopUpViewController {

    private Stage stage;

    @FXML
    private TextField projectNameField;

    @FXML
    private TextField popUpRenameText;

    @FXML
    private TextField pathField;

    private ProjectPopUpViewControllerListener listener;

    public void setListener(ProjectPopUpViewControllerListener listener) {
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
        boolean correct = listener.createProject(projectNameField.getText(), pathField.getText());
        if (correct) {
            stage.close();
        }
    }

    @FXML
    void onPressBrowseInPopUp() throws FileNotFoundException {
        String path = listener.browseFilesToGetPath(stage);
        pathField.setText(path);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public interface ProjectPopUpViewControllerListener {

        boolean createProject(String text, String path) throws ProjectCreationException;

        void renameProject(String text);

        String browseFilesToGetPath(Stage popUpStage) throws FileNotFoundException;
    }
}
