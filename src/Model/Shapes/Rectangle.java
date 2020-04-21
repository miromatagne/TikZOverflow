package Model.Shapes;

/**
 * Class for the Rectangle shape (node-type).
 */

public class Rectangle extends Node {
    private float height, width;

    public Rectangle(float xCenter, float yCenter) {
        super(xCenter, yCenter);
    }


    @Override
    public String getDescription() {
        return "Rectangle of height " + this.getHeight() + " and width " + this.getWidth() + " with a thickness of " + this.getOutlineThickness() + ".";
    }

    @Override
    public String generateAndGetTikzCode() {
        float x1 = getPosX();
        float y1 = getPosY();
        float x2 = getPosX() + width;
        float y2 = getPosY() + height;
        String code = "\\filldraw";
        code += "[fill={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}," +
                "line width=" + getOutlineThickness()/20 + "] ";
        code += "(" + x1 + "," + y1 + ") rectangle ";
        code += "(" + x2 + "," + y2 + ");\n";
        return code;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
