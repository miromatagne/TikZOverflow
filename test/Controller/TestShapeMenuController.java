package Controller;



import Model.Shapes.Circle;
import Model.Shapes.Rectangle;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestShapeMenuController {

    @Test
    public void createString(){
        ShapeMenuController shapeMenuController = new ShapeMenuController();
        Circle circle = new Circle(3, 1);
        Rectangle rectangle = new Rectangle(4, 6);
        rectangle.setHeight(3);
        rectangle.setColor(Color.BLUE);
        assertEquals("Added Circle of radius 0.0 , center (3.0,1.0) and thickness 0.0. Color : null.", shapeMenuController.createString(circle));
        assertEquals("Added Rectangle of height 3.0 and width 0.0 with a thickness of 0.0. Color : 0x0000ffff.", shapeMenuController.createString(rectangle));
    }


}
