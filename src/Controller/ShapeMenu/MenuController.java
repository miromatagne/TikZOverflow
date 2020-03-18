package Controller.ShapeMenu;

import Controller.ControllerSuperclass;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Abstract class which allows to group all the shape menus controller into one type of instance
 */

abstract public class MenuController extends ControllerSuperclass {
    public abstract Color getColor();
    public abstract ArrayList<String> getAllFields();
    public abstract ArrayList<TextField> getAllTextFields();
}
