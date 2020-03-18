package Controller.ShapeMenu;

import Model.Shapes.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FactoryShapeTest {

    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;

    @Test
    void getInstance(){
        ArrayList<Float> data = new ArrayList<>();
        data.add((float) 1.2);
        data.add((float) 15.6);
        data.add((float) 102);
        data.add((float) 589);
        data.add((float) 145);
        data.add((float) 0.456);
        data.add((float) 40);
        Color color = Color.WHITE;

        //RECTANGLE
        Rectangle r = (Rectangle) FactoryShape.getInstance(RECTANGLE,data,color);
        assertEquals((float) 1.2,r.getXCenter());
        assertEquals((float) 15.6,r.getYCenter());
        assertEquals(Color.WHITE,r.getColor());
        assertEquals((float) 102,r.getHeight());
        assertEquals((float) 589,r.getWidth());
        assertEquals((float) 145,r.getOutlineThickness());

        //CIRCLE
        Circle c = (Circle) FactoryShape.getInstance(CIRCLE,data,color);
        assertEquals((float) 1.2,c.getXCenter());
        assertEquals((float) 15.6,c.getYCenter());
        assertEquals(Color.WHITE,c.getColor());
        assertEquals((float) 102,c.getRadius());

        //ARROW
        Arrow a = (Arrow) FactoryShape.getInstance(ARROW,data,color);
        assertEquals((float) 1.2,a.getxOrigin());
        assertEquals((float) 15.6,a.getyOrigin());
        assertEquals(Color.WHITE,a.getColor());
        assertEquals((float) 102,a.getxDestination());
        assertEquals((float) 589,a.getyDestination());
        assertEquals((float) 145,a.getStrokeWidth());
        assertEquals((float) 0.456,a.getArrowHeadLength());
        assertEquals((float) 40,a.getArrowHeadWidth());

        //CURVED LINE
        CurvedLine cl = (CurvedLine) FactoryShape.getInstance(CURVED_LINE,data,color);
        assertEquals((float) 1.2,cl.getxOrigin());
        assertEquals((float) 15.6,cl.getyOrigin());
        assertEquals(Color.WHITE,cl.getColor());
        assertEquals((float) 102,cl.getxDestination());
        assertEquals((float) 589,cl.getyDestination());
        assertEquals((float) 145,cl.getStrokeWidth());
        assertEquals((float) 0.456,cl.getCurvedRadius());

        //LINE
        Line l = (Line) FactoryShape.getInstance(LINE,data,color);
        assertEquals((float) 1.2,l.getxOrigin());
        assertEquals((float) 15.6,l.getyOrigin());
        assertEquals(Color.WHITE,l.getColor());
        assertEquals((float) 102,l.getxDestination());
        assertEquals((float) 589,l.getyDestination());
        assertEquals((float) 145,l.getStrokeWidth());


    }
}
