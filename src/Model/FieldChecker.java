package Model;

/**
 * This class allows to check different inputs from the user of the application.
 */

public class FieldChecker {
    private final TextTypeChecker textTypeChecker = new TextTypeChecker();

    /**
     * Check if the username is valid.
     *
     * @param username String mail to be tested
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidUsername(String username) {
        if (username == null || username.equals("")) {
            return false;
        }
        return textTypeChecker.isAlphaNumeric(username);
    }

    /**
     * Check if the name is valid.
     *
     * @param name String mail to be tested
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidName(String name) {
        if (name == null || name.equals("")) {
            return false;
        }
        return textTypeChecker.isAlpha(name);
    }

    /**
     * Check if the mail is valid.
     *
     * @param mail String mail to be tested
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidMail(String mail) {
        if (mail == null || mail.equals("")) {
            return false;
        }
        String pattern = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]+(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)+$";
        return mail.matches(pattern);
    }

    /**
     * Check if all the fields are ok to create a new account.
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

}
