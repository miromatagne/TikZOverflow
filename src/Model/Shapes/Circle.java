package Model.Shapes;

/**
 * Class for the Rectangle shape (node-type).
 */

public class Circle extends Node {
    private float radius;

    private final static double POSITION_OFFSET_FACTOR = 0.7; //factor to get the circle closer to the mouse on the drop event

    public Circle(float xCenter, float yCenter) {
        super(xCenter, yCenter);
    }

    /**
     * Generate TikZ code that creates the shape using the properties.
     *
     * @return generated code
     */
    @Override
    public String generateAndGetTikzCode() {
        String code = super.generateAndGetTikzCode();
        //position of the center of the circle
        code += "(" + getPosX() + "," + getPosY() + ") ";
        //position of the label and his content
        code += "node[color=black, below] at (" + getPosX() + "," + (getPosY()-radius) + "){" + getLabel() +"} ";
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
        setXCenter((float) (x + radius * POSITION_OFFSET_FACTOR));
    }

    @Override
    public void setPosY(float y) {
        setYCenter((float) (y + radius * POSITION_OFFSET_FACTOR));
    }
}
