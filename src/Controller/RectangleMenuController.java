package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RectangleMenuController extends ControllerSuperclass implements Initializable {
    @FXML private TextField xPositionTextField, yPositionTextField, heightTextField, widthTextField;
    @FXML private ColorPicker colorPicker;
    private ArrayList<TextField> allTextField = new ArrayList<>();

    @Override
    public void update() {
        for(int i=0;i<allTextField.size();i++){
            allTextField.get(i).setText("");
        }
        colorPicker.setValue(Color.valueOf("#FFFFFF"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextField.add(xPositionTextField);
        allTextField.add(yPositionTextField);
        allTextField.add(heightTextField);
        allTextField.add(widthTextField);
    }

    public String getXPosition() {
        return xPositionTextField.getText();
    }

    public String getYPosition() {
        return yPositionTextField.getText();
    }

    public String getHeight() {
        return heightTextField.getText();
    }

    public String getWidth() {
        return widthTextField.getText();
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }
}
