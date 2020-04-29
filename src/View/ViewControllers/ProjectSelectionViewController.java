package View.ViewControllers;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    private TableView<Project> tableView;

    @FXML
    private TableColumn<Project, CheckBox> checkBoxColumn;

    @FXML
    private TableColumn<Project, String> titleColumn;

    @FXML
    private TableColumn<Project, String> ownerColumn;

    @FXML
    private TableColumn<Project, String> dateColumn;

    private ObservableList<Project> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Project p1 = new Project("Test","Minh", "12 septembre");
        Project p2 = new Project("Test2","Miro", "19 septembre");
        data = FXCollections.observableArrayList(p1, p2);
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setItems(data);

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(tableView.getSelectionModel().getSelectedItem().getTitle());
            }
        });
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
    void modificationButtonAction(ActionEvent event) {

    }

    @FXML
    void onCreateButton(ActionEvent event) {

    }

    @FXML
    void onPressCopy(ActionEvent event) {
        System.out.println(getCheckedBoxes());
    }

    @FXML
    void onPressDelete(ActionEvent event) {

    }

    @FXML
    void onPressRename(ActionEvent event) throws IOException {
    listener.showRenamePopUp();
    }

    @FXML
    void onPressSave(ActionEvent event) {

    }

    @FXML
    void onPressShare(ActionEvent event) throws IOException {
        listener.showSharePopUp();
    }

    @FXML
    void onPressShareInPopUp(ActionEvent event) {

    }

    @FXML
    void onPressRenameInPopUp(ActionEvent event) {

    }

    public ObservableList<Project> getCheckedBoxes() {
        ObservableList<Project> checkedBoxes = FXCollections.observableArrayList();
        for(Project p : data)
        {
            if(p.getCheckBox().isSelected())
            {
                checkedBoxes.add(p);
            }
        }
        return checkedBoxes;
    }

    public interface ProjectSelectionViewControllerListener {
        void accountModificationRequest();
        void onLogoutRequest();
        void showSharePopUp() throws IOException;
        void showRenamePopUp() throws IOException;
    }
}
