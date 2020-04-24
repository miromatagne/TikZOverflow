package Model.Shapes;

/**
 * Class for the Curved Line shape (link-type).
 */

public class CurvedLine extends Link {
    private float curveOutAngle;
    private float curveInAngle;

    public CurvedLine(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public CurvedLine(Node origin, Node destination) {
        super(origin, destination);
    }

    public void setCurveOutAngle(float curveOutAngle) {
        this.curveOutAngle = curveOutAngle;
    }
    public void setCurveInAngle(float curveInAngle) { this.curveInAngle = curveInAngle; }

    public float getCurvedOutAngle() {
        return curveOutAngle;
    }
    public float getCurvedInAngle() { return curveInAngle; }

    @Override
    public String getDescription() {
        return "Curved Line from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + ") with a stroke width of " + this.getStrokeWidth() + " and a curved out angle of " + this.getCurvedOutAngle() + " and a curved in angle of " + this.getCurvedInAngle() + ".";
    }

    @Override
    public String generateAndGetTikzCode() {
        String code = "\\draw";

        //Width
        code += " [line width=" + getStrokeWidth() + "mm";

        //Color
        code += " ,color={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() + ";blue," + getColor().getBlue() + "}]";


        //Starting position
        code += " (" + getxOrigin() + "," + getyOrigin() + ")";

        //Curve
        code += " to[out=" + getCurvedOutAngle() + ",in=" + getCurvedInAngle() + "]";

        //Ending position
        code += "(" + getxDestination() + "," + getyDestination() + ");\n";

        return code;
    }
}
