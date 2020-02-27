package Model;

public class Session {
    public static final int CONNECTION_ESTABLISHED = 0;
    public static final int USER_NOT_REGISTERED = -1;
    public static final int INVALID_PASSWORD = -2;
    private User currentUser = null;
    private FieldChecker fc = null;
    private FileHandler fh = null;
    private static Session session = new Session();

    /* Singleton class */
    private Session(){
        fc = new FieldChecker();
        fh = new FileHandler();
    }

    public static Session getInstance() {
        return session;
    }

    public int openSession(String username, String password){
        FileHandler fh = new FileHandler();
        fh.setupSaveUserDirectory("save user");
        currentUser = fh.getUserFromSave(username);
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
     * Create an account (a user save) if all the fields are ok
     *
     * @param username                  username
     * @param firstName                 first name
     * @param lastName                  last name
     * @param mail                      mail
     * @param password                  password
     * @param passwordConfirmation      passwordConfirmation
     * @return                          TRUE if creation successful
     *                                  FALSE otherwise
     */
    public boolean createAccount(String username, String firstName, String lastName,
                              String mail, String password, String passwordConfirmation){
        if (!fc.isValidAccount(username,firstName, lastName, mail, password, passwordConfirmation))
            return false;
        if(fh.getUserFromSave(username) != null)//User already exists
            return false;
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setMail(mail);
        return fh.createUserSave(newUser);
    }
}
