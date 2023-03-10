package View.ShapeMenu.AllShapeMenus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This controller is used to handle the curved line menu, get the inputs from the texts fields and clear them when it
 * has to.
 */

public class CurvedLineMenuController extends MenuController implements Initializable {

    @FXML
    private TextField xOriginTextField;
    @FXML
    private TextField yOriginTextField;
    @FXML
    private TextField xDestinationTextField;
    @FXML
    private TextField yDestinationTextField;
    @FXML
    private TextField strokeWidthTextField;
    @FXML
    private TextField curveOutAngleTextField;
    @FXML
    private TextField curveInAngleTextField;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField labelTextField;

    private final List<TextField> allTextFields = new ArrayList<>();


    /**
     * Function called when a new menu is selected. It clears the different fields.
     */
    @Override
    public void update() {
        for (TextField textField : allTextFields) {
            textField.setText("");
            textField.setStyle("");
        }
        colorPicker.setValue(Color.WHITE);
        labelTextField.setText("");
        labelTextField.setStyle("");
    }


    /**
     * Initialization by adding the different textfield to an array list.
     *
     * @param url            URL (not used)
     * @param resourceBundle ResourceBundle(not used)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextFields.add(xOriginTextField);
        allTextFields.add(yOriginTextField);
        allTextFields.add(xDestinationTextField);
        allTextFields.add(yDestinationTextField);
        allTextFields.add(strokeWidthTextField);
        allTextFields.add(curveOutAngleTextField);
        allTextFields.add(curveInAngleTextField);
    }

    /**
     * Get information from all fields.
     *
     * @return list of all information
     */
    @Override
    public List<String> getAllFields() {
        ArrayList<String> returnValue = new ArrayList<>();
        for (TextField textField : allTextFields) {
            returnValue.add(textField.getText());
        }
        return returnValue;
    }

    @Override
    public List<TextField> getAllTextFields() {
        return allTextFields;
    }

    @Override
    public Color getColor() {
        return colorPicker.getValue();
    }

    @Override
    public TextField getLabel() {
        return labelTextField;
    }

}
