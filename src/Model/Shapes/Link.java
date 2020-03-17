package Model.Shapes;

public abstract class Link extends Shape{

    private float xOrigin;
    private float yOrigin;
    private float xDestination;
    private float yDestination;
    private float strokeWidth;
    public Link(float xOrigin, float yOrigin, float xDestination, float yDestination){
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xDestination = xDestination;
        this.yDestination = yDestination;
    }

    public Link(Node origin, Node destination){
        this.xOrigin = origin.getPosX();
        this.yOrigin = origin.getPosY();
        this.xDestination = destination.getPosX();
        this.yDestination = destination.getPosY();
    }

    public void setStrokeWidth(float strokeWidth){
        this.strokeWidth = strokeWidth;
    }
}
