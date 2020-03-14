package Model.Shapes;

public class Rectangle extends Node {
    private int rectangleLength,rectangleWidth;
    public Rectangle(int xCenter, int yCenter){
        super(xCenter,yCenter);
    }
    public void setRectangleLength(int rectangleLength) {
        this.rectangleLength = rectangleLength;
    }
    public void setRectangleWidth(int rectangleWidth) {
        this.rectangleWidth = rectangleWidth;
    }
}
