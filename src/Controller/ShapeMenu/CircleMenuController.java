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

/**
 * This controller is used to handle the circle menu, get the inputs from the texts fields and clear them when it has to
 */

public class CircleMenuController extends ControllerSuperclass implements Initializable {

    @FXML private TextField xPositionTextField;
    @FXML private TextField yPositionTextField;
    @FXML private TextField radiusTextField;
    @FXML private ColorPicker colorPicker;

    private static ArrayList<TextField> allTextFields = new ArrayList<>();



    @Override
    /**
     * Function called when a new menu is selected. It clears the different fields
     */
    public void update() {
        for (int i = 0; i < allTextFields.size();i++){
            allTextFields.get(i).setText("");
        }
        colorPicker.setValue(Color.WHITE);
    }


    @Override
    /**
     * Initialization by adding the different textfield to an array list
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextFields.add(xPositionTextField);
        allTextFields.add(yPositionTextField);
        allTextFields.add(radiusTextField);
    }

    public String getXPosition() {
        return xPositionTextField.getText();
    }

    public String getYPosition() {
        return yPositionTextField.getText();
    }

    public String getRadius() {
        return radiusTextField.getText();
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
