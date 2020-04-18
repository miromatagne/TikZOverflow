package Model.Shapes;

/**
 * Class for the Line shape (link-type).
 */

public class Line extends Link {
    public Line(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public Line(Node origin, Node destination) {
        super(origin, destination);
    }

    @Override
    public String getDescription() {
        return "Line from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + ") with a stroke width of " + this.getStrokeWidth() + ".";
    }
}
