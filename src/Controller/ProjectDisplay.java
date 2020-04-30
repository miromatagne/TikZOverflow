package Controller;

import Model.Project;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ProjectDisplay {

    private final SimpleStringProperty owner;
    private final SimpleStringProperty date;
    private final CheckBox checkBox;
    private final Project project;
    private final Button renameButton;
    private final Button shareButton;

    private SimpleStringProperty title;

    public ProjectDisplay(Project project) {
        this.title = new SimpleStringProperty(project.getTitle());
        this.owner = new SimpleStringProperty(project.getCreatorUsername());
        this.date = new SimpleStringProperty(project.getDate().toString());
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

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
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
        this.title.set(title);
    }
}
