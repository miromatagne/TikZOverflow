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

    @Override
    public String generateAndGetTikzCode() {
        String code = "\\draw";

        //Width
        code += " [line width=" + String.valueOf(getStrokeWidth()) + "mm";

        //Color
        code += " ,color={rgb:red," + String.valueOf(getColor().getRed()*100) + ";green," + String.valueOf(getColor().getGreen()*100);
        code += ";blue," + String.valueOf(getColor().getBlue()*100) + "}]";

        //Draw line Position
        code += " (" + String.valueOf(getxOrigin()) + "," + String.valueOf(getyOrigin()) + ")";
        code += " --";
        code += " (" + String.valueOf(getxDestination()) + "," + String.valueOf(getyDestination()) + ");";
        return code ;
    }
}
