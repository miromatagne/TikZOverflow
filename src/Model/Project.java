package Model;

import java.util.ArrayList;
import java.util.Date;

public class Project {

    private int id;
    private String title;
    private User creator;
    private ArrayList<User> collaborators;
    private Date date;
    private String code;


    private static String DEFAULT_TITLE = "Unnamed";


    /**
     * New projects constructor
     *
     * @param id
     * @param creator
     */
    public Project(int id, User creator){
        this(id, creator, DEFAULT_TITLE, new Date());
    }

    /**
     * Existing projects constructor (made from a save)
     *
     * @param id
     * @param creator
     * @param title
     * @param date
     */
    public Project(int id, User creator, String title, Date date){
        collaborators = new ArrayList<>();
        setID(id);
        setCreator(creator);
        setTitle(title);
        setDate(date);
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

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void addCollaborator(User newCollaborator){
        collaborators.add(newCollaborator);
    }


    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getCreator() {
        return creator;
    }

    public ArrayList<User> getCollaborators() {
        return collaborators;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
