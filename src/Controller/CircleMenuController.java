package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CircleMenuController extends ControllerSuperclass implements Initializable {

    @FXML TextField xPositionTextField;
    @FXML TextField yPositionTextField;
    @FXML TextField radiusTextField;
    @FXML ColorPicker colorPicker;

    private static ArrayList<TextField> allTextFields = new ArrayList<>();



    @Override
    public void update() {
        for (int i = 0; i < allTextFields.size();i++){
            allTextFields.get(i).setText("");
        }
        colorPicker.setValue(Color.WHITE);
    }


    @Override
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

    public Color getColor() {
        return colorPicker.getValue();
    }
}
