package Controller;

import Model.Project;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * Object used to display the projects on the TableView in the Project Selection Screen.
 * Each project is associated with a ProjectDisplay, which contains its information such as the title,
 * the date of modification and the owner, as well as 2 buttons (Rename and Share) and a checkbox.
 */
public class ProjectDisplay {

    private final SimpleStringProperty owner;
    private final SimpleStringProperty date;
    private final CheckBox checkBox;
    private final Project project;
    private final Button renameButton;
    private final Button shareButton;
    private final Label title;

    /**
     * Constructor of ProjectDisplay based on a project.
     * @param project project that needs to be viewed in the TableView and therefore converted
     *                to a ProjectDisplay
     */
    public ProjectDisplay(Project project) {
        this.title = new Label(project.getTitle());
        this.owner = new SimpleStringProperty(project.getCreatorUsername());
        this.date = new SimpleStringProperty(project.getLastModificationDate().toString());
        this.project = project;
        this.checkBox = new CheckBox();
        this.renameButton = new Button("Rename");
        this.shareButton = new Button("Share");
    }

    public String getOwner() {
        return owner.get();
    }

    public SimpleStringProperty ownerProperty() {
        return owner;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public Label getTitle() {
        return title;
    }

    public Button getRenameButton() {
        return renameButton;
    }

    public Button getShareButton() {
        return shareButton;
    }

    public Project getProject() {
        return project;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public SimpleStringProperty getSimpleStringPropertyTitle() {
        return title;
    }
}
