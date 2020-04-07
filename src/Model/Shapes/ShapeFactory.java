package Model.Shapes;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Factory to create instance of shape
 */

public class ShapeFactory {


    final static int RECTANGLE = 0;
    final static int CIRCLE = 1;
    final static int LINE = 2;
    final static int CURVED_LINE = 3;
    final static int ARROW = 4;
    final static int SQUARE = 5;
    final static int TRIANGLE = 6;

    /**
     * Get an instance of a shape based on information given in parameters
     *
     * @param id    Identify the type of shape
     * @param data  Data needed to create the shape
     * @param color Color of the shape
     * @return Instance of shape
     */
    public static Shape getInstance(int id, ArrayList<Float> data, Color color) {
        Shape instance = null;
        switch (id) {
            case RECTANGLE:
                Rectangle r = new Rectangle(data.get(0), data.get(1));
                r.setHeight(data.get(2));
                r.setWidth(data.get(3));
                r.setOutlineThickness(data.get(4));
                r.setColor(color);
                instance = r;
                break;
            case CIRCLE:
                Circle c = new Circle(data.get(0), data.get(1));
                c.setRadius(data.get(2));
                c.setOutlineThickness(data.get(3));
                c.setColor(color);
                instance = c;
                break;
            case LINE:
                Line l = new Line(data.get(0), data.get(1), data.get(2), data.get(3));
                l.setStrokeWidth(data.get(4));
                l.setColor(color);
                instance = l;
                break;
            case CURVED_LINE:
                CurvedLine cl = new CurvedLine(data.get(0), data.get(1), data.get(2), data.get(3));
                cl.setStrokeWidth(data.get(4));
                cl.setCurveRadius(data.get(5));
                cl.setColor(color);
                instance = cl;
                break;
            case ARROW:
                Arrow a = new Arrow(data.get(0), data.get(1), data.get(2), data.get(3));
                a.setStrokeWidth(data.get(4));
                a.setArrowHeadLength(data.get(5));
                a.setArrowHeadWidth(data.get(6));
                a.setColor(color);
                instance = a;
                break;
            case SQUARE:
                Rectangle square = new Rectangle(data.get(0), data.get(1));
                square.setHeight(data.get(2));
                square.setWidth(data.get(3));
                square.setOutlineThickness(data.get(4));
                square.setColor(color);
                instance = square;
                break;
            case TRIANGLE:
                Triangle triangle = new Triangle(data.get(0), data.get(1));
                triangle.setBase(data.get(2));
                triangle.setHeight(data.get(3));
                triangle.setOutlineThickness(data.get(4));
                triangle.setColor(color);
                instance = triangle;
        }
        return instance;
    }

    public static Shape getDefaultInstance(int id) {
        Shape instance = null;
        ArrayList<Float> data = new ArrayList<>();

        switch(id) {
            case RECTANGLE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("3"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("50"));
                instance = getInstance(RECTANGLE, data, Color.BLACK);
                break;
            case CIRCLE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("3"));
                data.add(Float.parseFloat("50"));
                instance = getInstance(CIRCLE, data, Color.BLACK);
                break;
            case LINE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("50"));
                instance = getInstance(LINE, data, Color.BLACK);
                break;
            case CURVED_LINE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("50"));
                data.add(Float.parseFloat("1"));
                instance = getInstance(CURVED_LINE, data, Color.BLACK);
                break;
            case ARROW:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("2"));
                data.add(Float.parseFloat("2"));
                data.add(Float.parseFloat("50"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("1"));
                instance = getInstance(ARROW, data, Color.BLACK);
                break;
            case SQUARE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("50"));
                instance = getInstance(RECTANGLE, data, Color.BLACK);
                break;
            case TRIANGLE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("50"));
                instance = getInstance(TRIANGLE, data, Color.BLACK);
                break;
        }
        return instance;
    }
}
