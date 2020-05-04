package Model.Shapes;

/**
 * Class for the Rectangle shape (node-type).
 */

public class Circle extends Node {
    private float radius;

    private final double positionOffsetFactor = 0.7; //factor to get the circle closer to the mouse on the drop event

    public Circle(float xCenter, float yCenter) {
        super(xCenter, yCenter);
    }

    @Override
    public String generateAndGetTikzCode() {
        String code = "\\filldraw";
        code += "[fill={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}," +
                "line width=" + getOutlineThickness() / 20 + "] ";
        code += "(" + getPosX() + "," + getPosY() + ") circle ";
        code += "(" + radius + ");\n";
        return code;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void setPosX(float x) {
        setXCenter((float) (x + radius * positionOffsetFactor));
    }

    @Override
    public void setPosY(float y) {
        setYCenter((float) (y + radius * positionOffsetFactor));
    }
}
