package Model;

import java.util.HashSet;

/**
 * This class allows to check different inputs from the user of the application
 */

public class FieldChecker {

    HashSet<Character> userCharCollection = new HashSet<>();
    HashSet<Character> alphaCharCollection = new HashSet<>();
    HashSet<Character> numericCharCollection = new HashSet<>();
    HashSet<Character> numericCollection = new HashSet<>();

    public FieldChecker() {
        setupFieldChecker();
    }

    /**
     * Setup the FieldChecker object by initializing char collections
     */
    public void setupFieldChecker() {
        for (int i = 48; i < 127; i++) { //ASCII TABLE
            if (i < 58) { //0-9
                userCharCollection.add((char) i);
                numericCharCollection.add((char) i);
                numericCollection.add((char) i);
            } else if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) { //A-Z and a-z
                alphaCharCollection.add((char) i);
                userCharCollection.add((char) i);
            }
        }
    }

    /**
     * Check if the username is valid
     *
     * @param username String mail to be tested
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidUsername(String username) {
        if (username == null || username.equals("")) {
            return false;
        }
        for (int i = 0; i < username.length(); i++) {
            if (!userCharCollection.contains(username.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the name is valid
     *
     * @param name String mail to be tested
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidName(String name) {
        if (name == null || name.equals("")) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            if ((!alphaCharCollection.contains(name.charAt(i))) && name.charAt(i) != '-' && name.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the mail is valid
     *
     * @param mail String mail to be tested
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidMail(String mail) {
        if (mail == null || mail.equals("")) {
            return false;
        }
        int arobaseCounter = 0;
        for (int i = 0; i < mail.length(); i++) {
            if (mail.charAt(i) == ' ') {
                return false;
            }
            else if (mail.charAt(i) == '@') {
                arobaseCounter++;
            }
        }
        return arobaseCounter == 1;
    }

    /**
     * Check if all the fields are ok to create a new account
     *
     * @param username             username
     * @param firstName            first name
     * @param lastName             last name
     * @param mail                 mail
     * @param password             password
     * @param passwordConfirmation passwordConfirmation
     * @return TRUE if creation successful
     * FALSE otherwise
     */
    public boolean isValidAccount(String username, String firstName, String lastName,
                                  String mail, String password, String passwordConfirmation) {
        if (isValidUsername(username) && isValidName(firstName) && isValidName(lastName) && isValidMail(mail)) {
            return password.equals(passwordConfirmation) && !password.equals("");
        }
        return false;
    }

    /**
     * Check if the text given in parameter is a number (float)
     *
     * @param text text to be checked
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidNumber(String text) {
        if (text == null || text.equals("")) {
            return false;
        }
        int pointCounter = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '.') {
                pointCounter++;
                if (i == 0 || i == text.length() - 1) {
                    return false;
                }
            } else if (!numericCollection.contains(text.charAt(i))) {
                return false;
            }
        }
        return pointCounter <= 1;
    }
}
