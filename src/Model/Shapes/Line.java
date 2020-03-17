package Model.Shapes;

public class Line extends Link {
    public Line(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public Line(Node origin, Node destination) {
        super(origin, destination);
    }
}
