package Model.Shapes;

public abstract class Node extends Shape {
    private float outlineThickness;
    private float xCenter,yCenter;
    public Node(float xCenter, float yCenter){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
    }
    public void setOutlineThickness(float outlineThickness){
        this.outlineThickness = outlineThickness;
    }
}
