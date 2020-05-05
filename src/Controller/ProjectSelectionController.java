package Controller;

import Model.*;
import Model.Exceptions.*;
import View.ViewControllers.ProjectPopUpViewController;
import View.ViewControllers.ProjectSelectionViewController;
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
        currentTreatedProject.setTitle(title);
        ProjectHandler projectHandler = new ProjectHandler();
        try {
            projectHandler.saveProjectInfo(currentTreatedProject);
        } catch (ProjectSaveException e) {
            e.printStackTrace();
            AlertController.showStageError("Save error", "Error saving project information");
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

    public void loadProject(String path) throws ProjectLoadException,ProjectNotAllowException{
        ProjectHandler projectHandler = new ProjectHandler();
        Project loadedProject = projectHandler.loadProject(path);
        User currentUser = Session.getInstance().getUser();
        if(currentUser.getUsername().equals(loadedProject.getCreatorUsername()) || loadedProject.getCollaboratorsUsernames().contains(currentUser.getUsername())){
            currentTreatedProject = loadedProject;
        }
        else{
            throw new ProjectNotAllowException();
        }
    }

    public void copyProject(Project projectToCopy, User user, String new_path) throws ProjectCopyException, DirectoryCreationException, ProjectAlreadyExistsException{
        ProjectHandler projectHandler = new ProjectHandler();
        Project copyProject = projectHandler.createCopy(projectToCopy,user,new_path);
        try {
            String code = projectHandler.readInFile(projectToCopy.getPath() + File.separator + projectToCopy.getTitle() + ".tex");
            currentTreatedProject = copyProject;
            projectHandler.makeTexFile(code);
            user.addProject(new_path);
            UserHandler userHandler = new UserHandler();
            userHandler.saveUser(user);
        } catch (IOException | LatexWritingException | SaveUserException e){
            throw new ProjectCopyException(e);
        }
    }

    public void deleteProject(Project projectToDelete) throws ProjectDeletionException{
        ArrayList<String> users = projectToDelete.getCollaboratorsUsernames();
        users.add(projectToDelete.getCreatorUsername());
        String path = projectToDelete.getPath();
        ProjectHandler projectHandler = new ProjectHandler();
        projectHandler.deleteProject(projectToDelete);
        try {
            UserHandler userHandler = new UserHandler();
            for (String user : users) {
                User u = userHandler.getUserFromSave(user);
                if (u.getProjectPaths().contains(path)) {
                    u.getProjectPaths().remove(path);
                }
                userHandler.saveUser(u);
            }
        }catch (UserFromSaveCreationException | SaveUserException e){
            throw new ProjectDeletionException();
        }
    }

    public void shareProject(Project project,User user) throws SaveUserException, ProjectSaveException{
        project.addCollaborator(user.getUsername());
        user.getProjectPaths().add(project.getPath());
        UserHandler userHandler = new UserHandler();
        ProjectHandler projectHandler = new ProjectHandler();
        userHandler.saveUser(user);
        projectHandler.saveProjectInfo(project);
    }

    public interface ProjectSelectionControllerListener {
        void accountModificationRequest();
        void logout();
    }
}
