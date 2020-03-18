package Model.Shapes;

public class Line extends Link {
    public Line(int xOrigin, int yOrigin, int xDestination, int yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public Line(Node origin, Node destination) {
        super(origin, destination);
    }
}
