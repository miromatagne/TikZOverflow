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
        float labelPosX = (x1+x2)/2;
        float labelPosY = y1;

        String code = super.generateAndGetTikzCode();
        //position of the bottom left corner
        code += "(" + x1 + "," + y1 + ") ";
        //position of the label and his content
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"} ";
        //position of the top right corner
        code += "rectangle (" + x2 + "," + y2 + ");\n";
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
