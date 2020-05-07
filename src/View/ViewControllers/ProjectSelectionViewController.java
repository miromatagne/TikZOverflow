package View.ViewControllers;

import Controller.ProjectDisplay;
import Controller.Session;
import Model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class manages the View of the Project Selection Screen.
 */

public class ProjectSelectionViewController implements Initializable {

    ProjectSelectionViewControllerListener listener;

    @FXML
    private TableView<ProjectDisplay> tableView;

    @FXML
    private TableColumn<ProjectDisplay, CheckBox> checkBoxColumn;

    @FXML
    private TableColumn<ProjectDisplay, String> titleColumn;

    @FXML
    private TableColumn<ProjectDisplay, String> ownerColumn;

    @FXML
    private TableColumn<ProjectDisplay, String> dateColumn;

    @FXML
    private TableColumn<ProjectDisplay, Button> renameColumn;

    @FXML
    private TableColumn<ProjectDisplay, Button> shareColumn;

    private ObservableList<ProjectDisplay> data;

    /**
     * This function executes as soon as the the class is created.
     * It initializes the TableView containing the projects and its columns,
     * and lists the appropriate projects.
     * It also sets a listener on the TableView in order to know when the user
     * clicks on a certain project.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        renameColumn.setCellValueFactory(new PropertyValueFactory<>("renameButton"));
        shareColumn.setCellValueFactory(new PropertyValueFactory<>("shareButton"));
        tableView.setItems(data);
        listProjects();
        tableView.setOnMousePressed(event -> {
            System.out.println(tableView.getSelectionModel().getSelectedItem().getProject().getTitle());
            Project project = tableView.getSelectionModel().getSelectedItem().getProject();
            listener.goToMainPage(project); 
        });
    }

    /**
     * Lists all projects of the current user in the TableView.
     */
    private void listProjects() {
        ArrayList<Project> projects = Session.getInstance().getUserProjects();
        for(Project p: projects){
            ProjectDisplay projectDisplay = new ProjectDisplay(p);
            addProjectToDisplay(projectDisplay);
        }
    }

    /**
     * Sets a listener referring to the ProjectSelectionController.
     * @param listener ProjectSelectionController instance
     */
    public void setListener(ProjectSelectionViewControllerListener listener) {
        this.listener = listener;
    }

    /**
     * Action of "Modification" button. Sends user to the account modification screen.
     */
    @FXML
    public void modificationButtonAction() {
        listener.accountModificationRequest();
    }

    /**
     * Action of "Log Out" button. Logs current user out and goes back to LoginScreen.
     */
    public void logout() {
        listener.onLogoutRequest(); //requesting logout
    }

    /**
     * Action of "Create" button.
     */
    @FXML
    void onCreateButton(){
        listener.showCreatePopUp();
    }

    /**
     * Action of "Copy" button.
     */
    @FXML
    void onPressCopy() {
        listener.copyProjects(getCheckedBoxes());
    }

    /**
     * Action of "Delete" button.
     */
    @FXML
    void onPressDelete() {
        listener.deleteProjects(getCheckedBoxes());
    }

    /**
     * @return returns a list of all ProjectDisplay items that are checked (checkbox ticked)
     */
    public ObservableList<ProjectDisplay> getCheckedBoxes() {
        ObservableList<ProjectDisplay> checkedBoxes = FXCollections.observableArrayList();
        for (ProjectDisplay p : data) {
            if (p.getCheckBox().isSelected()) {
                checkedBoxes.add(p);
            }
        }
        return checkedBoxes;
    }

    /**
     * Adds a project to the TableView on the screen.
     * @param projectDisplay ProjectDisplay to add
     */
    public void addProjectToDisplay(ProjectDisplay projectDisplay) {
        data.add(projectDisplay);
        projectDisplay.getRenameButton().setOnAction(e -> listener.showRenamePopUp(projectDisplay.getProject()));
        projectDisplay.getShareButton().setOnAction(e -> listener.showSharePopUp(projectDisplay.getProject()));
    }

    /**
     * Refreshes the title of a project when the user has decided to change its name.
     * @param currentTreatedProject project that the user has decided to rename
     * @param title new title the user wishes to give to that project
     */
    public void refreshProjectTitle(Project currentTreatedProject, String title) {
        for(ProjectDisplay projectDisplay : data) {
            if(projectDisplay.getProject().equals(currentTreatedProject)){
                projectDisplay.setTitle(title);
            }
        }
    }

    /**
     * Remove a certain project from the TableView. This happens
     * when the user decides to delete a project.
     * @param projectDisplay ProjectDisplay to remove
     */
    public void removeProjectFromDisplay(ProjectDisplay projectDisplay) {
        data.remove(projectDisplay);
    }

    public interface ProjectSelectionViewControllerListener {
        void accountModificationRequest();

        void onLogoutRequest();

        void showSharePopUp(Project project);

        void showRenamePopUp(Project project);

        void showCreatePopUp();

        void deleteProjects(ObservableList<ProjectDisplay> checkedBoxes);

        void copyProjects(ObservableList<ProjectDisplay> checkedBoxes);

        void goToMainPage(Project project);
    }
}
