package Model.Shapes;

/**
 * Class for the Arrow shape (link-type).
 */

public class Arrow extends Link {
    private float arrowHeadLength = 5;
    private float arrowHeadWidth = 5;

    public Arrow(float xOrigin, float yOrigin, float xDestination, float yDestination) {
        super(xOrigin, yOrigin, xDestination, yDestination);
    }

    public Arrow(Node origin, Node destination) {
        super(origin, destination);
    }

    @Override
    public String getDescription() {
        return "Arrow from (" + this.getxOrigin() + "," + this.getyOrigin() + ") to (" + this.getxDestination() + "," + this.getyDestination() + "), with a stroke width of " + this.getStrokeWidth() + " , a head length of " + this.getArrowHeadLength() + " and a head width of " + this.getArrowHeadWidth() + "." ;
    }

    @Override
    public String generateAndGetTikzCode() {

      //  \draw [arrows={->[length=5mm,width=5mm]}, line width=1mm,
        // color={rgb:red,2;green,0;blue,5}] (0.0,0.0) -- (5.0,5.0);
        String code = "\\draw";

        //Arrow
        code += " [arrows={->[length=" + String.valueOf(getArrowHeadLength()) + "mm, width="
                + String.valueOf(getArrowHeadWidth()) + "mm]}";

        //Width
        code += " ,line width=" + String.valueOf(getStrokeWidth()) + "mm";

        //Color
        code += " ,color={rgb:red," + String.valueOf(getColor().getRed()*100) + ";green," + String.valueOf(getColor().getGreen()*100);
        code += ";blue," + String.valueOf(getColor().getBlue()*100) + "}]";

        //Draw line Position
        code += " (" + String.valueOf(getxOrigin()) + "," + String.valueOf(getyOrigin()) + ")";
        code += " --";
        code += " (" + String.valueOf(getxDestination()) + "," + String.valueOf(getyDestination()) + ");";
        return code ;
    }

    public float getArrowHeadLength() {
        return arrowHeadLength;
    }

    public void setArrowHeadLength(float arrowHeadLength) {
        this.arrowHeadLength = arrowHeadLength;
    }

    public float getArrowHeadWidth() {
        return arrowHeadWidth;
    }

    public void setArrowHeadWidth(float arrowHeadWidth) {
        this.arrowHeadWidth = arrowHeadWidth;
    }
}
