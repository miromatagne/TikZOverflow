package Model.Shapes;

/**
 * Superclass for node-type shapes figuring in a diagram
 * Nodes can be connected using links (Link class)
 * In addition to the general attributes from the Shape class, nodes have an outlineThickness attribute
 */

public abstract class Node extends Shape {
    private float outlineThickness;
    private float xCenter, yCenter;

    public Node(float xCenter, float yCenter) {
        super(0, 0);
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

    public void setOutlineThickness(float outlineThickness) {
        this.outlineThickness = outlineThickness;
    }
}
