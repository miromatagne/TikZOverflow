package View.ShapeMenu.AllShapeMenus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller is used to handle the rectangle menu, get the inputs from the texts fields and clear them when it
 * has to.
 */

public class RectangleMenuController extends MenuController implements Initializable {
    @FXML
    private TextField xPositionTextField;
    @FXML
    private TextField yPositionTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private TextField widthTextField;
    @FXML
    private Slider thicknessSlider;
    @FXML
    private TextField thicknessValue;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField labelTextField;

    private final ArrayList<TextField> allTextFields = new ArrayList<>();

    private static final double THICKNESS_DEFAULT_VALUE = 50;

    /**
     * Function called when a new menu is selected. It clears the different fields.
     */
    @Override
    public void update() {
        for (TextField textField : allTextFields) {
            textField.setText("");
            textField.setStyle("");
        }
        thicknessSlider.setValue(THICKNESS_DEFAULT_VALUE);
        colorPicker.setValue(Color.WHITE);
        labelTextField.setText("");
        labelTextField.setStyle("");
    }

    /**
     * Initialization by adding the different textfield to an array list and adding a listener for slider to see
     * it value.
     *
     * @param url            URL (not used)
     * @param resourceBundle ResourceBundle(not used)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allTextFields.add(xPositionTextField);
        allTextFields.add(yPositionTextField);
        allTextFields.add(heightTextField);
        allTextFields.add(widthTextField);
        thicknessValue.setText(String.format("%.1f", thicknessSlider.getValue()));
        thicknessSlider.valueProperty().addListener((ov, old_val, new_val) -> thicknessValue.setText(String.format("%.1f", new_val.floatValue())));
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
        returnValue.add(Double.toString(thicknessSlider.getValue()));
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
