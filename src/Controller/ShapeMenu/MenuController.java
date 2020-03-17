package Controller.ShapeMenu;

import Controller.ControllerSuperclass;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;

abstract public class MenuController extends ControllerSuperclass {
    public abstract Color getColor();
    public abstract ArrayList<TextField> getAllTextFields();
}
