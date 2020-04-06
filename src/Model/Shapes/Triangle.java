package Model.Shapes;

public class Triangle extends Node {
    private float height, base;

    public Triangle(float xCenter, float yCenter) { super(xCenter, yCenter); }

    @Override
    public String getDescription() {
        return "Triangle of center (" + this.getXCenter() + "," + this.getYCenter() + ").";
    }

    public float getBase() { return base; }

    public void setBase(float base) { this.base = base; }

    public float getHeight() { return height; }

    public void setHeight(float height) { this.height = height; }
}
