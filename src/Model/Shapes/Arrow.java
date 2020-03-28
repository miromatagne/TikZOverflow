package Model.Shapes;

public class Arrow extends Link {
    private float arrowHeadLength = 5;
    private float arrowHeadWidth = 5;

    public Arrow(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public Arrow(Node origin, Node destination) {
        super(origin, destination);
    }

    @Override
    public String getDescription() {
        return "Arrow from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + ").";
    }

    public float getArrowHeadLength() {
        return arrowHeadLength;
    }

    public void setArrowHeadLength(float arrowHeadLength) {
        this.arrowHeadLength = arrowHeadLength;
    }

    public float getArrowHeadWidth() {
        return arrowHeadWidth;
    }

    public void setArrowHeadWidth(float arrowHeadWidth) {
        this.arrowHeadWidth = arrowHeadWidth;
    }
}
