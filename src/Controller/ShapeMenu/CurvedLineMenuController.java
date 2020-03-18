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
 * This controller is used to handle the arrow menu, get the inputs from the texts fields and clear them when it has to
 */

public class CurvedLineMenuController extends MenuController implements Initializable{

    @FXML private TextField xOriginTextField;
    @FXML private TextField yOriginTextField;
    @FXML private TextField xDestinationTextField;
    @FXML private TextField yDestinationTextField;
    @FXML private TextField strokeWidthTextField;
    @FXML private TextField curveRadiusTextField;
    @FXML private ColorPicker colorPicker;

    private static ArrayList<TextField> allTextFields = new ArrayList<>();



    /**
     * Function called when a new menu is selected. It clears the different fields
     */
    @Override
    public void update() {
        for (TextField textField : allTextFields) {
            textField.setText("");
        }
        colorPicker.setValue(Color.WHITE);
    }


    /**
     * Initialization by adding the different textfield to an array list
     * @param url               URL (not used)
     * @param resourceBundle    ResourceBundle(not used)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextFields.add(xOriginTextField);
        allTextFields.add(yOriginTextField);
        allTextFields.add(xDestinationTextField);
        allTextFields.add(yDestinationTextField);
        allTextFields.add(strokeWidthTextField);
        allTextFields.add(curveRadiusTextField);
    }

    @Override
    public ArrayList<String> getAllFields() {
        ArrayList<String> returnValue = new ArrayList<>();
        for (TextField textField : allTextFields) {
            returnValue.add(textField.getText());
        }
        return returnValue;
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
