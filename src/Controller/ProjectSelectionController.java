package Controller;

import Model.Exceptions.*;
import Model.Project;
import Model.ProjectHandler;
import Model.User;
import Model.UserHandler;
import View.ViewControllers.ProjectPopUpViewController;
import View.ViewControllers.ProjectSelectionViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class controls the Project Selection Screen, which is the screen where the user's
 * projects are listed. The user can create, delete, copy, rename or share projects in this
 * screen. When he clicks on a project, he is taken to the Main Page where he will be
 * able to compile and modify his project.
 */

public class ProjectSelectionController implements ProjectSelectionViewController.ProjectSelectionViewControllerListener, ProjectPopUpViewController.ProjectPopUpViewControllerListener {

    private final Stage stage;
    private final ProjectSelectionControllerListener listener;
    private Project currentTreatedProject;
    private ProjectSelectionViewController controller;

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

    /**
     * Alerts the ScreenHandler that the user wants to modify his information.
     */
    @Override
    public void accountModificationRequest() {
        listener.accountModificationRequest();
    }

    /**
     * Alerts the ScreenHandler that the user wants to logout.
     */
    @Override
    public void onLogoutRequest() {
        listener.logout();
    }

    /**
     * This method is called to show a popup corresponding to the button
     * the user has pressed on the Project Selection Screen.
     * @param FXMLPath  path of the FXML file corresponding to the popup
     * @param title     title of the popup
     */
    private void showPopUp(String FXMLPath, String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath));
        Parent root;
        try {
            root = loader.load();
            ProjectPopUpViewController popUpController = loader.getController();
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

    /**
     * Shows a popup when the user presses the Share button.
     * @param project   project that the user wants to share.
     */
    @Override
    public void showSharePopUp(Project project) {
        showPopUp("/View/FXML/shareProjectPopUp.fxml", String.format("Share %s", project.getTitle()));
        currentTreatedProject = project;
    }

    /**
     * Shows a popup when the user presses the Rename button.
     * @param project   project that the user wants to rename.
     */
    @Override
    public void showRenamePopUp(Project project) {
        showPopUp("/View/FXML/renameProjectPopUp.fxml", String.format("Rename %s", project.getTitle()));
        currentTreatedProject = project;
    }

    /**
     * Shows a popup when the user presses the Create button.
     */
    @Override
    public void showCreatePopUp() {
        showPopUp("/View/FXML/createProjectPopUp.fxml", "Create your project");
    }

    /**
     * Deletes the projects that are selected (checkbox ticked) when the user
     * presses the Delete button.
     * @param checkedBoxes  list of all the selected ProjectDisplays
     */
    @Override
    public void deleteProjects(ObservableList<ProjectDisplay> checkedBoxes) {
        for(ProjectDisplay projectDisplay: checkedBoxes){
            ProjectHandler projectHandler = new ProjectHandler();
            Project project = projectDisplay.getProject();
            try {
                projectHandler.deleteProject(project);
                controller.removeProjectFromDisplay(projectDisplay);
            } catch (ProjectDeletionException | SaveUserException | UserFromSaveCreationException e) {
                AlertController.showStageError("Failed to delete", String.format("Could not delete %s", project.getTitle()));
            }
        }
    }

    /**
     *  Copies the projects that are selected (checkbox ticked) when the user
     *  presses the Copy button.
     * @param checkedBoxes  list of all the selected ProjectDisplays
     */
    @Override
    public void copyProjects(ObservableList<ProjectDisplay> checkedBoxes) {
        for(ProjectDisplay projectDisplay: checkedBoxes){
            ProjectHandler projectHandler = new ProjectHandler();
            Project project = projectDisplay.getProject();
            User user = Session.getInstance().getUser();
            try {
                Project copiedProject = projectHandler.createCopy(project, user);
                controller.addProjectToDisplay(new ProjectDisplay(copiedProject));
            } catch (ProjectCopyException e) {
                AlertController.showStageError("Copy error", String.format("Error copying %s", project.getTitle()));
            }
        }
    }

    /**
     * Alerts the ScreenHandler that the user wishes to go to the Main Page.
     * @param project project the user wants to open in the Main Page, the one
     *                he clicked on
     */
    @Override
    public void goToMainPage(Project project) {
        listener.goToMainPage(project);
    }

    /**
     * Creates a project, when the user presses the Create button.
     * @param title title of the project
     * @param path  path where the project should be saved (chosen
     *              by the user)
     * @return  returns false if there was an error, true otherwise.
     */
    @Override
    public boolean createProject(String title, String path) {
        if(path.equals("") || title.equals("")){
            AlertController.showStageError("Invalid information", "Please enter valid information");
            return false; // return false if given path was invalid
        }
        try {
            ProjectHandler projectHandler = new ProjectHandler();
            Project project  = projectHandler.createProject(Session.getInstance().getUser(), path, title);
            Session.getInstance().getUser().addProject(project.getPath());
            UserHandler userHandler = new UserHandler();
            userHandler.saveUser(Session.getInstance().getUser());
            ProjectDisplay projectDisplay = new ProjectDisplay(project);
            controller.addProjectToDisplay(projectDisplay);
        } catch (ProjectCreationException | LatexWritingException | SaveUserException e) {
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while creating project : "+title, "Creating failed");
        } catch (ProjectAlreadyExistsException | DirectoryCreationException e) {
            AlertController.showStageError("Error while creating project : "+title, "A project with this name already exists. Please chose another name.");
        }
        return true;
    }

    /**
     * Renames a project, when the user confirms the new title he wishes to
     * give to a project.
     * @param title new title to be given to the project
     */
    @Override
    public void renameProject(String title) {
        ProjectHandler projectHandler = new ProjectHandler();
        try {
            projectHandler.renameProject(currentTreatedProject, title);
        } catch (ProjectRenameException e) {
            e.printStackTrace();
            AlertController.showStageError("Save error", "Error renaming project");
        }
        controller.refreshProjectTitle(currentTreatedProject, title);
    }

    /**
     * Opens a DirectoryChooser when the user wants to browse a path where
     * he wants to create his project.
     * @param popUpStage stage where the DirectoryChooser will appear
     * @return  returns the selected path
     */
    @Override
    public String browseFilesToGetPath(Stage popUpStage) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose your project folder");
            File folder = directoryChooser.showDialog(popUpStage);
            if(folder==null){
                throw new FileNotFoundException();
            }
            return folder.getAbsolutePath();
        } catch (FileNotFoundException e){
            AlertController.showStageError("Error opening folder", "Please choose a valid folder", false);
            return "";
        }
    }

    /**
     * Shares a project with a different user.
     * @param collaboratorUsername  username of the user we wish to share the project with
     */
    @Override
    public void shareProject(String collaboratorUsername) {
        ProjectHandler projectHandler = new ProjectHandler();
        try {
            projectHandler.shareProject(collaboratorUsername, currentTreatedProject);
            AlertController.showStageInfo("Successful sharing", String.format("Project successfully shared with %s !", collaboratorUsername));
        } catch (UserFromSaveCreationException e) {
            AlertController.showStageError("User not found", String.format("User %s does not exist", collaboratorUsername));
        } catch (ProjectSaveException | SaveUserException e) {
            AlertController.showStageError("Error sharing project", "There was an error sharing this project.");
        }
    }

    public interface ProjectSelectionControllerListener {
        void accountModificationRequest();
        void logout();

        void goToMainPage(Project project);
    }
}
