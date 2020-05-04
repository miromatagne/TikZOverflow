package Model;

import Model.Exceptions.*;

import java.io.*;

/**
 * This class is used to handle interactions with files. It creates directories and writes the saves
 */

public class FileHandler {

    private static final String DEFAULT_DIRECTORY = "save user";
    private static int ERRORS_COUNTER = 0;
    private static String ERRORS = "";
    private String saveUserDirectory = "";
    private String saveProjectDirectory = "";
    private final String saveUserFormat = ".txt";

    /**
     * Create a new instance of file handler
     *
     * @throws FileHandlerConstructorException if construction failed
     */
    public FileHandler() throws FileHandlerConstructorException {
        try {
            setupSaveUserDirectory(DEFAULT_DIRECTORY);
        } catch (SetupDirectoryException e) {
            throw new FileHandlerConstructorException(e);
        }
    }

    /**
     * Create a new instance of file handler
     *
     * @param saveUserDirectory path of the directory where users will be saved
     * @throws FileHandlerConstructorException if construction failed
     */
    public FileHandler(String saveUserDirectory) throws FileHandlerConstructorException {
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
     * Setups the directory for projects' saves
     *
     * @param saveProjectDirectory Path to the directory projects's saves
     * @throws SetupDirectoryException if setup failed
     */

    public void setupSaveProjectDirectory(String saveProjectDirectory) throws SetupDirectoryException {
        try {
            if (saveProjectDirectory.equals("")) {
                return;
            }
            this.saveProjectDirectory = saveProjectDirectory;
            File file = new File(saveProjectDirectory);
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
            makeTexFile(user, "");
            writeSave(user, saveFile);
        } catch (LatexWritingException | SaveWritingException e) {
            throw new SaveUserCreationException(e);
        }
    }

    /**
     * Creates a .tex file for every new user, and updates it with the new source code
     * when compiling.
     *
     * @param user       User for whom the .tex file will be created/updated
     * @param sourceCode String from the compiling text area
     * @throws LatexWritingException when the text has not be written successfully in the tex file
     */
    public void makeTexFile(User user, String sourceCode) throws LatexWritingException {
        try {
            setupSaveProjectDirectory("./Latex/");
            File texFile = new File(saveProjectDirectory + user.getUsername() + ".tex");
            if (texFile.exists()) {
                writeInFile(texFile, sourceCode);
            } else {
                File template_file = new File("./Latex/template.txt");
                String temp, text = "";
                BufferedReader br;
                br = new BufferedReader(new FileReader(template_file));
                while ((temp = br.readLine()) != null) {
                    text = text.concat(temp + '\n');
                }
                writeInFile(texFile, text);
            }
        } catch (IOException | SetupDirectoryException e) {
            throw new LatexWritingException(e);
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
            File file = new File(saveUserDirectory + "/" + user.getUsername() + saveUserFormat);
            if (file.exists()) {
                writeSave(user, file);
            }
        } catch (SaveWritingException e) {
            throw new SaveUserException(e);
        }
    }


    /**
     * Writes text into file.
     *
     * @param file File written
     * @param text Content to write
     * @throws IOException when a IO error occurs
     */
    public void writeInFile(File file, String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            bw.write(text);
        } catch (IOException e){
            throw e;
        }
    }


    /**
     * Read the text in a File
     *
     * @param path File path.
     * @return text in the file
     * @throws IOException if error in IO interactions
     */
    public String readInFile(String path) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(path)))){
            String textInFile;
            StringBuilder builder = new StringBuilder();
            while ((textInFile = buffer.readLine()) != null) {
                builder.append(textInFile).append("\n");
            }

            return builder.toString();
        } catch (IOException e){
            throw e;
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
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split(":");
                if (lineArray[0].equals(flag)) {
                    if (!lineArray[1].equals("")) {
                        return lineArray[1];
                    }
                }
            }
            System.out.println("No information for the flag : " + flag + ", in file " + file.getPath());
            return "";
        } catch (IOException e){
            throw e;
        }

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
        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(path)))) {
            ERRORS = "";
            ERRORS_COUNTER = 0;
            String[] words;
            String line;
            String input = "Latex/" + username + ".tex";

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
