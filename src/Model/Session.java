package Model;

public class Session {
    public static final int CONNECTION_ESTABLISHED = 0;
    public static final int USER_NOT_REGISTERED = -1;
    public static final int INVALID_PASSWORD = -2;
    private User currentUser = null;


    /**
     *  Allows a user to log in
     *
     *  @param  username         Name of the user
     *  @param  password         Password of the user
     *  @return                  CONNECTION_ESTABLISHED if the connection is successful
     *                           USER_NOT_REGISTERED if the user does not yet have an account
     *                           INVALID_PASSWORD if the password does not match the username
     */
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
}
