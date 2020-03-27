package Controller;

import Model.FileHandler;
import Model.User;

/**
 * Class that controls the current session, including the logging in, the logging out
 * and the account creation by the clients.
 */

public class Session {
    public static final int CONNECTION_ESTABLISHED = 0;
    public static final int USER_NOT_REGISTERED = -1;
    public static final int INVALID_PASSWORD = -2;
    private User currentUser = null;
    private FileHandler fileHandler;
    private static Session session = new Session();

    /* Singleton class */
    private Session(){
        fileHandler = new FileHandler();
    }

    public static Session getInstance() {
        return session;
    }

    public User getUser(){return currentUser;}
    public void setUser(User newUser){ currentUser = newUser;}

    public int openSession(String username, String password){
        fileHandler.setupSaveUserDirectory("save user");
        currentUser = fileHandler.getUserFromSave(username);
        if(currentUser == null){
            return USER_NOT_REGISTERED; //User is not registered
        }else{

            if(password.equals(currentUser.getPassword())){
                System.out.println("Connected user : "+ currentUser.getUsername());
                System.out.println("Connected user password : " + currentUser.getPassword());
                return CONNECTION_ESTABLISHED;
            }else{
                return INVALID_PASSWORD;
            }
        }
    }

    /**
     * Logs the user out of the session.
     */
    public void logOut(){
        currentUser = null;
    }

    /**
     * Create an account (a user save) if all the fields are ok
     *
     * @param username                  username
     * @param firstName                 first name
     * @param lastName                  last name
     * @param mail                      mail
     * @param password                  password
     * @return                          TRUE if creation successful
     *                                  FALSE otherwise
     */
    public boolean createAccount(String username, String firstName, String lastName,
                                 String mail, String password){
        if(fileHandler.getUserFromSave(username) != null)//User already exists
            return false;
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setMail(mail);
        return fileHandler.createUserSave(newUser);
    }
}
