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
    public String generateAndGetTikzCode() {
        String code = super.generateAndGetTikzCode();
        //position of the center of the circle
        code += "(" + getPosX() + "," + getPosY() + ") ";
        //position of the label and his content
        code += "node[below] at (" + getPosX() + "," + (getPosY()-radius) + "){" + getLabel() +"} ";
        //radius of the circle
        code += "circle (" + radius + ");\n";
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
        setXCenter((float) (x + radius * 0.7));
    }

    @Override
    public void setPosY(float y) {
        setYCenter((float) (y + radius * 0.7));
    }
}
