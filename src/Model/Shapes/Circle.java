package Model.Shapes;

public class Circle extends Node {
    private int radius;
    public Circle(int xCenter,int yCenter){
        super(xCenter,yCenter);
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public int getRadius(){return radius;}
}
