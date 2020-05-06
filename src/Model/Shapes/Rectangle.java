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
    public String generateAndGetTikzCode() {
        float x1 = getPosX();
        float y1 = getPosY();
        float x2 = getPosX() + width;
        float y2 = getPosY() + height;
        //filled form
        String code = "\\filldraw";
        //color and border width
        code += "[fill={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}," +
                "line width=" + getOutlineThickness() / 20 + "] ";
        //position of the bottom left corner
        code += "(" + x1 + "," + y1 + ") ";
        //label of the shape
        code += "node[above] at (" + (x1+x2)/2 + "," + (y1+y2)/2+ "){" + getLabel() +"} rectangle ";
        //position of the top right corner
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

    @Override
    public void setPosX(float x) {
        setXCenter(x);
    }

    @Override
    public void setPosY(float y) {
        setYCenter(y);
    }
}
