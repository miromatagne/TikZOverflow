package Model.Shapes;

public class Circle extends Node {
    private int radius;
    public Circle(xCenter,yCenter){
        super(xCenter,yCenter);
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
}
