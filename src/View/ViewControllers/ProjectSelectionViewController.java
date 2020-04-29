package View.ViewControllers;

import Controller.ProjectDisplay;
import Model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        //listener.showCreatePopUp();
        addProjectToDisplay(new ProjectDisplay(new Project(0,"test", "titleTest", new Date(), new ArrayList<>(), "")));
    }

    @FXML
    void onPressCopy(ActionEvent event) {
    }

    @FXML
    void onPressDelete(ActionEvent event) {

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
        projectDisplay.getRenameButton().setOnAction(e -> {
            listener.showRenamePopUp(projectDisplay.getTitle());
        });
        projectDisplay.getShareButton().setOnAction(e -> {
            listener.showSharePopUp(projectDisplay.getTitle());
        });
    }

    public interface ProjectSelectionViewControllerListener {
        void accountModificationRequest();

        void onLogoutRequest();

        void showSharePopUp(String title);

        void showRenamePopUp(String title);

        void showCreatePopUp();
    }
}
