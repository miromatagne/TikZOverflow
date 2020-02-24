package Model;

public class Session {
    public static final int CONNECTION_ESTABLISHED = 0;
    public static final int USER_NOT_REGISTERED = -1;
    public static final int INVALID_PASSWORD = -2;
    private User currentUser = null;

    public Session(){
        this.currentUser = null;
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
}
