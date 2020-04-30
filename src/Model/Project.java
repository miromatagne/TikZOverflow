package Model;

import java.util.ArrayList;
import java.util.Date;


/**
 * This class models a project with all the characteristics needed to identify it and to work on.
 */

public class Project {

    private int id;
    private String title;
    private String creatorUsername;
    private ArrayList<String> collaboratorsUsernames;
    private Date date;
    private String code;
    private String path;

    private final static String DEFAULT_TITLE = "Unnamed";
    private final static String DEFAULT_CODE =    "\\node (h) at (0,0) {Hello};\n" +
                                            "\\node (w) at (2,3) {World};\n" +
                                            "\\draw (h) edge (w);";

    /**
     * New project constructor
     *
     * @param id                id of the project
     * @param creatorUsername   creator username
     * @param path              path to the project save directory
     */
    public Project(int id, String creatorUsername, String path){
        this(id, creatorUsername, DEFAULT_TITLE, new Date(), new ArrayList<>(), path);
    }

    /**
     * Existing projects constructor (made from a save)
     *
     * @param id                project id
     * @param creatorUsername   creator username
     * @param title             project title
     * @param date              last date of modification
     * @param collaborators     list of collaborators
     */
    public Project(int id, String creatorUsername, String title, Date date, ArrayList<String> collaborators, String path){
        setID(id);
        setCreatorUsername(creatorUsername);
        setTitle(title);
        setDate(date);
        this.collaboratorsUsernames = collaborators;
        setCode(code);
        this.path=path;
    }


    public void setID(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatorUsername(String creator) {
        this.creatorUsername = creator;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void addCollaborator(String newCollaboratorUsername){
        collaboratorsUsernames.add(newCollaboratorUsername);
    }


    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public ArrayList<String> getCollaboratorsUsernames() {
        return collaboratorsUsernames;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Date getDate() {
        return date;
    }

    public String getPath() {
        return path;
    }

}
