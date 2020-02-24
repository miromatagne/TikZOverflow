package Model;
import java.util.HashSet;

public class FieldChecker {

    HashSet<Character> userCharCollection = new HashSet<>();
    HashSet<Character> alphaCharCollection = new HashSet<>();
    HashSet<Character> numericCharCollection = new HashSet<>();

    /**
     * Setup the FieldChecker object by initializing char collections
     */
    public void setupFieldChecker(){
        for (int i = 48; i < 127; i++){ //ASCII TABLE
            if (i < 58) { //0-9
                userCharCollection.add((char) i);
                numericCharCollection.add((char) i);
            }
            else if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) { //A-Z and a-z
                alphaCharCollection.add((char)i);
                userCharCollection.add((char)i);
            }
        }
    }

    /**
     * Check if the username is valid
     *
     * @param username              String mail to be tested
     * @return                  TRUE if valid
     *                          FALSE otherwise
     */
    public boolean isValidUsername(String username){
        if (username == null || username.equals("")){
            return false;
        }
        for (int i = 0; i < username.length(); i++){
            if (! userCharCollection.contains(username.charAt(i))){
                return false;
            }
        }
        return  true;
    }

    /**
     * Check if the name is valid
     *
     * @param name              String mail to be tested
     * @return                  TRUE if valid
     *                          FALSE otherwise
     */
    public boolean isValidName(String name){
        if (name == null || name.equals("")){
            return false;
        }
        for (int i = 0; i < name.length(); i++){
            if ((!alphaCharCollection.contains(name.charAt(i))) && name.charAt(i) != '-' && name.charAt(i) != ' '){
                return false;
            }
        }
        return  true;
    }

    /**
     * Check if the mail is valid
     *
     * @param mail              String mail to be tested
     * @return                  TRUE if valid
     *                          FALSE otherwise
     */
    public boolean isValidMail(String mail){
        if (mail == null || mail.equals("")){
            return false;
        }
        int arobaseCounter = 0;
        for (int i = 0; i < mail.length(); i++){
            if (mail.charAt(i) == ' ') return false;
            else if (mail.charAt(i) == '@'){
                arobaseCounter++;
            }
        }
        return arobaseCounter == 1;
    }
}
