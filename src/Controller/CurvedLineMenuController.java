package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CurvedLineMenuController extends ControllerSuperclass implements Initializable {

    @FXML TextField xOriginTextField;
    @FXML TextField yOriginTextField;
    @FXML TextField xDestinationTextField;
    @FXML TextField yDestinationTextField;
    @FXML TextField strokeWidthTextField;
    @FXML TextField curvedRadiusTextField;
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
        allTextFields.add(xOriginTextField);
        allTextFields.add(yOriginTextField);
        allTextFields.add(xDestinationTextField);
        allTextFields.add(yDestinationTextField);
        allTextFields.add(strokeWidthTextField);
        allTextFields.add(curvedRadiusTextField);
    }
}
