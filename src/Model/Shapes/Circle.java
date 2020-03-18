package Model.Shapes;

public class Circle extends Node {
    private int radius;
    public Circle(int xCenter,int yCenter){
        super(xCenter,yCenter);
    }

    @Override
    public String getDescription() {
        return "Circle of radius " + this.getRadius() + " and center (" + this.getPosX() + ","+ this.getPosY() + ").";
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    public int getRadius(){return radius;}
}
