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
        code += " [line width=" + String.valueOf(getStrokeWidth()) + "mm";

        //Color
        code += " ,color={rgb:red," + String.valueOf(getColor().getRed()*100) + ";green," + String.valueOf(getColor().getGreen()*100);
        code += ";blue," + String.valueOf(getColor().getBlue()*100) + "}]";


        //Starting position
        code += " (" + String.valueOf(getxOrigin()) + "," + String.valueOf(getyOrigin()) + ")";

        //Curve
        code += " to[out=" + String.valueOf(getCurvedOutAngle()) + ",in=" + String.valueOf(getCurvedInAngle()) + "]";

        //Ending position
        code += "(" + String.valueOf(getxDestination()) + "," + String.valueOf(getyDestination()) + ");";

        return code;
    }
}
