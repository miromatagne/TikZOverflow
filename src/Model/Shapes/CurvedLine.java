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

    /**
     * Generate TikZ code that creates the shape using the properties.
     *
     * @return generated code
     */
    @Override
    public String generateAndGetTikzCode() {
        //Defining the position where the label will be drawn
        double labelPosY = Math.min(getYOrigin(), getYDestination());
        double labelPosX;
        if(labelPosY == getYOrigin()){
            labelPosX= getXOrigin();
        }
        else{
            labelPosX= getXDestination();
        }

        String code = "\\draw";
        //Width
        code += " [line width=" + getStrokeWidth() + "mm";
        //Color
        code += super.getColorTikzCode();
        //Starting position
        code += " (" + getXOrigin() + "," + getYOrigin() + ")";
        //Curve
        code += " to[out=" + getCurvedOutAngle() + ",in=" + getCurvedInAngle() + "]";
        //Ending position
        code += "(" + getXDestination() + "," + getYDestination() + ") ";
        //label
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"}; \n" ;

        return code;
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
}
