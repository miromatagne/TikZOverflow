package Model;

import Controller.Session;
import Model.Exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class is used to handle interactions with files. It creates directories and writes the saves
 */

public class UserHandler extends FileHandler{

    private static final String DEFAULT_DIRECTORY = "save user";
    private static int ERRORS_COUNTER = 0;
    private static String ERRORS = "";
    private String saveUserDirectory = "";
    private final String saveUserFormat = ".txt";


    public UserHandler() {
    }

    /**
     * Create a new instance of file handler
     *
     * @param saveUserDirectory path of the directory where users will be saved
     * @throws FileHandlerConstructorException if construction failed
     */
    public UserHandler(String saveUserDirectory) throws FileHandlerConstructorException {
        try {
            setupSaveUserDirectory(saveUserDirectory);
        } catch (SetupDirectoryException e) {
            throw new FileHandlerConstructorException(e);
        }
    }


    /**
     * Setups the directory for users' saves.
     *
     * @param saveUserDirectory Path to the directory users' saves
     * @throws SetupDirectoryException if setup failed
     */
    public void setupSaveUserDirectory(String saveUserDirectory) throws SetupDirectoryException {
        try {
            if (saveUserDirectory == null || saveUserDirectory.equals("")) {
                return;
            }
            this.saveUserDirectory = saveUserDirectory;
            File file = new File(saveUserDirectory);
            checkAndCreateSaveDirectory(file);
        } catch (DirectoryCreationException e) {
            throw new SetupDirectoryException(e);
        }
    }

    /**
     * Checks if the directory exists. Otherwise, creates it.
     *
     * @param file File created with path to the save_user directory
     * @throws DirectoryCreationException if directory creation failed
     */
    private void checkAndCreateSaveDirectory(File file) throws DirectoryCreationException {
        if (file.exists() && file.isDirectory()) {
            return;
        }
        if (!file.mkdir()) {
            throw new DirectoryCreationException();
        }
    }

    /**
     * Get the save text from the user
     *
     * @param user User to be handle
     * @return The user save in a string
     */
    private String getSaveTextFromUser(User user) {
        String text = "";
        text += "last:" + user.getLastName() + "\n";
        text += "first:" + user.getFirstName() + "\n";
        text += "username:" + user.getUsername() + "\n";
        text += "mail:" + user.getMail() + "\n";
        text += "password:" + user.getPassword() + "\n";
        ArrayList<String> projectPaths = user.getProjectPaths();
        text += "projects:" + String.join(", ", projectPaths) + "\n";
        return text;
    }

    /**
     * Creates a save corresponding to given user.
     *
     * @param user User to be saved in a text file
     * @throws SaveUserCreationException when the creation of the user save fails
     */
    public void createUserSave(User user) throws SaveUserCreationException {
        if (saveUserDirectory.equals("")) {
            return;
        }
        try {
            File saveFile = new File(saveUserDirectory + "/" + user.getUsername() + saveUserFormat);
            if (saveFile.exists()) {
                //Error, the file does already exist
                return;
            }
            writeSave(user, saveFile);
        } catch (SaveWritingException e) {
            throw new SaveUserCreationException(e);
        }
    }

    /**
     * Writes a user's data in his save file.
     *
     * @param user User to be saved.
     * @param file File in which to write.
     * @throws SaveWritingException when the save could not be written
     */
    private void writeSave(User user, File file) throws SaveWritingException {
        try {
            String text = getSaveTextFromUser(user);
            writeInFile(file, text);
        } catch (IOException e) {
            throw new SaveWritingException(e);
        }
    }

    /**
     * Save the user in the user directory
     *
     * @param user user contain all the new data to be saved
     * @throws SaveUserException when the user save could not be written
     */
    public void saveUser(User user) throws SaveUserException {
        if (saveUserDirectory.equals("")) {
            return;
        }
        try {
            File file = new File(saveUserDirectory + File.separator + user.getUsername() + saveUserFormat);
            if (file.exists()) {
                writeSave(user, file);
            }
        } catch (SaveWritingException e) {
            throw new SaveUserException(e);
        }
    }

    /**
     * Creates a user from its username and its save in the save_user directory.
     *
     * @param username Username (identifying users)
     * @return User created
     * @throws UserFromSaveCreationException when then user can not be created from the save corresponding to the username given
     */
    public User getUserFromSave(String username) throws UserFromSaveCreationException {
        try {
            File file = new File(saveUserDirectory + "/" + username + saveUserFormat);
            User user = new User();
            user.setUsername(username);
            setUserLastName(file, user);
            setUserFirstName(file, user);
            setUserMail(file, user);
            setUserPassword(file, user);
            setUserProjectPaths(file, user);
            return user;
        } catch (IOException e) {
            throw new UserFromSaveCreationException(e);
        }
    }

