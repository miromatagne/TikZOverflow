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

    public void setCurveOutAngle(float curveOutAngle) {
        this.curveOutAngle = curveOutAngle;
    }

    public void setCurveInAngle(float curveInAngle) {
        this.curveInAngle = curveInAngle;
    }

    public float getCurvedOutAngle() {
        return curveOutAngle;
    }

    public float getCurvedInAngle() {
        return curveInAngle;
    }

    @Override
    public String generateAndGetTikzCode() {
        //Defining the position where the label will be drawn
        double labelPosY = Math.min(getyOrigin(),getyDestination());
        double labelPosX;
        if(labelPosY == getyOrigin()){
            labelPosX=getxOrigin();
        }
        else{
            labelPosX=getxDestination();
        }

        String code = "\\draw";
        //Width
        code += " [line width=" + getStrokeWidth() + "mm";
        //Color
        code += super.getColorTikzCode();
        //Starting position
        code += " (" + getxOrigin() + "," + getyOrigin() + ")";
        //Curve
        code += " to[out=" + getCurvedOutAngle() + ",in=" + getCurvedInAngle() + "]";
        //Ending position
        code += "(" + getxDestination() + "," + getyDestination() + ") ";
        //label
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"}; \n" ;

        return code;
    }
}
