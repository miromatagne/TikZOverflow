package View.ShapeMenu.AllShapeMenus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller is used to handle the line menu, get the inputs from the texts fields and clear them when it has to.
 */

public class LineMenuController extends MenuController implements Initializable {
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
    private ColorPicker colorPicker;
    @FXML
    private TextField labelTextField;

    private final ArrayList<TextField> allTextFields = new ArrayList<>();

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
    }


    /**
     * Get information from all fields.
     *
     * @return list of all information
     */
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

    @Override
    public TextField getLabel() {
        return labelTextField;
    }

}
