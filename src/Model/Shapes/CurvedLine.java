package Model.Shapes;

/**
 * Class for the Curved Line shape (link-type).
 */

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
        return "Curved Line from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + ") with a stroke width of " + this.getStrokeWidth() + " and a curved radius of " + this.getCurvedRadius() + ".";
    }

    @Override
    public String generateAndGetTikzCode() {
        String code = "\\draw";

        //Width
        code += " [line width=" + String.valueOf(getStrokeWidth()) + "mm";

        //Color
        code += " ,color={rgb:red," + String.valueOf(getColor().getRed()*100) + ";green," + String.valueOf(getColor().getGreen()*100);
        code += ";blue," + String.valueOf(getColor().getBlue()*100) + "}]";


        //Starting position
        code += " (" + String.valueOf(getxOrigin()) + "," + String.valueOf(getyOrigin()) + ")";

        //Curve
        code += " to[out=" + String.valueOf(getCurvedRadius()) + ",in=" + String.valueOf(getCurvedRadius()) + "]";

        //Ending position
        code += "(" + String.valueOf(getxDestination()) + "," + String.valueOf(getyDestination()) + ");";

        return code;
    }
}
