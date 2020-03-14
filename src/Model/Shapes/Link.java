package Model.Shapes;

public abstract class Link extends Shape{

    private int xOrigin, yOrigin, xDestination, yDestination;
    private int strokeWidth;
    public Link(int xOrigin, int yOrigin, int xDestination, int yDestination){
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

    public void setStrokeWidth(int strokeWidth){
        this.strokeWidth = strokeWidth;
    }
}
