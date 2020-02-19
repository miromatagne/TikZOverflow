package Model;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class FileHandler {
    String saveUserDirectory = "";
    String saveUserFormat = ".txt";

    public boolean setupSaveUserDirectory(String saveUserDirectory){
        /*
         *  ------------------------------------------------------
         *  Parameter : Path to the directory users' saves
         *  Function : Setup the directory for users' saves
         *  Return value : - TRUE if the setup is made successfully
         *                 - FALSE otherwise
         *  ------------------------------------------------------
         */
        if (saveUserDirectory.equals("")){
            return false;
        }
        this.saveUserDirectory = saveUserDirectory;
        File file = new File(saveUserDirectory);
        return checkAndCreateSaveDirectory(file);
    }

    private boolean checkAndCreateSaveDirectory(File file){
        /*
         *  ------------------------------------------------
         *  Parameter : File created with path to the save_user directory
         *  Function : Check if the directory exists. Otherwise, create it.
         *  Return value : - TRUE if the directory already exists OR if the
         *                   creation of the directory is successful
         *                 - FALSE if the creation of the directory failed
         *  -----------------------------------------------
         */
        if (file == null){
            return false;
        }
        if(file.exists() && file.isDirectory()){
            return true;
        }
        return file.mkdir();
    }

    public boolean createUserSave(User user){
        /*
         * --------------------------------------------
         * Parameters : user to be saved in a text file
         * Function : Create a save corresponding to the user
         * Return value : TRUE if writing successfully
         *                FALSE otherwise
         * --------------------------------------------
         */
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
        //result+="projects:"+user.getLastName()+"\n";
        return writeInFile(file, text);
    }

    public boolean writeInFile(File file, String text) {
        /*
         * --------------------------------------------
         * Parameters : file : in which we will write
         *              text : content to be writed
         * Function : Write text in a file
         * Return value : TRUE if writing successfully
         *                FALSE otherwise
         * --------------------------------------------
         */
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

    public User getUserFromSave(String username) {
        /*
         *  ---------------------------------
         *  Parameter : Username, identifying users
         *  Function : Create a user from its username,
         *             and indeed, its save in the save_user directory
         *  Return value : User object (null object if the save file does not exist)
         *  ---------------------------------
         */
        User user = null;
        String temp;
        if (saveUserDirectory.equals("")) {
            return null;
        }
        File file = new File(saveUserDirectory+"/"+username+saveUserFormat);
        if (!file.exists()){
            //Error, the file does not exist
            return null;
        }
        user = new User();
        user.setUsername(username);
        setUserLastName(file, user);
        setUserFirstName(file, user);
        setUserMail(file, user);
        setUserPassword(file, user);
        return user;
    }

    public String getInformation(File file, String flag){
        /*
         * ----------------------------------------------
         * Parameters : - file, corresponding to the user's save file
         *              - flag, which give the information needed
         * Function : Search the information needed with the flag in
         *            the save file
         * Return value : - Information needed
         *                - "" if : empty file;
         *                          empty field
         * ----------------------------------------------
         */
        if (file == null || flag.equals("")){
            return "";
        }
        String line = "";
        String[] lineArray;
        if (file.length() == 0){
            System.out.println("File "+file.getPath()+" is empty");
            return "";
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null){
                lineArray = line.split(":");
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

    private void setUserLastName(File file, User user) {
        /*
         * --------------------------------
         * Parameters : file, corresponding to the user's save file
         *              user, object made from the save
         * Function : Fill the user's last name
         * Return value : void
         * --------------------------------
         */
        String temp;
        if (!(temp = getInformation(file, "last")).equals("")) {
            user.setLastName(temp);
        }
    }

    private void setUserFirstName(File file, User user) {
        /*
         * --------------------------------
         * Parameters : file, corresponding to the user's save file
         *              user, object made from the save
         * Function : Fill the user's first name
         * Return value : void
         * --------------------------------
         */
        String temp;
        if (!(temp = getInformation(file, "first")).equals("")) {
            user.setFirstName(temp);
        }
    }

    private void setUserMail(File file, User user) {
        /*
         * --------------------------------
         * Parameters : file, corresponding to the user's save file
         *              user, object made from the save
         * Function : Fill the user's mail
         * Return value : void
         * --------------------------------
         */
        String temp;
        if (!(temp = getInformation(file, "mail")).equals("")) {
            user.setMail(temp);
        }
    }

    private void setUserPassword(File file, User user) {
        /*
         * --------------------------------
         * Parameters : file, corresponding to the user's save file
         *              user, object made from the save
         * Function : Fill the user's password
         * Return value : void
         * --------------------------------
         */
        String temp;
        if (!(temp = getInformation(file, "password")).equals("")) {
            user.setPassword(temp);
        }
    }


}
