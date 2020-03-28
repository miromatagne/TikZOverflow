package Model.Shapes;

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

    public float getxOrigin() {
        return xOrigin;
    }

    public void setxOrigin(float xOrigin) {
        this.xOrigin = xOrigin;
    }

    public float getyOrigin() {
        return yOrigin;
    }

    public void setyOrigin(float yOrigin) {
        this.yOrigin = yOrigin;
    }

    public float getxDestination() {
        return xDestination;
    }

    public void setxDestination(float xDestination) {
        this.xDestination = xDestination;
    }

    public float getyDestination() {
        return yDestination;
    }

    public void setyDestination(float yDestination) {
        this.yDestination = yDestination;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
