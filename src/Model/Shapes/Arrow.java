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
        code += " [arrows={->[length=" + getArrowHeadLength() + "mm, width="
                + getArrowHeadWidth() + "mm]}";

        //Width
        code += " ,line width=" + getStrokeWidth() + "mm";

        //Color
        code += " ,color={rgb,1:red," + getColor().getRed() + ";green," + getColor().getGreen() +
                ";blue," + getColor().getBlue() + "}]";

        //Draw line Position
        code += " (" + getxOrigin() + "," + getyOrigin() + ")";
        code += " --";
        code += " (" + getxDestination() + "," + getyDestination() + ");";
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

    @Override
    public void setPosX(float posX) {
        super.setPosX(posX);
        super.setxOrigin(posX);
        super.setxDestination(posX+5);
    }

    @Override
    public void setPosY(float posY) {
        super.setPosY(posY);
        super.setyOrigin(posY);
        super.setyDestination(posY+5);
    }
}
