package Model;

import java.io.*;

public class FileHandler {
    private String saveUserDirectory = "";
    private String saveUserFormat = ".txt";

    /**
     *  Setups the directory for users' saves.
     *
     *  @param  saveUserDirectory   Path to the directory users' saves
     *  @return                     TRUE if the setup is made successfully
     *                              FALSE otherwise
     */
    public boolean setupSaveUserDirectory(String saveUserDirectory){
        if (saveUserDirectory == null || saveUserDirectory.equals("")){
            return false;
        }
        this.saveUserDirectory = saveUserDirectory;
        File file = new File(saveUserDirectory);
        return checkAndCreateSaveDirectory(file);
    }

    /**
     *  Checks if the directory exists. Otherwise, creates it.
     *
     *  @param  file                File created with path to the save_user directory
     *  @return                     TRUE if the directory already exists OR if the
     *                              creation of the directory is successful
     *                              FALSE if the creation of the directory failed
     */
    private boolean checkAndCreateSaveDirectory(File file){
        if (file == null){
            return false;
        }
        if(file.exists() && file.isDirectory()){
            return true;
        }
        return file.mkdir();
    }

    /**
     *  Creates a save corresponding to given user.
     *
     *  @param  user                User to be saved in a text file
     *  @return                     TRUE if writing successful
     *                              FALSE otherwise
     */
    public boolean createUserSave(User user){
        if (saveUserDirectory.equals("")) {
            return false;
        }
        File file = new File(saveUserDirectory+"/"+user.getUsername()+saveUserFormat);
        if (file.exists()){
            //Error, the file does already exist
            return false;
        }
        String text="";
        text+="last:"+user.getLastName()+"\n";
        text+="first:"+user.getFirstName()+"\n";
        text+="username:"+user.getUsername()+"\n";
        text+="mail:"+user.getMail()+"\n";
        text+="password:"+user.getPassword()+"\n";

        return writeInFile(file, text);
    }

    /**
     *  Writes text into file.
     *
     *  @param  file                File written
     *  @param  text                Content to write
     *  @return                     TRUE if writing successful
     *                              FALSE otherwise
     */
    public boolean writeInFile(File file, String text) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.close();
            return true;
        }
        catch (IOException e){
            System.err.format("IOException: %s%n", e);
        }
        return false;
    }

    /**
     * Creates an user from its username and its save in the save_user directory.
     *
     * @param username              Username (identifying users)
     * @return                      User created. Null if save file does not exist.
     */
    public User getUserFromSave(String username) {
        if (saveUserDirectory.equals("")) {
            return null;
        }
        File file = new File(saveUserDirectory+"/"+username+saveUserFormat);
        if (!file.exists()){
            //Error, the file does not exist
            return null;
        }
        User user = new User();
        user.setUsername(username);
        setUserLastName(file, user);
        setUserFirstName(file, user);
        setUserMail(file, user);
        setUserPassword(file, user);
        return user;
    }

    /**
     * Searches the information needed with the given save file flag.
     *
     * @param file                  File corresponding to user save file
     * @param flag                  Flag to extract the information from
     * @return                      Information needed, or empty string if empty file/flag
     */
    private String getInformation(File file, String flag){
        if (file == null || flag.equals("")){
            return "";
        }
        if (file.length() == 0){
            System.out.println("File "+file.getPath()+" is empty");
            return "";
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                String[] lineArray = line.split(":");
                if(lineArray[0].equals(flag)){
                    if (!lineArray[1].equals("")){
                        return lineArray[1];
                    }
                }
            }
            br.close();
        }
        catch (IOException e){
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("No information for the flag : "+flag+", in file "+file.getPath());
        return "";
    }

    /**
     * Fills user last name from file.
     *
     * @param file                  File corresponding to user save file
     * @param user                  User whose last name is set
     */
    private void setUserLastName(File file, User user) {
        String temp;
        if (!(temp = getInformation(file, "last")).equals("")) {
            user.setLastName(temp);
        }
    }

    /**
     * Fills user first name from file.
     *
     * @param file                  File corresponding to user save file
     * @param user                  User whose first name is set
     */
    private void setUserFirstName(File file, User user) {
        String temp;
        if (!(temp = getInformation(file, "first")).equals("")) {
            user.setFirstName(temp);
        }
    }

    /**
     * Fills user mail from file.
     *
     * @param file                  File corresponding to user save file
     * @param user                  User whose mail is set
     */
    private void setUserMail(File file, User user) {
        String temp;
        if (!(temp = getInformation(file, "mail")).equals("")) {
            user.setMail(temp);
        }
    }

    /**
     * Fills user password from file.
     * @param file                  File corresponding to user save file
     * @param user                  User whose password is set
     */
    private void setUserPassword(File file, User user) {
        String temp;
        if (!(temp = getInformation(file, "password")).equals("")) {
            user.setPassword(temp);
        }
    }


}
