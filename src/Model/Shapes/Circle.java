package Model.Shapes;

public class Circle extends Node {
    private float radius;
    public Circle(float xCenter,float yCenter){
        super(xCenter,yCenter);
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }
    public float getRadius(){return radius;}
}
