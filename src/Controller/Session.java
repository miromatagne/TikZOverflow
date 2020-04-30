package Controller;

import Controller.Exceptions.SessionOpeningException;
import Model.Exceptions.*;
import Model.FileHandler;
import Model.Project;
import Model.ProjectHandler;
import Model.User;

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
            fileHandler.setupSaveProjectDirectory(ProjectHandler.PROJECT_DIRECTORY);

            if (!fileHandler.saveUserExists(username)) {
                return USER_NOT_REGISTERED; //User is not registered
            } else {
                currentUser = fileHandler.getUserFromSave(username);

                if (password.equals(currentUser.getPassword())) {
                    newProjectRequest("test", "./Test Project/");
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
    public void newProjectRequest(String title, String path){
        try {
            currentProject = projectHandler.createProject(currentUser, path);
            currentProject.setTitle(title);
            currentUser.getProjectIDs().add(currentProject.getID());
            fileHandler.makeTexFile(currentUser, "");
        } catch (ProjectCreationException e) {
            e.printStackTrace();
        } catch (DirectoryCreationException e) {
            e.printStackTrace();
        } catch (LatexWritingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a project
     *
     * @param project
     */
    public void openProject(Project project){
         this.currentProject = project;
    }

    /**
     * Accesses the projects that the current logged in user has access to
     *
     * @return ArrayList containing the user's projects
     */
    public ArrayList<Project> getUserProjects(){
        ArrayList<Project> userProjects = new ArrayList<Project>();
        for(int id: currentUser.getProjectIDs()){
            try {
                Project p = projectHandler.loadProject(id);
                userProjects.add(p);
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
