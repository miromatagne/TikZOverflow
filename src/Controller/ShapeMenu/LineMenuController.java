package Controller.ShapeMenu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller is used to handle the line menu, get the inputs from the texts fields and clear them when it has to
 */

public class LineMenuController extends MenuController implements Initializable {
    @FXML private TextField xOriginTextField;
    @FXML private TextField yOriginTextField;
    @FXML private TextField xDestinationTextField;
    @FXML private TextField yDestinationTextField;
    @FXML private TextField strokeWidthTextField;
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
        allTextFields.add(xOriginTextField);
        allTextFields.add(yOriginTextField);
        allTextFields.add(xDestinationTextField);
        allTextFields.add(yDestinationTextField);
        allTextFields.add(strokeWidthTextField);
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
