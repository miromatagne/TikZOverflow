package Model.Shapes;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Factory to create instance of shape
 */

public final class ShapeFactory {


    public static final int RECTANGLE = 0;
    public static final int CIRCLE = 1;
    public static final int LINE = 2;
    public static final int CURVED_LINE = 3;
    public static final int ARROW = 4;
    public static final int TRIANGLE = 5;
    public static final int SQUARE = 6;

    private ShapeFactory(){}

    /**
     * Get an instance of a shape based on information given in parameters
     *
     * @param id    Identify the type of shape
     * @param data  Data needed to create the shape
     * @param color Color of the shape
     * @param label Label of the shape
     * @return Instance of shape
     */
    public static Shape getInstance(int id, ArrayList<Float> data, Color color, String label) {
        Shape instance = null;
        switch (id) {
            case RECTANGLE:
                Rectangle rectangle = new Rectangle(data.get(0), data.get(1));
                rectangle.setHeight(data.get(2));
                rectangle.setWidth(data.get(3));
                rectangle.setOutlineThickness(data.get(4));
                rectangle.setColor(color);
                rectangle.setLabel(label);
                instance = rectangle;
                break;
            case CIRCLE:
                Circle circle = new Circle(data.get(0), data.get(1));
                circle.setRadius(data.get(2));
                circle.setOutlineThickness(data.get(3));
                circle.setColor(color);
                circle.setLabel(label);
                instance = circle;
                break;
            case LINE:
                Line line = new Line(data.get(0), data.get(1), data.get(2), data.get(3));
                line.setStrokeWidth(data.get(4));
                line.setColor(color);
                line.setLabel(label);
                instance = line;
                break;
            case CURVED_LINE:
                CurvedLine curvedLine = new CurvedLine(data.get(0), data.get(1), data.get(2), data.get(3));
                curvedLine.setStrokeWidth(data.get(4));
                curvedLine.setCurveOutAngle(data.get(5));
                curvedLine.setCurveInAngle(data.get(6));
                curvedLine.setColor(color);
                curvedLine.setLabel(label);
                instance = curvedLine;
                break;
            case ARROW:
                Arrow arrow = new Arrow(data.get(0), data.get(1), data.get(2), data.get(3));
                arrow.setStrokeWidth(data.get(4));
                arrow.setArrowHeadLength(data.get(5));
                arrow.setArrowHeadWidth(data.get(6));
                arrow.setColor(color);
                arrow.setLabel(label);
                instance = arrow;
                break;
            case SQUARE:
                Rectangle square = new Rectangle(data.get(0), data.get(1));
                square.setHeight(data.get(2));
                square.setWidth(data.get(3));
                square.setOutlineThickness(data.get(4));
                square.setColor(color);
                square.setLabel(label);
                instance = square;
                break;
            case TRIANGLE:
                Triangle triangle = new Triangle(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5));
                triangle.setOutlineThickness(data.get(6));
                triangle.setColor(color);
                triangle.setLabel(label);
                instance = triangle;
        }
        return instance;
    }

    /**
     * Get a default instance of a shape
     *
     * @param id Identify the type of shape
     * @return default instance of shape
     */
    public static Shape getDefaultInstance(int id) {
        Shape instance = null;
        ArrayList<Float> data = new ArrayList<>();

        switch (id) {
            case RECTANGLE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("3"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("3"));
                instance = getInstance(RECTANGLE, data, Color.GREY, "Rectangle");
                break;
            case CIRCLE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("2"));
                data.add(Float.parseFloat("3"));
                instance = getInstance(CIRCLE, data, Color.GREY, "Circle");
                break;
            case LINE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("1"));
                instance = getInstance(LINE, data, Color.GREY, "Line");
                break;
            case CURVED_LINE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("90"));
                data.add(Float.parseFloat("-90"));
                instance = getInstance(CURVED_LINE, data, Color.GREY, "Curved line");
                break;
            case ARROW:
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("1"));
                data.add(Float.parseFloat("5"));
                data.add(Float.parseFloat("5"));
                instance = getInstance(ARROW, data, Color.GREY, "Arrow");
                break;
            case SQUARE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("4"));
                data.add(Float.parseFloat("3"));
                instance = getInstance(RECTANGLE, data, Color.GREY, "Square");
                break;
            case TRIANGLE:
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("2"));
                data.add(Float.parseFloat("3"));
                data.add(Float.parseFloat("5"));
                data.add(Float.parseFloat("0"));
                data.add(Float.parseFloat("3"));
                instance = getInstance(TRIANGLE, data, Color.GREY, "Triangle");
                break;
        }
        return instance;
    }
}
