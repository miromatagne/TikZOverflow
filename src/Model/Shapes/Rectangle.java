package Model.Shapes;

public class Rectangle extends Node {
    private float height, width;

    public Rectangle(float xCenter, float yCenter) {
        super(xCenter, yCenter);
    }


    @Override
    public String getDescription() {
        return "Rectangle of height " + this.getHeight() + " and width " + this.getWidth() + ".";
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
