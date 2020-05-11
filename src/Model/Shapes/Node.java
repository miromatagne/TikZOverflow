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
        super(xCenter, yCenter);
        this.xCenter = xCenter;
        this.yCenter = yCenter;
    }

    /**
     * Generate TikZ code that creates the node using the properties
     * @return generated code
     */
    public String generateAndGetTikzCode(){
        //filled form
        String code = "\\filldraw";
        //color and border width
        code += "[fill={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}," +
                "line width=" + getOutlineThickness() / 20 + "] ";
        return code;
    }


    public float getXCenter() {
        return xCenter;
    }

    public void setXCenter(float xCenter) {
        this.xCenter = xCenter;
        super.setPosX(xCenter);
    }

    public float getYCenter() {
        return yCenter;
    }

    public void setYCenter(float yCenter) {
        this.yCenter = yCenter;
        super.setPosY(yCenter);
    }

    public float getOutlineThickness() {
        return outlineThickness;
    }

    public void setOutlineThickness(float outlineThickness) {
        this.outlineThickness = outlineThickness;
    }

    public abstract void setPosX(float x);

    public abstract void setPosY(float y);
}
