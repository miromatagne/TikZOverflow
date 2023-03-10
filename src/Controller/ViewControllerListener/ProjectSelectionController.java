package Controller.ViewControllerListener;

import Controller.AlertController;
import Controller.ProjectDisplay;
import Controller.Session;
import Model.Exceptions.*;
import Model.Exceptions.ProjectHandler.*;
import Model.Exceptions.ProjectHandler.ProjectCopyException;
import Model.Exceptions.ProjectHandler.ProjectCreationException;
import Model.Exceptions.ProjectHandler.ProjectDeletionException;
import Model.Exceptions.ProjectHandler.ProjectSaveException;
import Model.Exceptions.UserHandler.SaveUserException;
import Model.Exceptions.UserHandler.UserFromSaveCreationException;
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
            AlertController.showStageError("Error while loading the project selection fxml file.", "Process aborted", true);
        }
    }

    /**
     * Gets parent root from FXML file.
     *
     * @return root
     * @throws IOException if FXML file was not found
     */
    private FXMLLoader getLoader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/projectSelectionScreen.fxml"));
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
     *
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
            AlertController.showStageError("Error while loading the fxml file : "+FXMLPath, "Process aborted", true);
        }
    }

    /**
     * Shows a popup when the user presses the Share button.
     *
     * @param project   project that the user wants to share.
     */
    @Override
    public void showSharePopUp(Project project) {
        showPopUp("/View/FXML/shareProjectPopUp.fxml", String.format("Share %s", project.getTitle()));
        currentTreatedProject = project;
    }

    /**
     * Shows a popup when the user presses the Rename button.
     *
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
     *
     * @param checkedBoxes  list of all the selected ProjectDisplays
     */
    @Override
    public void deleteProjects(ObservableList<ProjectDisplay> checkedBoxes) {
        for(ProjectDisplay projectDisplay: checkedBoxes){
            ProjectHandler projectHandler = new ProjectHandler();
            Project project = projectDisplay.getProject();
            try {
                Session.getInstance().getUser().removeProject(project.getPath());
                projectHandler.deleteProject(project);
                controller.removeProjectFromDisplay(projectDisplay);
            } catch (ProjectDeletionException e) {
                AlertController.showStageError("Failed to delete", String.format("Could not delete %s", project.getTitle()));
            }
        }
    }

    /**
     *  Copies the projects that are selected (checkbox ticked) when the user
     *  presses the Copy button.
     *
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
                user.addProject(copiedProject.getPath());
                UserHandler userHandler = UserHandler.getInstance();
                userHandler.saveUser(user);
                controller.addProjectToDisplay(new ProjectDisplay(copiedProject));
            } catch (ProjectCopyException | SaveUserException e) {
                AlertController.showStageError("Copy error", String.format("Error copying %s", project.getTitle()));
            }
        }
    }

    /**
     * Alerts the ScreenHandler that the user wishes to go to the Main Page.
     *
     * @param project project the user wants to open in the Main Page, the one
     *                he clicked on
     */
    @Override
    public void goToMainPage(Project project) {
        listener.goToMainPage(project);
    }

    /**
     * Creates a project, when the user presses the Create button.
     *
     * @param title title of the project
     * @param path  path where the project should be saved (chosen
     *              by the user)
     * @return  returns false if there was an error, true otherwise.
     */
    @Override
    public boolean createProject(String title, String path) {
        if(path.equals("")){
            AlertController.showStageError("Invalid path", "Please enter a valid path");
            return false; // return false if given path was invalid
        }
        if(!title.matches("^[-_.A-Za-z0-9]+$")) {
            AlertController.showStageError("Invalid project name", "Please enter a valid project name.\nProject names can not contain spaces, or illegal file characters.");
            return false;
        }
        try {
            ProjectHandler projectHandler = new ProjectHandler();
            Project project  = projectHandler.createProject(Session.getInstance().getUser(), path, title);
            Session.getInstance().getUser().addProject(project.getPath());
            UserHandler userHandler = UserHandler.getInstance();
            userHandler.saveUser(Session.getInstance().getUser());
            ProjectDisplay projectDisplay = new ProjectDisplay(project);
            controller.addProjectToDisplay(projectDisplay);
        } catch (ProjectCreationException | SaveUserException e) {
            AlertController.showStageError("Error while creating project : "+title, "Creating failed");
        } catch (ProjectAlreadyExistsException | DirectoryCreationException e) {
            AlertController.showStageError("Error while creating project : "+title, "A project with this name already exists. Please choose another name.");
        }
        return true;
    }

    /**
     * Renames a project, when the user confirms the new title he wishes to
     * give to a project.
     *
     * @param title new title to be given to the project
     */
    @Override
    public void renameProject(String title) {
        ProjectHandler projectHandler = new ProjectHandler();
        try {
            projectHandler.renameProject(currentTreatedProject, title);
        } catch (ProjectRenameException e) {
            AlertController.showStageError("Save error", "Error renaming project");
        }
        controller.refreshProjectTitle(currentTreatedProject, title);
    }

    /**
     * Opens a DirectoryChooser when the user wants to browse a path where
     * he wants to create his project.
     *
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
     *
     * @param collaboratorUsername  username of the user we wish to share the project with
     */
    @Override
    public void shareProject(String collaboratorUsername) {
        try {
            ProjectHandler projectHandler = new ProjectHandler();
            projectHandler.shareProject(collaboratorUsername, currentTreatedProject);
            AlertController.showStageInfo("Successful sharing", String.format("Project successfully shared with %s !", collaboratorUsername));
        } catch (UserFromSaveCreationException e) {
            AlertController.showStageError("User not found", String.format("User %s does not exist", collaboratorUsername));
        } catch (SaveUserException | ProjectSaveException e) {
            AlertController.showStageError("Error sharing project", "There was an error sharing this project.");
        }
    }

    /**
     * Interface used to communicate with ScreenHandler
     */
    public interface ProjectSelectionControllerListener {
        void accountModificationRequest();

        void logout();

        void goToMainPage(Project project);
    }
}
