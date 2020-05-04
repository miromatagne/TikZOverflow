package Model;

import java.io.*;

public class FileHandler {
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
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.close();
        fw.close();
    }

    /**
     * Read the text in a File
     *
     * @param file file.
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
        // add close

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
