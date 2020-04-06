package Controller;

import Model.Shapes.ShapeFactory;

public class PredefinedShapesPanelController {

    public void createShape(int shapeId) {
        ShapeFactory.getDefaultInstance(shapeId);
    }

}
