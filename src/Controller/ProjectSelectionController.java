package Controller;

import Model.*;
import Model.Exceptions.*;
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
import java.util.ArrayList;

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

    @Override
    public void goToMainPage(Project project) {
        listener.goToMainPage(project);
    }

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
        } catch (ProjectCreationException e) {
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while creating project : "+title, "Creating failed");
        } catch (ProjectAlreadyExistsException | DirectoryCreationException e) {
            e.printStackTrace();
        } catch (LatexWritingException e) { // TODO
            e.printStackTrace();
        } catch (SaveUserException e) { // TODO
            e.printStackTrace();
        }
        return true;
    }

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

    @Override
    public void shareProject(String collaboratorUsername) {
        ProjectHandler projectHandler = new ProjectHandler();
        try {
            projectHandler.shareProject(collaboratorUsername, currentTreatedProject);
            AlertController.showStageInfo("Successful sharing", String.format("Project successfully shared with %s !", collaboratorUsername));
        } catch (UserFromSaveCreationException e) {
            AlertController.showStageError("User not found", String.format("User %s does not exist", collaboratorUsername));
        } catch (ProjectSaveException e) {
            e.printStackTrace(); // TODO
        } catch (SaveUserException e) {
            e.printStackTrace(); // TODO
        }
    }

    public interface ProjectSelectionControllerListener {
        void accountModificationRequest();
        void logout();

        void goToMainPage(Project project);
    }
}
