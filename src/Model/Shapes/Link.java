package Model.Shapes;

/**
 * Superclass for link-type shapes. Links connect two points.
 * They store the positions of those two points and have a strokeWidth attribute.
 */

public abstract class Link extends Shape {

    private float xOrigin;
    private float yOrigin;
    private float xDestination;
    private float yDestination;
    private float strokeWidth;

    public Link(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin);
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xDestination = xDestination;
        this.yDestination = yDestination;
    }

    public Link(Node origin, Node destination) {
        super(origin.getPosX(), origin.getPosY());
        this.xOrigin = origin.getPosX();
        this.yOrigin = origin.getPosY();
        this.xDestination = destination.getPosX();
        this.yDestination = destination.getPosY();
    }

    /**
     * Generate TikZ code for the color using the properties.
     *
     * @return generated code
     */
    public String getColorTikzCode(){
        //Color
        return " ,color={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}]";
    }

    public float getXOrigin() {
        return xOrigin;
    }

    public void setXOrigin(float xOrigin) {
        this.xOrigin = xOrigin;
    }

    public float getYOrigin() {
        return yOrigin;
    }

    public void setYOrigin(float yOrigin) {
        this.yOrigin = yOrigin;
    }

    public float getXDestination() {
        return xDestination;
    }

    public void setXDestination(float xDestination) {
        this.xDestination = xDestination;
    }

    public float getYDestination() {
        return yDestination;
    }

    public void setYDestination(float yDestination) {
        this.yDestination = yDestination;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setPosX(float x) {
        float distanceX = getXDestination() - getXOrigin();
        setXOrigin(x);
        setXDestination(x + distanceX);
    }

    public void setPosY(float y) {
        float distanceY = getYDestination() - getYOrigin();
        setYOrigin(y);
        setYDestination(y + distanceY);
    }
}
