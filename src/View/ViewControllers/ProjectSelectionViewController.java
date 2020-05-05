package View.ViewControllers;

import Controller.ProjectDisplay;
import Controller.Session;
import Model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ProjectSelectionViewController implements Initializable {

    ProjectSelectionViewControllerListener listener;

    @FXML
    private TextField createText;

    @FXML
    private TextField popUpShareText;

    @FXML
    private Button popUpShareButton;

    @FXML
    private TextField popUpRenameText;

    @FXML
    private Button popUpRenameButton;

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
            //TODO : Creer la fonction qui va recuperer le project avec la ligne ci-dessous et se deplacer a l'ecran principal
            System.out.println(tableView.getSelectionModel().getSelectedItem().getTitle());
        });
    }

    private void listProjects() {
        ArrayList<Project> projects = Session.getInstance().getUserProjects();
        for(Project p: projects){
            ProjectDisplay projectDisplay = new ProjectDisplay(p);
            addProjectToDisplay(projectDisplay);
        }
    }

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

    @FXML
    void onCreateButton(ActionEvent event) throws IOException {
        listener.showCreatePopUp();
    }

    @FXML
    void onPressCopy(ActionEvent event) {
    }

    @FXML
    void onPressDelete() {
        listener.deleteProjects(getCheckedBoxes());
    }

    @FXML
    void onPressSave(ActionEvent event) {

    }

    public ObservableList<ProjectDisplay> getCheckedBoxes() {
        ObservableList<ProjectDisplay> checkedBoxes = FXCollections.observableArrayList();
        for (ProjectDisplay p : data) {
            if (p.getCheckBox().isSelected()) {
                checkedBoxes.add(p);
            }
        }
        return checkedBoxes;
    }

    public void addProjectToDisplay(ProjectDisplay projectDisplay) {
        data.add(projectDisplay);
        projectDisplay.getRenameButton().setOnAction(e -> listener.showRenamePopUp(projectDisplay.getProject()));
        projectDisplay.getShareButton().setOnAction(e -> listener.showSharePopUp(projectDisplay.getProject()));
    }

    public void refreshProjectTitle(Project currentTreatedProject, String title) {
        for(ProjectDisplay projectDisplay : data) {
            if(projectDisplay.getProject().equals(currentTreatedProject)){
                projectDisplay.setTitle(title);
            }
        }
    }

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
    }
}
