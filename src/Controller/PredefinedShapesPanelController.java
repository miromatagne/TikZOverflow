package Controller;

import Model.Shapes.ShapeFactory;

/**
  This class is the controller which handle messages
  from the predefined panel defined in the view to the creation of a shape instance
 */


public class PredefinedShapesPanelController {

    /**
     * Create a shape from the id given
     * @param shapeId       id of the shape
     */
    public void createShape(int shapeId) {
        ShapeFactory.getDefaultInstance(shapeId);
    }

}
