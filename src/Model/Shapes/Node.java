package Model.Shapes;

public abstract class Node extends Shape {
    private int outlineThickness;
    private int xCenter,yCenter;
    public Node(int xCenter, int yCenter){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
    }
    public void setOutlineThickness(int outlineThickness){
        this.outlineThickness = outlineThickness;
    }
}
