package Model.Shapes;

public class CurvedLine extends Link {
    private float curveRadius;

    public CurvedLine(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public CurvedLine(Node origin, Node destination) {
        super(origin, destination);
    }

    public void setCurveRadius(float curveRadius) {
        this.curveRadius = curveRadius;
    }

    public float getCurvedRadius() {
        return curveRadius;
    }

    @Override
    public String getDescription() {
        return "Curved Line from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + ").";
    }
}
