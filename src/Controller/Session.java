package Controller;

import Controller.Exceptions.Session.SessionOpeningException;
import Model.Exceptions.ProjectHandler.ProjectLoadException;
import Model.Exceptions.UserAlreadyExistsException;
import Model.Exceptions.UserHandler.SaveUserCreationException;
import Model.Exceptions.UserHandler.SetupDirectoryException;
import Model.Exceptions.UserHandler.UserFromSaveCreationException;
import Model.Project;
import Model.ProjectHandler;
import Model.User;
import Model.UserHandler;

import java.util.ArrayList;

/**
 * Class that controls the current session, including the logging in, the logging out
 * and the account creation.
 */

public class Session {
    public static final int CONNECTION_ESTABLISHED = 0;
    public static final int USER_NOT_REGISTERED = -1;
    public static final int INVALID_PASSWORD = -2;
    private static final int IMPOSSIBLE_TO_CREATE_PROJECT = -3;
    private User currentUser = null;
    private Project currentProject = null;
    private final UserHandler userHandler;
    private final ProjectHandler projectHandler;
    private static final Session session;

    static {
        session = new Session();
    }

    private String save_user;

    /**
     *  Singleton class
     */
    private Session() {
        userHandler = UserHandler.getInstance();
        projectHandler = new ProjectHandler();
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
        return openSessionWithDirectory(username, password, UserHandler.DEFAULT_DIRECTORY);
    }

    public int openSessionWithDirectory(String username, String password, String userDirectory ) throws SessionOpeningException {
        try {
            userHandler.setupSaveUserDirectory(userDirectory);
            if (!userHandler.saveUserExists(username)) {
                return USER_NOT_REGISTERED; //User is not registered
            } else {
                currentUser = userHandler.getUserFromSave(username);
                if (password.equals(currentUser.getPassword())) {
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
     * Accesses the projects that the current logged in user has access to
     *
     * @return ArrayList containing the user's projects
     */
    public ArrayList<Project> getUserProjects(){
        ArrayList<Project> userProjects = new ArrayList<>();
        for(String projectPath:currentUser.getProjectPaths()){
            try {
                Project project = projectHandler.loadProject(projectPath);
                userProjects.add(project);
            } catch (ProjectLoadException e) {
                e.printStackTrace(); // TODO
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
            if (userHandler.saveUserExists(username)) {//User already exists
                return false;
            }
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPassword(password);
            newUser.setMail(mail);
            userHandler.setupSaveUserDirectory(UserHandler.DEFAULT_DIRECTORY);
            userHandler.createUserSave(newUser);
            return true;
        } catch (SaveUserCreationException | SetupDirectoryException e) {
            System.err.println("Error while creating an account");
            e.printStackTrace();
            e.getCause().printStackTrace();
            AlertController.showStageError("Error while creating an account.", "Account creation failed");
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            AlertController.showStageError("Error while creating an account.", "Username is already taken, choose another one.");
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
