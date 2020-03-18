package Model.Shapes;

public class Arrow extends Link{
    private int arrowHeadLength = 5;
    private int arrowHeadWidth = 5;
    public Arrow(int xOrigin, int yOrigin, int xDestination, int yDestination){
        super(xOrigin, yOrigin, xDestination, yDestination);
    }
    public Arrow(Node origin, Node destination){
        super(origin, destination);
    }

    public void setArrowHeadLength(int arrowHeadLength) {
        this.arrowHeadLength = arrowHeadLength;
    }

    public void setArrowHeadWidth(int arrowHeadWidth) {
        this.arrowHeadWidth = arrowHeadWidth;
    }

    @Override
    public String getDescription() {
        return "Arrow from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + ").";
    }
}
