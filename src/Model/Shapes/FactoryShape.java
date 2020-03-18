package Model.Shapes;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Factory to creat instance of shape
 */

public class FactoryShape {


    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;

    /**
     * Get an instance of a shape based on information given in parameters
     * @param id            Identify the type of shape
     * @param data          Data needed to create the shape
     * @param color         Color of the shape
     * @return              Instance of shape
     */
    public static Shape getInstance(int id, ArrayList<Float> data, Color color) {
        Shape instance = null;
        switch (id) {
            case RECTANGLE:
                Rectangle r = new Rectangle(data.get(0),data.get(1));
                r.setHeight(data.get(2));
                r.setWidth(data.get(3));
                r.setOutlineThickness(data.get(4));
                r.setColor(color);
                instance=r;
                break;
            case CIRCLE:
                Circle c = new Circle(data.get(0),data.get(1));
                c.setRadius(data.get(2));
                c.setOutlineThickness(data.get(3));
                c.setColor(color);
                instance=c;
                break;
            case LINE:
                Line l = new Line(data.get(0),data.get(1),data.get(2),data.get(3));
                l.setStrokeWidth(data.get(4));
                l.setColor(color);
                instance=l;
                break;
            case CURVED_LINE:
                CurvedLine cl = new CurvedLine(data.get(0),data.get(1),data.get(2),data.get(3));
                cl.setStrokeWidth(data.get(4));
                cl.setCurveRadius(data.get(5));
                cl.setColor(color);
                instance=cl;
                break;
            case ARROW:
                Arrow a = new Arrow(data.get(0),data.get(1),data.get(2),data.get(3));
                a.setStrokeWidth(data.get(4));
                a.setArrowHeadLength(data.get(5));
                a.setArrowHeadWidth(data.get(6));
                a.setColor(color);
                instance=a;
                break;
        }
        return instance;
    }
}
