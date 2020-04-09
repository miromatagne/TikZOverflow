package Model.Shapes;

/**
 * Class for the Rectangle shape (node-type).
 */

public class Circle extends Node {
    private float radius;

    public Circle(float xCenter, float yCenter) {
        super(xCenter, yCenter);
    }

    @Override
    public String getDescription() {
        return "Circle of radius " + this.getRadius() + " , center (" + this.getXCenter() + "," + this.getYCenter() + ") and thickness " + this.getOutlineThickness() + ".";
    }

    @Override
    public String generateAndGetTikzCode() {
        return null;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
