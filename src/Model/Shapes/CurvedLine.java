package Model.Shapes;

public class CurvedLine extends Link{
    private int curveRadius;
    public CurvedLine(int xOrigin, int yOrigin, int xDestination, int yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public CurvedLine(Node origin, Node destination) {
        super(origin, destination);
    }

    public void setCurveRadius(int curveRadius) {
        this.curveRadius = curveRadius;
    }

    @Override
    public String getDescription() {
        return "Curved Line from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + ").";
    }
}
