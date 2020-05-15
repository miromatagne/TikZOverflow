package View.ViewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class manages the View of the popups that appear when the user wants
 * to create, share or rename a project.
 */

public class ProjectPopUpViewController {

    private Stage stage;

    @FXML
    private TextField projectNameField;

    @FXML
    private TextField popUpRenameText;

    @FXML
    private TextField pathField;

    @FXML
    private TextField popUpShareText;

    private ProjectPopUpViewControllerListener listener;

    /**
     * Sets the listener for ths class as it is a view controller.
     *
     * @param  listener ProjectSelectionController instance
     */
    public void setListener(ProjectPopUpViewControllerListener listener) {
        this.listener = listener;
    }

    /**
     * Executes when the "Share" button in the popup is pressed.
     */
    @FXML
    void onPressShareInPopUp() {
        listener.shareProject(popUpShareText.getText());
        stage.close();
    }

    /**
     * Executes when the "Rename" button in the popup is pressed.
     */
    @FXML
    void onPressRenameInPopUp() {
        listener.renameProject(popUpRenameText.getText());
        stage.close();
    }

    /**
     * Executes when the "Create" button in the popup is pressed.
     */
    @FXML
    public void onPressCreateInPopUp() {
        boolean correct = listener.createProject(projectNameField.getText(), pathField.getText());
        if (correct) {
            stage.close();
        }
    }

    /**
     * Executes when the "Browse" button in the popup is pressed.
     * The user wants to select a path to create his project.
     */
    @FXML
    void onPressBrowseInPopUp() {
        String path = listener.browseFilesToGetPath(stage);
        pathField.setText(path);
    }

    /**
     * Sets the stage corresponding to the popup.
     *
     * @param stage popup stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public interface ProjectPopUpViewControllerListener {

        boolean createProject(String text, String path);

        void renameProject(String text);

        String browseFilesToGetPath(Stage popUpStage);

        void shareProject(String collaboratorUsername);
    }
}
