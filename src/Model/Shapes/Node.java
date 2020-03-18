package Model.Shapes;

public abstract class Node extends Shape {


    private float outlineThickness;
    private float xCenter,yCenter;
    public Node(float xCenter, float yCenter){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
    }


    public float getXCenter() {
        return xCenter;
    }

    public void setXCenter(float xCenter) {
        this.xCenter = xCenter;
    }

    public float getYCenter() {
        return yCenter;
    }

    public void setYCenter(float yCenter) {
        this.yCenter = yCenter;
    }

    public float getOutlineThickness() {
        return outlineThickness;
    }

    public void setOutlineThickness(float outlineThickness){
        this.outlineThickness = outlineThickness;
    }
}
