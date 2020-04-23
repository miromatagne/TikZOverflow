package Model.Shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static Model.Shapes.ShapeFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class TestShapeFactory {

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
        String label = "Shape";

        //RECTANGLE
        Rectangle r = (Rectangle) ShapeFactory.getInstance(RECTANGLE,data,color,label);
        assertEquals((float) 1.2,r.getXCenter());
        assertEquals((float) 15.6,r.getYCenter());
        assertEquals(Color.WHITE,r.getColor());
        assertEquals((float) 102,r.getHeight());
        assertEquals((float) 589,r.getWidth());
        assertEquals((float) 145,r.getOutlineThickness());
        assertEquals("Shape", r.getLabel());

        //CIRCLE
        Circle c = (Circle) ShapeFactory.getInstance(CIRCLE,data,color,label);
        assertEquals((float) 1.2,c.getXCenter());
        assertEquals((float) 15.6,c.getYCenter());
        assertEquals(Color.WHITE,c.getColor());
        assertEquals((float) 102,c.getRadius());

        //ARROW
        Arrow a = (Arrow) ShapeFactory.getInstance(ARROW,data,color,label);
        assertEquals((float) 1.2,a.getxOrigin());
        assertEquals((float) 15.6,a.getyOrigin());
        assertEquals(Color.WHITE,a.getColor());
        assertEquals((float) 102,a.getxDestination());
        assertEquals((float) 589,a.getyDestination());
        assertEquals((float) 145,a.getStrokeWidth());
        assertEquals((float) 0.456,a.getArrowHeadLength());
        assertEquals((float) 40,a.getArrowHeadWidth());
        assertEquals("Shape", a.getLabel());

        //CURVED LINE
        CurvedLine cl = (CurvedLine) ShapeFactory.getInstance(CURVED_LINE,data,color,label);
        assertEquals((float) 1.2,cl.getxOrigin());
        assertEquals((float) 15.6,cl.getyOrigin());
        assertEquals(Color.WHITE,cl.getColor());
        assertEquals((float) 102,cl.getxDestination());
        assertEquals((float) 589,cl.getyDestination());
        assertEquals((float) 145,cl.getStrokeWidth());
        assertEquals((float) 0.456,cl.getCurvedOutAngle());
        assertEquals((float) 40,cl.getCurvedInAngle());

        //LINE
        Line l = (Line) ShapeFactory.getInstance(LINE,data,color,label);
        assertEquals((float) 1.2,l.getxOrigin());
        assertEquals((float) 15.6,l.getyOrigin());
        assertEquals(Color.WHITE,l.getColor());
        assertEquals((float) 102,l.getxDestination());
        assertEquals((float) 589,l.getyDestination());
        assertEquals((float) 145,l.getStrokeWidth());
        assertEquals("Shape", l.getLabel());

        //TRIANGLE
        Triangle t = (Triangle) ShapeFactory.getInstance(TRIANGLE,data,color, label);
        assertEquals((float) 1.2,t.getXCenter());
        assertEquals((float) 15.6,t.getYCenter());
        assertEquals(Color.WHITE,t.getColor());
        assertEquals((float) 102,t.getSide_a());
        assertEquals((float) 589,t.getSide_b());
        assertEquals((float) 145,t.getSide_c());
        assertEquals((float) 0.456,t.getOutlineThickness());



    }

    @Test
    void getDefaultInstance(){
        ArrayList<Float> data = new ArrayList<>();

        //RECTANGLE
        Rectangle r = (Rectangle) ShapeFactory.getDefaultInstance(RECTANGLE);
        assertEquals((float) 0,r.getXCenter());
        assertEquals((float) 0,r.getXCenter());
        assertEquals((float) 3,r.getHeight());
        assertEquals((float) 4,r.getWidth());
        assertEquals((float) 50,r.getOutlineThickness());
        assertEquals(Color.BLACK, r.getColor());


        //CIRCLE
        Circle c = (Circle) ShapeFactory.getDefaultInstance(CIRCLE);
        assertEquals((float) 0,c.getXCenter());
        assertEquals((float) 0,c.getXCenter());
        assertEquals((float) 3,c.getRadius());
        assertEquals((float) 50,c.getOutlineThickness());
        assertEquals(Color.BLACK, c.getColor());

        //ARROW
        Arrow a = (Arrow) ShapeFactory.getDefaultInstance(ARROW);
        assertEquals((float) 1,a.getxOrigin());
        assertEquals((float) 1,a.getyOrigin());
        assertEquals((float) 1,a.getxDestination());
        assertEquals((float) 1,a.getyDestination());
        assertEquals((float) 1,a.getStrokeWidth());
        assertEquals((float) 1,a.getArrowHeadLength());
        assertEquals((float) 1,a.getArrowHeadWidth());
        assertEquals(Color.BLACK, a.getColor());

        //CURVED LINE
        CurvedLine cl = (CurvedLine) ShapeFactory.getDefaultInstance(CURVED_LINE);
        assertEquals((float) 0,cl.getxOrigin());
        assertEquals((float) 0,cl.getyOrigin());
        assertEquals((float) 1,cl.getxDestination());
        assertEquals((float) 1,cl.getyDestination());
        assertEquals((float) 50,cl.getStrokeWidth());
        assertEquals((float) 1,cl.getCurvedOutAngle());
        assertEquals((float) 1,cl.getCurvedInAngle());
        assertEquals(Color.BLACK, cl.getColor());

        //LINE
        Line l = (Line) ShapeFactory.getDefaultInstance(LINE);
        assertEquals((float) 0,l.getxOrigin());
        assertEquals((float) 0,l.getyOrigin());
        assertEquals((float) 1,l.getxDestination());
        assertEquals((float) 1,l.getyDestination());
        assertEquals((float) 50,l.getStrokeWidth());
        assertEquals(Color.BLACK, l.getColor());



    }
}
