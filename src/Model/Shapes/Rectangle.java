package Model.Shapes;

public class Rectangle extends Node {
    private int height,width;
    public Rectangle(int xCenter, int yCenter){
        super(xCenter,yCenter);
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight(){return height;}
    public int getWidth(){return width;}
}
