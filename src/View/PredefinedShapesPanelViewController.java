package View;

import Controller.PredefinedShapesPanelController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

public class PredefinedShapesPanelViewController extends ControllerSuperclass implements Initializable {

    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;
    final static int SQUARE = 5;

    PredefinedShapesPanelController predefinedShapesPanelController;

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       predefinedShapesPanelController = new PredefinedShapesPanelController();
    }

    private void circleClicked() {
        predefinedShapesPanelController.createShape(CIRCLE);
    }

    private void rectangleClicked() {
        predefinedShapesPanelController.createShape(RECTANGLE);
    }

    private void lineClicked() {
        predefinedShapesPanelController.createShape(LINE);
    }

    private void curvedLineClicked() {
        predefinedShapesPanelController.createShape(CURVED_LINE);
    }

    private void arrowClicked() {
        predefinedShapesPanelController.createShape(ARROW);
    }

    private void squareClicked() {
        predefinedShapesPanelController.createShape(SQUARE);
    }


}
