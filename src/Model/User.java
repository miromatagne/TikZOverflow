package Model;

import java.util.ArrayList;

/**
 * Class to model a user
 */

public class User {

    private String username = "";
    private String lastName = "";
    private String firstName = "";
    private String password = "";
    private String mail = "";
    private ArrayList<Integer> projectIDs = new ArrayList<Integer>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Integer> getProjectIDs() {
        return projectIDs;
    }

    public void setProjectIDs(ArrayList<Integer> projectIDs) {
        this.projectIDs = projectIDs;
    }
}
