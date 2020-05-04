package Controller;

import Model.Exceptions.ProjectCreationException;
import Model.Project;
import Model.ProjectHandler;
import View.ViewControllers.ProjectPopUpViewController;
import View.ViewControllers.ProjectSelectionViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectSelectionController implements ProjectSelectionViewController.ProjectSelectionViewControllerListener, ProjectPopUpViewController.ProjectPopUpViewControllerListener {

    private final Stage stage;
    private final ProjectSelectionControllerListener listener;
    private Project currentTreatedProject;
    private ProjectSelectionViewController controller;
    private ProjectPopUpViewController popUpController;

    /**
     * Constructor.
     *
     * @param stage    stage this screen needs to be in.
     * @param listener listener used to communicate with ScreenHandler
     */
    public ProjectSelectionController(Stage stage, ProjectSelectionControllerListener listener) {
        this.stage = stage;
        this.listener = listener;
    }
    /**
     * Opens this scene coming from another one.
     */
    public void show() {
        try {
            FXMLLoader loader = getLoader();
            stage.getScene().setRoot(loader.getRoot());
        } catch (IOException e) {
            System.err.println("Error loading /View/FXML/projectSelection.fxml");
            e.printStackTrace();
            AlertController.showStageError("Error while loading the project selection fxml file.", "Process aborted", true);
        }
    }

    /**
     * Gets parent root from FXML file.
     * @return root
     * @throws IOException if FXML file was not found
     */
    private FXMLLoader getLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/projectSelection.fxml"));
        loader.load();
        controller = loader.getController();
        controller.setListener(this);
        return loader;
    }

    @Override
    public void accountModificationRequest() {
        listener.accountModificationRequest();
    }

    @Override
    public void onLogoutRequest() {
        listener.logout();
    }

    private void showPopUp(String FXMLPath, String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath));
        Parent root;
        try {
            root = loader.load();
            popUpController = loader.getController();
            popUpController.setListener(this);
            Stage popupStage = new Stage();
            popUpController.setStage(popupStage);
            popupStage.setTitle(title);
            Scene scene = new Scene(root, 350, 150);
            popupStage.setScene(scene);
            popupStage.show();
        } catch (IOException e) {
            System.err.println(String.format("Unable to load %s pop-up path", FXMLPath));
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while loading the fxml file : "+FXMLPath, "Process aborted", true);
        }
    }

    @Override
    public void showSharePopUp(Project project) {
        showPopUp("/View/FXML/shareProjectPopUp.fxml", String.format("Share %s", project.getTitle()));
        currentTreatedProject = project;
    }

    @Override
    public void showRenamePopUp(Project project) {
        showPopUp("/View/FXML/renameProjectPopUp.fxml", String.format("Rename %s", project.getTitle()));
        currentTreatedProject = project;
    }

    @Override
    public void showCreatePopUp() {
        showPopUp("/View/FXML/createProjectPopUp.fxml", "Create your project");
    }

    @Override
    public void createProject(String title) {
        try {
            Project project  = ProjectHandler.getInstance().createProject(Session.getInstance().getUser(), title, null, "");
            ProjectDisplay projectDisplay = new ProjectDisplay(project);
            controller.addProjectToDisplay(projectDisplay);
        } catch (ProjectCreationException e) {
            System.err.println(String.format("Error creating project %s", title));
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while creating project : "+title, "Creating failed");
        }
    }

    @Override
    public void renameProject(String title) {
        currentTreatedProject.setTitle(title);
        controller.refreshProjectTitle(currentTreatedProject, title);
    }

    public interface ProjectSelectionControllerListener {
        void accountModificationRequest();
        void logout();
    }
}
