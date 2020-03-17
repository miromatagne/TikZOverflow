package Model.Shapes;

public class Rectangle extends Node {
    private float height,width;
    public Rectangle(float xCenter, float yCenter){
        super(xCenter,yCenter);
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public float getHeight(){return height;}
    public float getWidth(){return width;}
}
