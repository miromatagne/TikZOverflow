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
    public String generateAndGetTikzCode() {
        String code = "\\draw";

        //Width
        code += " [line width=" + getStrokeWidth() + "mm";

        //Color
        code += " ,color={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}]";

        //Draw line Position
        code += " (" + getxOrigin() + "," + getyOrigin() + ")";
        code += " --";
        code += " (" + getxDestination() + "," + getyDestination() + ");\n";
        return code ;
    }

}
