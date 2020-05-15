package Model;


import java.util.ArrayList;
import java.util.Date;


/**
 * This class models a project with all the characteristics needed to identify it and to work on.
 */

public class Project {

    private String title;
    private String creatorUsername;

    private ArrayList<String> collaboratorsUsernames;
    private Date creationDate;
    private Date lastModificationDate;
    private String code;
    private String path;

    /**
     * New project constructor.
     *
     * @param creatorUsername   creator username
     * @param path              path to the project save directory
     * @param title             title of the project
     */
    public Project( String creatorUsername, String path,String title){
        this(creatorUsername, title, new Date(), new Date(), new ArrayList<>(), path);
    }

    /**
     * Existing projects constructor (made from a save).
     *
     * @param creatorUsername      creator username
     * @param title                project title
     * @param creationDate         last date of modification
     * @param lastModificationDate date of the last modification
     * @param collaborators        list of collaborators
     * @param path                 project path
     */
    public Project(String creatorUsername, String title, Date creationDate, Date lastModificationDate, ArrayList<String> collaborators, String path){
        setCreatorUsername(creatorUsername);
        setTitle(title);
        setCreationDate(creationDate);
        setLastModificationDate(lastModificationDate);
        this.collaboratorsUsernames = collaborators;
        setCode(code);
        this.path=path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatorUsername(String creator) {
        this.creatorUsername = creator;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public void setLastModificationDate(Date lastModificationDate){
        this.lastModificationDate = lastModificationDate;
    }

    public void addCollaborator(String newCollaboratorUsername){
        if(!collaboratorsUsernames.contains(newCollaboratorUsername)) {
            collaboratorsUsernames.add(newCollaboratorUsername);
        }
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

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path=path;
    }
}
