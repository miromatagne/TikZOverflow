package Model.Shapes;

public class Arrow extends Link{
    private int arrowHeadLength;
    private int arrowHeadWidth;
    public Arrow(int xOrigin, int yOrigin, int xDestination, int yDestination){
        super(xOrigin, yOrigin, xDestination, yDestination);
    }
    public Arrow(Shape origin, Shape destination){
        super(origin, destination);
    }

    public void setArrowHeadLength(int arrowHeadLength) {
        this.arrowHeadLength = arrowHeadLength;
    }

    public void setArrowHeadWidth(int arrowHeadWidth) {
        this.arrowHeadWidth = arrowHeadWidth;
    }
}
