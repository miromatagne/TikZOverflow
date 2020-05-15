package Model;

/**
 * This class allows to check if the content of a String is number, alphanumeric or alpha.
 */

public class TextTypeChecker {
    /**
     * Check if the text given in parameter is a number (float).
     *
     * @param text text to be checked
     * @return TRUE if valid
     * FALSE otherwise
     */
    public boolean isValidNumber(String text) {
        if (text == null || text.equals("")) {
            return false;
        }
        String pattern = "^([0-9]+|[0-9]+\\.[0-9]+)$"; // we match integers or well-formed floats
        return text.matches(pattern);
    }

    /**
     * Checks if string is fully alphanumeric.
     *
     * @param field string to check
     * @return true if string is totally alphanumeric, 0 otherwise
     */
    public boolean isAlphaNumeric(String field) {
        String pattern = "^[a-zA-Z0-9]+$";
        return field.matches(pattern);
    }

    /**
     * Checks if string contains only letters.
     *
     * @param field string to check
     * @return true if string contains only letters, false otherwise
     */
    public boolean isAlpha(String field) {
        String pattern = "^[-a-zA-Z ]+$";
        return field.matches(pattern);
    }
}
