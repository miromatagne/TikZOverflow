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
        String code = "\\filldraw";
        code += "[fill={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}," +
                "line width=" + getOutlineThickness()/20 + "] ";
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
    public void setPosX(float posX) {
        super.setPosX(posX);
        setXCenter(posX);
    }

    @Override
    public void setPosY(float posY) {
        super.setPosY(posY);
        setYCenter(posY);
    }
}
