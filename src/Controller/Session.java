package Controller;

import Controller.Exceptions.SessionOpeningException;
import Model.Exceptions.*;
import Model.FileHandler;
import Model.Project;
import Model.ProjectHandler;
import Model.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class that controls the current session, including the logging in, the logging out
 * and the account creation.
 */

public class Session {
    public static final int CONNECTION_ESTABLISHED = 0;
    public static final int USER_NOT_REGISTERED = -1;
    public static final int INVALID_PASSWORD = -2;
    private User currentUser = null;
    private Project currentProject = null;
    private FileHandler fileHandler;
    private ProjectHandler projectHandler;
    private static final Session session;

    static {
        session = new Session();
    }

    /* Singleton class */
    private Session() {
        try {
            fileHandler = new FileHandler();
            projectHandler = new ProjectHandler();
        } catch (FileHandlerConstructorException e) {
            System.err.println("Error while creating the session");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    public static Session getInstance() {
        return session;
    }

    /**
     * Tries to open a new session (log in)
     *
     * @param username Username of the user
     * @param password Password of the user
     * @return 0 if successful
     * -1 if wrong username
     * -2 if wrong password
     * @throws SessionOpeningException when there is a problem while getting a user to open his session
     */
    public int openSession(String username, String password) throws SessionOpeningException {
        try {
            fileHandler.setupSaveUserDirectory("save user");

            if (!fileHandler.saveUserExists(username)) {
                return USER_NOT_REGISTERED; //User is not registered
            } else {
                currentUser = fileHandler.getUserFromSave(username);

                if (password.equals(currentUser.getPassword())) {

                    if(currentUser.getProjectPaths().size() == 0) {
                        try {
                            newProjectRequest("test", "./TestProject/");
                        } catch (ProjectAlreadyExistsException e){ //TODO inform project creation panel
                            System.out.println("A project properties file already exists -> overwrite ? (to do : inform project creation panel)");
                        }
                    }
                    else {
                        try {
                            loadProjectRequest(currentUser.getProjectPaths().get(0));
                        } catch (ProjectLoadException e) { //TODO : informe view
                            e.printStackTrace();
                        } catch (ProjectNotAllowException e){//TODO : informe view
                            System.out.println("wesh tu peux pas ouvir car il t'appartient pas ");
                        }
                    }
                    return CONNECTION_ESTABLISHED;
                } else {
                    return INVALID_PASSWORD;
                }
            }
        } catch (UserFromSaveCreationException | SetupDirectoryException e) {
            throw new SessionOpeningException(e);
        }
    }

    /**
     * Handles new project creation requests
     *
     * @param title new project title
     * @param path new project directory path
     */
    public void newProjectRequest(String title, String path) throws ProjectAlreadyExistsException{
        try {
            currentProject = projectHandler.createProject(currentUser, path,title);
            currentUser.getProjectPaths().add(path);
            fileHandler.saveUser(currentUser);
            fileHandler.makeTexFile("");
        } catch (ProjectCreationException | DirectoryCreationException | LatexWritingException | SaveUserException e) {
            e.printStackTrace();
        }
    }

    public void loadProjectRequest(String path) throws ProjectLoadException,ProjectNotAllowException{
        Project loadedProject = projectHandler.loadProject(path);
        if(currentUser.getUsername().equals(loadedProject.getCreatorUsername()) || loadedProject.getCollaboratorsUsernames().contains(currentUser.getUsername())){
            currentProject = loadedProject;
        }
        else{
            throw new ProjectNotAllowException();
        }

    }

    public void copyProjectRequest(Project projectToCopy, User user, String new_path) throws ProjectCopyException, DirectoryCreationException, ProjectAlreadyExistsException{
        Project copyProject = projectHandler.createCopy(projectToCopy,user,new_path);
        try {
            String code = fileHandler.readInFile(projectToCopy.getPath() + File.separator + projectToCopy.getTitle() + ".tex");
            currentProject = copyProject;
            fileHandler.makeTexFile(code);
            user.getProjectPaths().add(new_path);
            fileHandler.saveUser(user);
        }catch (IOException | LatexWritingException | SaveUserException e){
            throw new ProjectCopyException(e);
        }
    }

    public void deleteProjectRequest(Project projectToDelete) throws ProjectDeletionException{
        ArrayList<String> users = projectToDelete.getCollaboratorsUsernames();
        users.add(projectToDelete.getCreatorUsername());
        String path = projectToDelete.getPath();
        projectHandler.deleteProject(projectToDelete);

        try {
            for (String user : users) {
                User u = fileHandler.getUserFromSave(user);
                if (u.getProjectPaths().contains(path)) {
                    u.getProjectPaths().remove(path);
                }
                fileHandler.saveUser(u);
            }
        }catch (UserFromSaveCreationException | SaveUserException e){
            throw new ProjectDeletionException();
        }
    }

    public void shareProjectRequest(Project project,User user) throws SaveUserException, ProjectSaveException{
        project.addCollaborator(user.getUsername());
        user.getProjectPaths().add(project.getPath());
        fileHandler.saveUser(user);
        projectHandler.saveProjectInfo(project);
    }

    public void renameProject(Project project, String newTitle) throws ProjectRenameException {
        try {
            String code = fileHandler.readInFile(project.getPath() + File.separator + project.getTitle() + ".tex");

            project.setTitle(newTitle);
            projectHandler.saveProjectInfo(project);

            fileHandler.makeTexFile(code);
        } catch (IOException | LatexWritingException | ProjectSaveException e){
            throw new ProjectRenameException();
        }
    }

    /**
     * Accesses the projects that the current logged in user has access to
     *
     * @return ArrayList containing the user's projects
     */
    public ArrayList<Project> getUserProjects(){
        ArrayList<Project> userProjects = new ArrayList<>();
        for(String p:currentUser.getProjectPaths()){
            try {
                Project project = projectHandler.loadProject(p);
                userProjects.add(project);
            } catch (ProjectLoadException e) {
                e.printStackTrace();
            }

        }
        return userProjects;
    }


    /**
     * Logs the user out of the session.
     */
    public void logOut() {
        currentUser = null;
        currentProject = null;
    }

    /**
     * Create an account (a user save) if all the fields are ok
     *
     * @param username  username
     * @param firstName first name
     * @param lastName  last name
     * @param mail      mail
     * @param password  password
     * @return TRUE if account creation was successful
     * FALSE otherwise
     */
    public boolean createAccount(String username, String firstName, String lastName,
                                 String mail, String password) {
        try {
            if (fileHandler.saveUserExists(username)) {//User already exists
                return false;
            }
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPassword(password);
            newUser.setMail(mail);
            fileHandler.createUserSave(newUser);
            return true;
        } catch (SaveUserCreationException e) {
            System.err.println("Error while creating an account");
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
        return false;
    }

    public User getUser() {
        return currentUser;
    }

    public void setUser(User newUser) {
        currentUser = newUser;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
}
