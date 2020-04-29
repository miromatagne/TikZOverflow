package Model;


import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class Project {

    private CheckBox checkBox;
    private final SimpleStringProperty  title;
    private final SimpleStringProperty owner;
    private final SimpleStringProperty  date;

    public Project(String title, String owner, String date) {
        this.title = new SimpleStringProperty(title);
        this.owner = new SimpleStringProperty(owner);
        this.date = new SimpleStringProperty(date);
        this.checkBox = new CheckBox();

    }
    public CheckBox getCheckBox() {
        return checkBox;
    }

    public String getTitle() {
        return title.get();
    }

    public String getOwner() {
        return owner.get();
    }

    public String getDate() {
        return date.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setCheckBox(CheckBox checkbox) {
        this.checkBox = checkbox;
    }
}
