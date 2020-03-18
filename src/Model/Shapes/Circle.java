package Model.Shapes;

public class Circle extends Node {
    private float radius;
    public Circle(float xCenter,float yCenter){
        super(xCenter,yCenter);
    }
    public void setRadius(float radius) {

    @Override
    public String getDescription() {
        return "Circle of radius " + this.getRadius() + " and center (" + this.getPosX() + ","+ this.getPosY() + ").";
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    public float getRadius(){return radius;}
}
