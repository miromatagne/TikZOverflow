package Controller;

import Model.Shapes.FactoryShape;
import Model.Shapes.Shape;
import View.MainPageController;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ShapeMenuController {
    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    public void addShape(int idCurrent, ArrayList<Float> allData, Color color){
        Shape shape = FactoryShape.getInstance(idCurrent,allData, color);
        mainPageController.addShape(shape);
        mainPageController.closePopup();
    }
}
