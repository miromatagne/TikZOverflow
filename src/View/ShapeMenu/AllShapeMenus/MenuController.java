package View.ShapeMenu.AllShapeMenus;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Abstract class which allows to group all the shape menus controller into one type of instance.
 */

public abstract class MenuController {

    public static final String ERROR_STYLE =  "-fx-text-box-border: red";
    public static final String DEFAULT_STYLE =  "";

    public abstract void update();

    public abstract Color getColor();

    public abstract TextField getLabel();

    public abstract List<String> getAllFields();

    public abstract List<TextField> getAllTextFields();

    /**
     * Sets a style on a given field.
     *
     * @param i index of the field in the ArrayList
     * @param style style to set to the field. Can be ERROR_STYLE or DEFAULT_STYLE
     */
    public void setFieldStyle(int i, String style){
        getAllTextFields().get(i).setStyle(style);
    }

    public String getText() {
        return getLabel().getText();
    }
}