    /**
     * Check if the save user file exists
     *
     * @param username username of the user
     * @return TRUE if file exists, FALSE otherwise
     */
    public boolean saveUserExists(String username) {
        File file = new File(saveUserDirectory + "/" + username + saveUserFormat);
        return file.exists();
    }

    /**
     * Searches the information needed with the given save file flag.
     *
     * @param file File corresponding to user save file
     * @param flag Flag to extract the information from
     * @return Information needed, or empty string if empty file/flag
     * @throws IOException if any IO error interaction occurs
     */
    private String getInformation(File file, String flag) throws IOException {
        if (file == null || flag.equals("")) {
            return "";
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] lineArray = line.split(":");
            if (lineArray[0].equals(flag)) {
                if (lineArray.length>1 &&!lineArray[1].equals("")) {
                    return lineArray[1];
                }
            }
        }
        br.close();
        System.out.println("No information for the flag : " + flag + ", in file " + file.getPath());
        return "";
    }

    /**
     * Fills user last name from file.
     *
     * @param file File corresponding to user save file
     * @param user User whose last name is set
     * @throws IOException if any IO error interaction occurs
     */
    private void setUserLastName(File file, User user) throws IOException {
        String temp;
        if (!(temp = getInformation(file, "last")).equals("")) {
            user.setLastName(temp);
        }
    }

    /**
     * Fills user projects from file.
     *
     * @param file File corresponding to user save file
     * @param user User whose projects are set
     * @throws IOException if any IO error interaction occurs
     */
    private void setUserProjectPaths(File file, User user) throws IOException {
        String temp;
        if (!(temp = getInformation(file, "projects")).equals("")) {
            String[] projectPathArray = temp.split(",");
            ArrayList<String> projectPaths = new ArrayList<>(Arrays.asList(projectPathArray));
            user.setProjectPaths(projectPaths);
        }
    }

    /**
     * Fills user first name from file.
     *
     * @param file File corresponding to user save file
     * @param user User whose first name is set
     * @throws IOException if any IO error interaction occurs
     */
    private void setUserFirstName(File file, User user) throws IOException {
        String temp;
        if (!(temp = getInformation(file, "first")).equals("")) {
            user.setFirstName(temp);
        }
    }

    /**
     * Fills user mail from file.
     *
     * @param file File corresponding to user save file
     * @param user User whose mail is set
     * @throws IOException if any IO error interaction occurs
     */
    private void setUserMail(File file, User user) throws IOException {
        String temp;
        if (!(temp = getInformation(file, "mail")).equals("")) {
            user.setMail(temp);
        }
    }

    /**
     * Fills user password from file.
     *
     * @param file File corresponding to user save file
     * @param user User whose password is set
     * @throws IOException if any IO error interaction occurs
     */
    private void setUserPassword(File file, User user) throws IOException {
        String temp;
        if (!(temp = getInformation(file, "password")).equals("")) {
            user.setPassword(temp);
        }
    }

    /**
     * Get the errors in the compiler
     *
     * @return string with all the errors that the user let in the compiler
     */
    public String getErrors() {
        return ERRORS;
    }

    /**
     * Get the counter of errors in the compiler
     *
     * @return quantity of errors that occur in the compiler
     */
    public int getErrorsCounter() {
        return ERRORS_COUNTER;
    }

    /**
     * Find the errors that the user has written in the compiler
     *
     * @param path     the path to the log file which contains all information about the last compilation
     *                 that we made
     * @param username each error give the username so we need it to filter errors from other information
     * @throws LogErrorException If there was an error while reading the file.
     */
    public void errorLogs(String path, String username) throws LogErrorException {
        try {
            ERRORS = "";
            ERRORS_COUNTER = 0;
            File file = new File(path);
            String[] words;
            FileReader fileReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fileReader);
            String line;
            String input = Session.getInstance().getCurrentProject().getPath() + Session.getInstance().getCurrentProject().getTitle() + ".tex";

            while ((line = buffer.readLine()) != null) {
                words = line.split(":");
                for (String word : words) {
                    if (word.equals(input)) {
                        ERRORS_COUNTER++;
                        for (int i = 1; i < words.length; i++) {
                            if (i == 1) {
                                ERRORS += "line ";
                            }
                            ERRORS += words[i];
                            if (i < words.length - 1) {
                                ERRORS += ":";
                            } else {
                                ERRORS += "\n";
                            }
                        }
                    } else if (word.equals("*** (job aborted, no legal \\end found)")) {
                        ERRORS_COUNTER++;
                        ERRORS += "*** Missing '\\begin{document}' and/or '\\end{document}'";
                    }
                }
            }
        } catch (IOException e) {
            throw new LogErrorException(e);
        }
    }


}
