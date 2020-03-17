package Controller.ShapeMenu;

import Controller.ControllerSuperclass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RectangleMenuController extends MenuController implements Initializable{
    @FXML private TextField xPositionTextField, yPositionTextField, heightTextField, widthTextField;
    @FXML private ColorPicker colorPicker;
    private ArrayList<TextField> allTextFields = new ArrayList<>();

    @Override
    public void update() {
        for(int i = 0; i< allTextFields.size(); i++){
            allTextFields.get(i).setText("");
        }
        colorPicker.setValue(Color.valueOf("#FFFFFF"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextFields.add(xPositionTextField);
        allTextFields.add(yPositionTextField);
        allTextFields.add(heightTextField);
        allTextFields.add(widthTextField);
    }

    @Override
    public ArrayList<TextField> getAllTextFields() {
        return allTextFields;
    }

    @Override
    public Color getColor() {
        return colorPicker.getValue();
    }
}
